package com.me.mygdxgame;

import java.util.ArrayList;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class WorldRenderer {
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;
	
	public WorldRenderer() {
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
	}
	
	public void renderWorld(OrthographicCamera cam, Player player, ArrayList<Rectangle> blocks, ArrayList<Entity> entityList, boolean debug) {
		// if debug mode is on, draw bounding boxes
		if (debug) {
			shapeRenderer.setProjectionMatrix(cam.combined);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.rect(player.getBound().x, player.getBound().y, player.getBound().width, player.getBound().height);
			for (Rectangle rect : blocks) {
				shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
			}
			shapeRenderer.end();
		}
		
		// draw all entities and the player
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		for (Entity ent : entityList) {
			spriteBatch.draw(ent.getFrame(), ent.getPosition().x, ent.getPosition().y, 1, 1);
		}
		spriteBatch.draw(player.getFrame(), player.getPosition().x, player.getPosition().y, 1, 1);
		spriteBatch.end();
	}
}