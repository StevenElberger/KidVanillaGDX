package com.me.mygdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.me.mygdxgame.Player.State;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class KidVanilla implements ApplicationListener {
	private Blob blob;
	private TiledMap map;
	private Player player;
	private boolean debug;
	private SpriteBatch spriteBatch;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private ArrayList<Rectangle> blocks;
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void create() {				
		// load map and map renderer (set scale to 1/16)
		map = new TmxMapLoader().load("data/newmap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);
		debug = false;
		
		// testing the new player class
		blob = new Blob(100, 7, 95, "blobsheet.png", 10, 0.8f, 0.95f);
		player = new Player(100, 3, 95, "vanillawalk.png", 4, 0.8f, 0.95f);
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		// create the player character and load the collision blocks
		createCollisionArray();
		
		// create the orthographic camera, show 10x10 tiles
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10, 10);
		camera.position.set(2, 94, 0);	// Top left corner is (0, 100)
	}
	
	public void createCollisionArray() {
		// tiledMap > tiledLayer > Cell > Tile
		// for every tile that has a blocked property that equals true
		// add it to the array list of blocks we will use for collision detection
		// note: every tile in the map must have a blocked property!
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
		// clear screen to avoid blipping
		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// process user input
		handleInput();
		// set camera to follow player (needs to be reworked once we get teleporting down
		camera.position.set(player.getPosition().x, 94, 0);
		// update camera changes
		camera.update();
		// set the camera's view to the map
		renderer.setView(camera);
		// draw the map
		renderer.render();
		// if debug mode is on, draw collision boundaries
		if (debug) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.rect(player.getBound().x, player.getBound().y, player.getBound().width, player.getBound().height);
			for (Rectangle rect : blocks) {
				shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
			}
			shapeRenderer.end();
		}
		// draw the player
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(blob.getFrame(), blob.getPosition().x, blob.getPosition().y, 1, 1);
		spriteBatch.draw(player.getFrame(), player.getPosition().x, player.getPosition().y, 1, 1);
		spriteBatch.end();
		//player.drawPlayer(camera, blocks, debug);
	}
	
	private void handleInput() {
		// create booleans that determine whether a key was pressed
		boolean leftDown = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean rightDown = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		boolean spaceDown = Gdx.input.isKeyPressed(Input.Keys.SPACE);
		boolean dDown = Gdx.input.isKeyPressed(Input.Keys.D);
		// if player pressed d, toggle debug mode
		if (dDown) {
			if (debug) {
				debug = false;
			} else {
				debug = true;
			}
		}
		// if player pressed space and the player can move 1 block upward
		// and isn't still jumping, then set his y velocity to maximum
		if (spaceDown) {
			if (player.canMoveVertical(blocks, 1f) && !player.canJump()) {
				player.setJump(true);
				player.getVelocity().y = player.getMaxVel();
			}
		}
		// if player pressed left and the player can move .1 blocks left
		// set state to walking to show walking animation and move player left
		if (leftDown) {
			player.setFacingRight(false);
			if (player.canMoveLeft(blocks)) {
				player.moveLeft();
				player.setState(State.WALKING);
			}
		}
		// if player pressed right and the player can move .1 blocks right
		// set state to walking to show walking animation and move player right
		if (rightDown) {
			player.setFacingRight(true);
			if (player.canMoveRight(blocks)) {
				player.moveRight();
				player.setState(State.WALKING);
			}
		}
		// if player pressed both left and right, set player to idle
		if (rightDown && leftDown) {
			player.setState(State.IDLE);
		}
		// if player hasn't pressed left or right, set player to idle
		if (!(rightDown) && !(leftDown)) {
			player.setState(State.IDLE);
		}
		// if player can move in the y direction, apply gravity to y velocity
		if (player.canMoveVertical(blocks, player.getVelocity().y)) {
			if (player.getVelocity().y <= player.getGravity()) {
				player.getVelocity().y = player.getGravity();
				player.moveVertical(player.getVelocity().y);
			} else {
				player.getVelocity().y += player.getGravity();
				player.moveVertical(player.getVelocity().y);
			}
		} else if (player.getVelocity().y == player.getGravity()) {
			player.setJump(false);
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
