package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Entity {
	public State state;
	private Vector2 velocity;
	private float gravity, maxvel;
	private boolean canJump, facingRight;
	
	public enum State {
		IDLE, WALKING, JUMPING;
	}
	
	public Player() {
	}
	
	public Player(int hp, int x, int y, String sheetLocation, int frames, float width, float height) {
		super(hp, x, y, sheetLocation, frames, width, height);
		maxvel = 0.5f;
		gravity = -0.1f;
		canJump = false;
		state = State.IDLE;
		facingRight = true;
		velocity = new Vector2(0, gravity);
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State paramState) {
		state = paramState;
	}
	
	public float getMaxVel() {
		return maxvel;
	}
	
	public float getGravity() {
		return gravity;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public boolean canJump() {
		return canJump;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}
	
	public void setJump(boolean jump) {
		canJump = jump;
	}
	
	public void setFacingRight(boolean faceRight) {
		facingRight = faceRight;
	}
	
	public TextureRegion getFrame() {
		addStateTime(Gdx.graphics.getDeltaTime());
		if (this.state.equals(State.IDLE) && facingRight) {
			setCurrentFrame(getRightFrame());
		} else if (this.state.equals(State.IDLE) && !facingRight) {
			setCurrentFrame(getLeftFrame());
		} else if (this.state.equals(State.WALKING) && facingRight) {
			setCurrentFrame(getRightAnimation().getKeyFrame(getStateTime(),true));
		} else if (this.state.equals(State.WALKING) && !facingRight){
			setCurrentFrame(getLeftAnimation().getKeyFrame(getStateTime(),true));
		}
		return getCurrentFrame();
	}
}
