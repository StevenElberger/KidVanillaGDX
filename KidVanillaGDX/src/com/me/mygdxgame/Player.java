package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private float width,height;
	private float stateTime;
	//private Vector2 gravity;
	private Vector2 position;
	private Animation vanillaWalk;
	private Texture vanillaSheet;
	private TextureRegion[] vanillaFrames;
	private SpriteBatch spriteBatch;
	private TextureRegion currentFrame;
	
	public Player() {
		width = 64;
		height = 64;
		position = new Vector2(4,6);
		vanillaSheet = new Texture(Gdx.files.internal("vanillawalkrightsprite.png"));
		TextureRegion[][] tmp = TextureRegion.split(vanillaSheet, 16, 16);
		vanillaFrames = new TextureRegion[3];
		int index = 0;
		for (int i = 0; i < 3; i++) {
			vanillaFrames[index++] = tmp[0][i];
		}
		vanillaWalk = new Animation(0.025f, vanillaFrames);
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
		//gravity = new Vector2(0, -2f);
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public void setX(float paramX) {
		position.x = paramX;
	}
	
	public void setY(float paramY) {
		position.y = paramY;
	}
	
	public void drawPlayer() {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = vanillaWalk.getKeyFrame(stateTime, true);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, position.x * 64, position.y * 64, width, height);
		spriteBatch.end();
	}

}
