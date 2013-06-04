package com.me.mygdxgame;

import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
	public State state;
	private Vector2 velocity;
	private float gravity, maxvel;
	private boolean canJump;
	
	public Player() {
	}
	
	public Player(int hp, int x, int y, String moveSheetLocation, String attackSheetLocation, int frames, float width, float height) {
		super(hp, x, y, moveSheetLocation, attackSheetLocation, frames, width, height);
		maxvel = 0.65f;
		gravity = -0.1f;
		canJump = false;
		state = State.IDLE;
		velocity = new Vector2(0, gravity);
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
	
	public void setJump(boolean jump) {
		canJump = jump;
	}
}