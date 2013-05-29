package com.me.mygdxgame;

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

public class KidVanilla implements ApplicationListener {
	private TiledMap map;
	private Player player;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	@Override
	public void create() {		
		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();
		map = new TmxMapLoader().load("data/newmap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);
		
		//TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get(0);
		//Cell cell = tiledLayer.getCell(0, 0);
		//TiledMapTile tile = cell.getTile();
		//tile.getProperties();
		
		player = new Player();
		createCollisionArray();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 100, 100);
		camera.position.set(player.getX(), player.getY(), 0);	// Top left corner coords (0, 100)
		camera.zoom = .2f;	// Smaller means larger zoom
	}
	
	public void createCollisionArray() {
		Cell cell;
		TiledMapTile tile;
		MapProperties mapProps;
		TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get(0);
		int xAxis = tiledLayer.getWidth();
		int yAxis = tiledLayer.getHeight();
		int dick = 0;
		for (int i = 0; i < xAxis; i++) {
			for (int j = 0; j < yAxis; j++) {
				cell = tiledLayer.getCell(i, j);
				tile = cell.getTile();
				mapProps = tile.getProperties();
				if (mapProps.get("blocked") == null) {
					dick++;
					System.out.println("Shitdick" + dick);
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
		camera.update();
		renderer.setView(camera);
		renderer.render();
		player.drawPlayer();
	}
	
	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			camera.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (camera.position.y < 1400) {
				camera.translate(0, 1, 0);
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (camera.position.y > 0) {
				camera.translate(0, -1, 0);
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (camera.position.x > 0) {
				camera.translate(-1, 0, 0);
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (camera.position.x < 1600) {
				camera.translate(1, 0, 0);
			}
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
