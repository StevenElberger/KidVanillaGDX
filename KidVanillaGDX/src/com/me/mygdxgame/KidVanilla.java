package com.me.mygdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class KidVanilla implements ApplicationListener {
	private TiledMap map;
	private Player player;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private ArrayList<Rectangle> blocks;
	
	@Override
	public void create() {		
		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();
		map = new TmxMapLoader().load("data/newmap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);
		
		player = new Player();
		createCollisionArray();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10, 10);
		camera.position.set(2, 94, 0);	// Top left corner coords (0, 100)
		//camera.zoom = .2f;	// Smaller means larger zoom
	}
	
	public void createCollisionArray() {
		Cell cell;
		TiledMapTile tile;
		MapProperties mapProps;
		blocks = new ArrayList<Rectangle>();
		TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get(0);
		int xAxis = tiledLayer.getWidth();
		int yAxis = tiledLayer.getHeight();
		for (int i = 0; i < xAxis; i++) {
			for (int j = 0; j < yAxis; j++) {
				cell = tiledLayer.getCell(i, j);
				tile = cell.getTile();
				mapProps = tile.getProperties();
				if (mapProps.get("blocked") != null) {
					blocks.add(new Rectangle(xAxis, yAxis, 16, 16));
				}
			}
		}
	}

	@Override
	public void dispose() {
		map.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		handleInput();
		//camera.position.set(player.getX(), player.getY(), 0);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		player.drawPlayer();
	}
	
	private void handleInput() {
		//if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
		//	camera.zoom += 0.02;
		//}
		//if (Gdx.input.isKeyPressed(Input.Keys.A)) {
		//	camera.zoom -= 0.02;
		//}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.setY(player.getY() + 0.05f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.setY(player.getY() - 0.05f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.setX(player.getX() - 0.05f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.setX(player.getX() + 0.05f);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
