package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class KidVanilla implements ApplicationListener {
	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	//private Animation vanillaWalk;
	//private Texture vanillaSheet;
	//private TextureRegion[] vanillaFrames;
	//private SpriteBatch spriteBatch;
	//private TextureRegion currentFrame;
	//private float stateTime;
	private Player player;
	
	@Override
	public void create() {		
		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();
		map = new TmxMapLoader().load("data/newmap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 100, 100);
		camera.position.set(5, 95, 0);	// Top left corner coords (0, 100)
		camera.zoom = .2f;	// Smaller means larger zoom
		
		player = new Player();
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
