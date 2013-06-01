package com.me.mygdxgame;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity {
	private int health;
	private float stateTime;
	private Vector2 position;
	private BoundingBox bound;
	private Texture spriteSheet;
	private TextureRegion currentFrame;
	private Animation moveLeft, moveRight;
	private TextureRegion[] moveLeftFrames, moveRightFrames;
	
	public Entity() {
	}
	
	public Entity(int hp, int x, int y, String sheetLocation, int frames) {
		// Set up health, position, and bounding box
		health = hp;
		stateTime = 0f;
		position = new Vector2(x, y);
		bound = new BoundingBox(x, y, 1, 1);
		
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
	
	public int getHealth() {
		return health;
	}
	
	public float getStateTime() {
		return stateTime;
	}
	
	public void addStateTime(float delta) {
		stateTime += delta;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public BoundingBox getBound() {
		return bound;
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
	
	public void moveLeft() {
		bound.x -= 0.1f;
		position.x -= 0.1f;
	}
	
	public void moveRight() {
		bound.x += 0.1f;
		position.x += 0.1f;
	}
	
	public void moveVertical(float diffY) {
		bound.y += diffY;
		position.y += diffY;
	}
	
	public TextureRegion getLeftFrame() {
		return moveLeftFrames[0];
	}
	
	public TextureRegion getRightFrame() {
		return moveRightFrames[0];
	}
	
	public Animation getLeftAnimation() {
		return moveLeft;
	}
	
	public Animation getRightAnimation() {
		return moveRight;
	}
	
	public void setCurrentFrame(TextureRegion paramCurrentFrame) {
		currentFrame = paramCurrentFrame;
	}
	
	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}
}