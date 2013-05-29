package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
	private float x,y;
	private float stateTime;
	private Animation vanillaWalk;
	private Texture vanillaSheet;
	private TextureRegion[] vanillaFrames;
	private SpriteBatch spriteBatch;
	private TextureRegion currentFrame;
	
	public Player() {
		x = 200;
		y = 320;
		vanillaSheet = new Texture(Gdx.files.internal("vanillawalkrightsprite.png"));
		TextureRegion[][] tmp = TextureRegion.split(vanillaSheet, 16, 16);
		vanillaFrames = new TextureRegion[3];
		int index = 0;
		for (int i = 0; i < 3; i++) {
			vanillaFrames[index++] = tmp[0][i];
		}
		vanillaWalk = new Animation(0.025f, vanillaFrames);
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}
	
	public void drawPlayer() {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = vanillaWalk.getKeyFrame(stateTime, true);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, x, y);
		spriteBatch.end();
	}

}
