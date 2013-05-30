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
	private Animation vanillaWalkLeft, vanillaWalkRight;
	private Texture vanillaSheet;
	private TextureRegion[] vanillaWalkRightFrames, vanillaWalkLeftFrames;
	private SpriteBatch spriteBatch;
	private TextureRegion currentFrame;
	private float width, height;
	public State state;
	private Boolean facingRight;
	
	public enum State {
		IDLE, WALKING, JUMPING
	}
	
	public Player() {
		width = 1;
		height = 1;
		state = State.IDLE;
		facingRight = true;
		position = new Vector2(3,95);
		vanillaSheet = new Texture(Gdx.files.internal("vanillawalkrightsprite.png"));
		TextureRegion[][] tmp = TextureRegion.split(vanillaSheet, 16, 16);
		vanillaWalkRightFrames = new TextureRegion[4];
		vanillaWalkRightFrames[0] = tmp[0][2];
		vanillaWalkRightFrames[1] = tmp[0][1];
		vanillaWalkRightFrames[2] = tmp[0][2];
		vanillaWalkRightFrames[3] = tmp[0][0];
		vanillaWalkLeftFrames = new TextureRegion[4];
		vanillaWalkLeftFrames[0] = new TextureRegion(tmp[0][2]);
		vanillaWalkLeftFrames[1] = new TextureRegion(tmp[0][1]);
		vanillaWalkLeftFrames[2] = new TextureRegion(tmp[0][2]);
		vanillaWalkLeftFrames[3] = new TextureRegion(tmp[0][0]);
		vanillaWalkLeftFrames[0].flip(true, false);
		vanillaWalkLeftFrames[1].flip(true, false);
		vanillaWalkLeftFrames[2].flip(true, false);
		vanillaWalkLeftFrames[3].flip(true, false);
		vanillaWalkRight = new Animation(0.1f, vanillaWalkRightFrames);
		vanillaWalkLeft = new Animation(0.1f, vanillaWalkLeftFrames);
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
	
	public State getState() {
		return state;
	}
	
	public void setX(float paramX) {
		position.x = paramX;
	}
	
	public void setY(float paramY) {
		position.y = paramY;
	}
	
	public void setState(State paramState) {
		state = paramState;
	}
	
	public void setFacingRight(boolean faceRight) {
		facingRight = faceRight;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}
	
	public void drawPlayer(OrthographicCamera camera) {
		stateTime += Gdx.graphics.getDeltaTime();
		if (this.state.equals(State.IDLE) && facingRight) {
			currentFrame = vanillaWalkRightFrames[2];
		} else if (this.state.equals(State.IDLE) && !facingRight) {
			currentFrame = vanillaWalkLeftFrames[2];
		} else if (this.state.equals(State.WALKING) && facingRight) {
			currentFrame = vanillaWalkRight.getKeyFrame(stateTime, true);
		} else {
			currentFrame = vanillaWalkLeft.getKeyFrame(stateTime, true);
		}
		
		//currentFrame = vanillaWalk.getKeyFrame(stateTime, true);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, position.x, position.y, width, height);
		spriteBatch.end();
	}

}
