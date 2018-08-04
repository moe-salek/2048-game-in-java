package com.salek;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Block {
	
	private BufferedImage pic;
	private int x, y;
	private int destinyX, destinyY;
	private int pastIndex, destinyIndex;
	private int value;	
	private boolean moving;
	private boolean moveUp, moveDown, moveLeft, moveRight;
	private int speed = 20;
		
	public Block(int[] pos, int value) {
		moving = moveUp = moveDown = moveLeft = moveRight = false;
		this.x = pos[0];
		this.y = pos[1];
		this.value = value;
		this.pic = Image.numberList[value];
	}
	
	public void paint(Graphics2D g2d) {
		update();
		g2d.drawImage(pic, (int) x, (int) y, null);
	}
	
	private void update() {
		if(moving == true) {
			if(moveLeft) {
				x = x - speed;
				if(x <= destinyX) {
					x = destinyX;
					moving = moveLeft = false;
				}
			} else if(moveRight) {
				x = x + speed;
				if(x >= destinyX) {
					x = destinyX;
					moving = moveRight = false;
				}
			} else if(moveUp) {
				y = y - speed;
				if(y <= destinyY) {
					y = destinyY;
					moving = moveUp = false;
				}
			} else if(moveDown) {
				y = y + speed;
				if(y >= destinyY) {
					y = destinyY;
					moving = moveDown = false;
				}
			}
		} 
	}
	
	public void moveTo(int pos[], int pastIndex, int destinyIndex) {
		this.destinyX = pos[0];
		this.destinyY = pos[1];
		this.pastIndex = pastIndex;
		this.destinyIndex = destinyIndex;
		this.moving = true;
		if(destinyX > x) moveRight = true;
		else if(destinyX < x) moveLeft = true;
		else if(destinyY > y) moveDown = true;
		else if(destinyY < y) moveUp = true;
	}
	
	public int[] getBlockXY() {
		return new int[] {x, y};
	}
	public int[] getBlockDestinyXY() {
		return new int[] {destinyX, destinyY};
	}
	public int getBlockPastIndex() {
		return pastIndex;
	}
	public int getBlockDestinyIndex() {
		return destinyIndex;
	}
	
	public boolean getIsMoving() {
		return moving;
	}
	
	public int getValue() {
		return value;
	}
	public void upgrade() {
		value = value + 1;
		pic = Image.numberList[value];
	}

}
