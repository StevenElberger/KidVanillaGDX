package com.me.mygdxgame;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;

// import box2dLight.PointLight; --- used for testing lighting
//import box2dLight.RayHandler; // used for testing lighting
import com.badlogic.gdx.Input;
//import com.badlogic.gdx.math.Vector2; // used for testing lighting
import com.me.mygdxgame.Entity.State;
//import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.physics.box2d.World; // used for testing lighting
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class KidVanilla implements ApplicationListener {
	private Blob blob;
	private TiledMap map;
	private Player player;
	private boolean debug;
	//private World world; -- used for lighting
	//private RayHandler rayHandler;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private WorldRenderer worldRenderer;
	private ArrayList<Rectangle> blocks;
	private ArrayList<Entity> entityList;
	
	@Override
	public void create() {				
		// load map and map renderer (set scale to 1/16)
		map = new TmxMapLoader().load("data/newmap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);
		debug = false;
		
		// testing the new player class
		blob = new Blob(100, 7, 95, "blobsheet.png", "", 10, 0.8f, 0.95f);
		player = new Player(100, 3, 95, "vanillawalk.png", "staplesprite.png", 4, 0.8f, 0.95f);
		
		// testing crap
		entityList = new ArrayList<Entity>();
		entityList.add(blob);
		worldRenderer = new WorldRenderer();
		
		// create the player character and load the collision blocks
		createCollisionArray();
		
		// create the orthographic camera, show 10x10 tiles
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10, 10);
		camera.position.set(2, 94, 0);	// Top left corner is (0, 100)
		
		// testing lighting
		/*
		world = new World(new Vector2(), true);
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);
		//new PointLight(rayHandler, 128, new Color(0, 0, 0, 0), 1, 3, 95);
		*/
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
		//rayHandler.dispose();
	}

	@Override
	public void render() {
		// clear screen to avoid blipping
		//Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
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
		worldRenderer.renderWorld(camera, player, blocks, entityList, debug);
		// ----light tests
		// rayHandler.updateAndRender();
	}
	
	private void handleInput() {
		// create booleans that determine whether a key was pressed
		boolean leftDown = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean rightDown = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		boolean spaceDown = Gdx.input.isKeyPressed(Input.Keys.SPACE);
		boolean dDown = Gdx.input.isKeyPressed(Input.Keys.D);
		boolean aDown = Gdx.input.isKeyPressed(Input.Keys.A);
		// if player pressed d, toggle debug mode
		if (dDown) {
			if (debug) {
				debug = false;
			} else {
				debug = true;
			}
		}
		// if player pressed a, attack
		if (aDown && !leftDown && !rightDown && !spaceDown) {
			player.setState(State.ATTACKING);
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
			if (player.canMoveLeft(blocks)) {
				player.moveLeft();
			}
		}
		// if player pressed right and the player can move .1 blocks right
		// set state to walking to show walking animation and move player right
		if (rightDown) {
			if (player.canMoveRight(blocks)) {
				player.moveRight();
			}
		}
		// if player pressed both left and right, set player to idle
		if (rightDown && leftDown) {
			player.setState(State.IDLE);
		}
		// if player hasn't pressed left or right, set player to idle
		if (!(rightDown) && !(leftDown)) {
			if (!spaceDown && aDown) {
				player.setState(State.ATTACKING);
			} else {
				player.setState(State.IDLE);
			}
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