package com.me.mygdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.me.mygdxgame.Player.State;

public class KidVanilla implements ApplicationListener {
	private TiledMap map;
	private Player player;
	private boolean debug;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private ArrayList<Rectangle> blocks;
	
	@Override
	public void create() {		
		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();
		
		// load map and map renderer (set scale to 1/16)
		map = new TmxMapLoader().load("data/newmap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);
		debug = false;
		
		// create the player character and load the collision blocks
		player = new Player();
		createCollisionArray();
		
		// create the orthographic camera, show 10x10 tiles
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10, 10);
		camera.position.set(2, 94, 0);	// Top left corner is (0, 100)
	}
	
	public void createCollisionArray() {
		Cell cell;
		TiledMapTile tile;
		blocks = new ArrayList<Rectangle>();
		TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get("map");
		int xAxis = tiledLayer.getWidth();
		int yAxis = tiledLayer.getHeight();
		for (int i = 0; i < xAxis; i++) {
			for (int j = 0; j < yAxis; j++) {
				cell = tiledLayer.getCell(i, j);
				tile = cell.getTile();
				if (tile.getProperties().get("blocked").equals("true")) {
					blocks.add(new Rectangle(i, j, 1, 1));
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
		camera.position.set(player.getX(), 94, 0);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		player.drawPlayer(camera, blocks, debug);
	}
	
	private void handleInput() {
		boolean leftDown = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean rightDown = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		boolean spaceDown = Gdx.input.isKeyPressed(Input.Keys.SPACE);
		boolean dDown = Gdx.input.isKeyPressed(Input.Keys.D);
		if (dDown) {
			if (debug) {
				debug = false;
			} else {
				debug = true;
			}
		}
		if (spaceDown) {
			if ((player.getBound().moveVertical(blocks, 1f)) && !player.getJumping()) {
				player.setJumping(true);
				player.getVelocity().y = player.getMaxVelocity();
			}
		}
		if (leftDown) {
			player.setFacingRight(false);
			if (player.getBound().moveSideways(blocks, -0.1f)) {
				player.setState(State.WALKING);
				player.getBound().setX(player.getBound().getX() - 0.1f);
				player.setX(player.getX() - 0.1f);
			}
		}
		if (rightDown) {
			player.setFacingRight(true);
			if (player.getBound().moveSideways(blocks, 0.1f)) {
				player.setState(State.WALKING);
				player.getBound().setX(player.getBound().getX() + 0.1f);
				player.setX(player.getX() + 0.1f);
			}
		}
		if (rightDown && leftDown) {
			player.setState(State.IDLE);
		}
		if (!(rightDown) && !(leftDown)) {
			player.setState(State.IDLE);
		}
		if (player.getBound().moveVertical(blocks, player.getVelocity().y)) {
			if (player.getVelocity().y <= player.getGravity()) {
				player.getVelocity().y = player.getGravity();
				player.getBound().setY(player.getBound().getY() + player.getVelocity().y);
				player.getPosition().y += player.getVelocity().y;
			} else {
				player.getVelocity().y += player.getGravity();
				player.getBound().setY(player.getBound().getY() + player.getVelocity().y);
				player.getPosition().y += player.getVelocity().y;
			}
		} else if (player.getVelocity().y == player.getGravity()) {
			player.setJumping(false);
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
