package com.me.mygdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private float stateTime;
	private Vector2 velocity;
	private Vector2 position;
	private Animation vanillaWalkLeft, vanillaWalkRight;
	private Texture vanillaSheet;
	private TextureRegion[] vanillaWalkRightFrames, vanillaWalkLeftFrames;
	private SpriteBatch spriteBatch;
	private TextureRegion currentFrame;
	private float width, height;
	public State state;
	private Boolean facingRight;
	private BoundingBox bound;
	private ShapeRenderer shapeRenderer;
	private float gravity, maxvel;
	private boolean jumping;
	
	public enum State {
		IDLE, WALKING, JUMPING
	}
	
	public Player() {
		width = 1;
		height = 1;
		state = State.IDLE;
		facingRight = true;
		jumping = false;
		gravity = -0.1f;
		maxvel = 0.5f;
		position = new Vector2(3,95);
		bound = new BoundingBox(3.1f, 95, 0.8f, 0.95f);
		shapeRenderer = new ShapeRenderer();
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
		velocity = new Vector2(0, gravity);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public boolean getJumping() {
		return jumping;
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
	
	public BoundingBox getBound() {
		return bound;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public float getMaxVelocity() {
		return maxvel;
	}
	
	public float getGravity() {
		return gravity;
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
	
	public void setJumping(boolean jump) {
		jumping = jump;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}
	
	public void drawPlayer(OrthographicCamera camera, ArrayList<Rectangle> blocks, boolean debug) {
		stateTime += Gdx.graphics.getDeltaTime();
		if (this.state.equals(State.IDLE) && facingRight) {
			currentFrame = vanillaWalkRightFrames[2];
		} else if (this.state.equals(State.IDLE) && !facingRight) {
			currentFrame = vanillaWalkLeftFrames[2];
		} else if (this.state.equals(State.WALKING) && facingRight) {
			currentFrame = vanillaWalkRight.getKeyFrame(stateTime, true);
		} else if (this.state.equals(State.WALKING) && !facingRight){
			currentFrame = vanillaWalkLeft.getKeyFrame(stateTime, true);
		}
		if (debug) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.rect(bound.x, bound.y, bound.width, bound.height);
			for (Rectangle rect : blocks) {
				shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
			}
			shapeRenderer.end();
		}
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, position.x, position.y, width, height);
		spriteBatch.end();
	}

}
