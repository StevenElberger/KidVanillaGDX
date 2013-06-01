package com.me.mygdxgame;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity {
	private int health;
	private Vector2 position;
	private BoundingBox bound;
	private Texture spriteSheet;
	private SpriteBatch spriteBatch;
	private TextureRegion currentFrame;
	private Animation moveLeft, moveRight;
	private TextureRegion[] moveLeftFrames, moveRightFrames;
	
	public Entity() {
	}
	
	public Entity(int hp, Vector2 pos, String sheetLocation, int frames) {
		health = hp;
		position.x = pos.x;
		position.y = pos.y;
		bound = new BoundingBox(pos.x, pos.y, 1, 1);
		
		spriteSheet = new Texture(Gdx.files.internal(sheetLocation));
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, 16, 16);
		moveLeftFrames = new TextureRegion[frames];
		moveRightFrames = new TextureRegion[frames];
		for (int i = 0; i < frames; i++) {
			moveLeftFrames[i] = tmp[0][i];
		}		
		for (int j = 0; j < frames; j++) {
			moveRightFrames[j] = new TextureRegion(tmp[0][j]);
			moveRightFrames[j].flip(true, false);
		}
		moveLeft = new Animation(0.1f, moveLeftFrames);
		moveRight = new Animation(0.1f, moveRightFrames);
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
	
	public void moveLeft(ArrayList<Rectangle> blocks) {
		if (canMoveLeft(blocks)) {
			bound.x -= 0.1f;
			position.x -= 0.1f;
		}
	}
	
	public void moveRight(ArrayList<Rectangle> blocks) {
		if (canMoveRight(blocks)) {
			bound.x += 0.1f;
			position.x += 0.1f;
		}
	}
}