package com.me.mygdxgame;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity {
	//private int health; -- add in when we get to combat!
	private State state;
	private float stateTime;
	private Vector2 position;
	private BoundingBox bound;
	private boolean facingRight;
	private Texture spriteSheet;
	private TextureRegion currentFrame;
	private Animation moveLeft, moveRight;
	private TextureRegion[] moveLeftFrames, moveRightFrames;
	
	public enum State {
		IDLE, WALKING, JUMPING;
	}
	
	public Entity() {
	}
	
	public Entity(int hp, int x, int y, String sheetLocation, int frames, float width, float height) {
		// Set up stats
		//health = hp;
		stateTime = 0f;
		facingRight = true;
		state = State.IDLE;
		position = new Vector2(x, y);
		bound = new BoundingBox(x + 0.1f, y, width, height);
		
		// Load up the sprite sheet
		spriteSheet = new Texture(Gdx.files.internal(sheetLocation));
		// Split up the sprite sheet
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, 16, 16);
		// Create the texture regions to hold each frame of the animation
		moveLeftFrames = new TextureRegion[frames];
		moveRightFrames = new TextureRegion[frames];
		// Load the texture regions with the sprites
		for (int i = 0; i < frames; i++) {
			moveLeftFrames[i] = tmp[0][i];
		}
		// Flip the sprites for the other direction
		for (int j = 0; j < frames; j++) {
			moveRightFrames[j] = new TextureRegion(tmp[0][j]);
			moveRightFrames[j].flip(true, false);
		}
		// Create the animations
		moveLeft = new Animation(0.1f, moveLeftFrames);
		moveRight = new Animation(0.1f, moveRightFrames);
	}
	
	public void moveLeft() {
		state = State.WALKING;
		facingRight = false;
		bound.x -= 0.1f;
		position.x -= 0.1f;
	}
	
	public void moveRight() {
		state = State.WALKING;
		facingRight = true;
		bound.x += 0.1f;
		position.x += 0.1f;
	}
	
	public State getState() {
		return state;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public BoundingBox getBound() {
		return bound;
	}
	
	public void moveVertical(float diffY) {
		bound.y += diffY;
		position.y += diffY;
	}
	
	public void setState(State paramState) {
		state = paramState;
	}
	
	public boolean canMoveLeft(ArrayList<Rectangle> blocks) {
		return bound.moveSideways(blocks, -0.1f);
	}
	
	public boolean canMoveRight(ArrayList<Rectangle> blocks) {
		return bound.moveSideways(blocks, 0.1f);
	}
	
	public boolean canMoveVertical(ArrayList<Rectangle> blocks, float diffY) {
		return bound.moveVertical(blocks, diffY);
	}
	
	// Decide on which frame or animation to use depending upon
	// the state of the entity, then return it
	public TextureRegion getFrame() {
		stateTime += Gdx.graphics.getDeltaTime();
		if (this.state.equals(State.IDLE) && facingRight) {
			currentFrame = moveRightFrames[0];
		} else if (this.state.equals(State.IDLE) && !facingRight) {
			currentFrame = moveLeftFrames[0];
		} else if (this.state.equals(State.WALKING) && facingRight) {
			currentFrame = moveRight.getKeyFrame(stateTime, true);
		} else if (this.state.equals(State.WALKING) && !facingRight) {
			currentFrame = moveLeft.getKeyFrame(stateTime, true);
		}
		return currentFrame;
	}
}