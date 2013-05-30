package com.me.mygdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class BoundingBox extends Rectangle {
	
	public BoundingBox(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public boolean moveSideways(ArrayList<Rectangle> blocks, float diffX) {
		boolean canMove = true;		
		// Move rectangle to where player would move
		this.setX(this.getX() + diffX);
		// If this box contains another, return false
		for (Rectangle rect : blocks) {
			if (this.overlaps(rect)) {
				System.out.println(rect.getX() + " " + rect.getY());
				canMove = false;
			}
		}
		this.setX(this.getX() - diffX);
		return canMove;
	}
	
	public boolean moveVertical(ArrayList<Rectangle> blocks, float diffY) {
		boolean canMove = true;
		this.setY(this.getY() + diffY);
		for (Rectangle rect : blocks) {
			if (this.overlaps(rect)) {
				canMove = false;
			}
		}
		this.setY(this.getY() - diffY);	
		return canMove;
	}
}
