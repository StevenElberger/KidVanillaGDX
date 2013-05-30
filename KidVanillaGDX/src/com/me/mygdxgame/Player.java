package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private float stateTime;
	//private Vector2 gravity;
	private Vector2 position;
	private Animation vanillaWalk;
	private Texture vanillaSheet;
	private TextureRegion[] vanillaFrames;
	private SpriteBatch spriteBatch;
	private TextureRegion currentFrame;
	private float width, height;
	public State state;
	
	public enum State {
		IDLE, WALKING, JUMPING
	}
	
	public Player() {
		width = 1;
		height = 1;
		state = State.IDLE;
		position = new Vector2(2,94);
		vanillaSheet = new Texture(Gdx.files.internal("vanillawalkrightsprite.png"));
		TextureRegion[][] tmp = TextureRegion.split(vanillaSheet, 16, 16);
		vanillaFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			vanillaFrames[i] = tmp[0][i];
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
	
	public void drawPlayer(OrthographicCamera camera) {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = vanillaWalk.getKeyFrame(stateTime, true);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, position.x, position.y, width, height);
		spriteBatch.end();
	}

}
