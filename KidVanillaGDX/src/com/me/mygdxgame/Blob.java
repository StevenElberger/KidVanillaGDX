package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Blob extends Entity {

	public Blob() {
	}
	
	public Blob(int hp, int x, int y, String sheetLocation, int frames, float width, float height) {
		super(hp, x, y, sheetLocation, frames, width, height);
	}
	
	public TextureRegion getFrame() {
		addStateTime(Gdx.graphics.getDeltaTime());
		setCurrentFrame(getLeftAnimation().getKeyFrame(getStateTime(),true));
		return getCurrentFrame();
	}
}
