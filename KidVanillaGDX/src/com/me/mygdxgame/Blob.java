package com.me.mygdxgame;

public class Blob extends Entity {

	public Blob() {
	}
	
	public Blob(int hp, int x, int y, String moveSheetLocation, String attackSheetLocation, int frames, float width, float height) {
		super(hp, x, y, moveSheetLocation, attackSheetLocation, frames, width, height);
	}
}
