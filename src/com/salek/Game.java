package com.salek;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
	
	private Random random = new Random();
	private int score;
	private int numberOfBlocks = 16; 
	private Block[] blocks = new Block[numberOfBlocks];
	private int[][] screen = new int[numberOfBlocks][2]; // ID, Value 
	boolean[] keyboard = new boolean[5];
	private final int[][] positions = new int[numberOfBlocks][2];
	private List<Block> movingBlocksList = new ArrayList<>();
	private boolean blockIsMoving = false;
	private boolean blockMoved = true;
	private boolean generateRandomBlock = false;
	private boolean gameIsOver = false;

	private boolean[] upgradeSign = new boolean[numberOfBlocks];
	private boolean[] upgradedBlocks = new boolean[numberOfBlocks];
	private Block[] tempBlock4Show = new Block[numberOfBlocks];
	
	private int[] buttonBound = new int[4];

	
	public Game() {
		score = 0;
		for(int i = 0; i < numberOfBlocks; i++) {
			screen[i][0] = -1;
			screen[i][1] = -1;
		}
		
		positions[0][0] = 5;
		positions[4][0] = 5;
		positions[8][0] = 5;
		positions[12][0] = 5;
		positions[1][0] = 110;
		positions[5][0] = 110;
		positions[9][0] = 110;
		positions[13][0] = 110;
		positions[2][0] = 214;
		positions[6][0] = 214;
		positions[10][0] = 214;
		positions[14][0] = 214;
		positions[3][0] = 318;
		positions[7][0] = 318;
		positions[11][0] = 318;
		positions[15][0] = 318;
		
		positions[0][1] = 6;
		positions[1][1] = 6;
		positions[2][1] = 6;
		positions[3][1] = 6;
		positions[4][1] = 108;
		positions[5][1] = 108;
		positions[6][1] = 108;
		positions[7][1] = 108;
		positions[8][1] = 210;
		positions[9][1] = 210;
		positions[10][1] = 210;
		positions[11][1] = 210;
		positions[12][1] = 312;
		positions[13][1] = 312;
		positions[14][1] = 312;
		positions[15][1] = 312;
		
		for(int i = 0; i < numberOfBlocks; i++) {
			positions[i][0] += 0;
			positions[i][1] += Main.BAR_HEIGHT;
		}
		
		makeRandomBlock();
		blockMoved = true;
		makeRandomBlock();
	}
	
	private void gameOver() {
		System.out.println("Game Over!");
		gameIsOver = true;
//		System.exit(0);
	}
	
	private void createBlock(int blockScreenIndex) {
		int value = random.nextInt(10) == 9 ? 1 : 0;
		blocks[blockScreenIndex] = new Block(positions[blockScreenIndex], value);
		screen[blockScreenIndex][0] = blockScreenIndex;
		screen[blockScreenIndex][1] = value;
	}
	
	private void makeRandomBlock() {
		boolean makeNewBlock = true;
		List<Integer> space = new ArrayList<>();
		for(int i = 0; i < numberOfBlocks; i++) {
			if(screen[i][0] == -1) {
				space.add(i);
			}
		}
		if(space.size() == 0) {
			makeNewBlock = false;
			if(canMove() == false) {
				gameOver();
			}
		}
		
		if(makeNewBlock == true) {
			int randomBlockNumber =  space.get(random.nextInt(space.size()));
			if(blockMoved == true) {
				createBlock(randomBlockNumber);
				blockMoved = false;
			}
		}
	}
	
	private boolean canMove() {
		for(int i = 0; i < numberOfBlocks; i++) {
			if(i % 4 == 0) i++;
			if(screen[i - 1][1] == screen[i][1]) return true;
		}
		for(int i = 0; i < numberOfBlocks; i++) {
			if(i < 3) i = 4;
			if(screen[i - 4][1] == screen[i][1]) return true;
		}
		return false;
	}

	private void move1st(int fromScreenIndex, int toScreenIndex) {
		// before moving
		int[] pos = positions[toScreenIndex];
		blocks[fromScreenIndex].moveTo(pos, fromScreenIndex, toScreenIndex);
		movingBlocksList.add(blocks[fromScreenIndex]);
		blockIsMoving = true;
	

		if(screen[toScreenIndex][0] != -1) {
			if(screen[fromScreenIndex][1] == screen[toScreenIndex][1] ) {
				upgradeSign[toScreenIndex] = true;
				screen[fromScreenIndex][1]++;
				upgradedBlocks[toScreenIndex] = true;
			}
		}

		if(movingBlocksList.indexOf(blocks[toScreenIndex]) != -1) {
			movingBlocksList.remove(movingBlocksList.indexOf(blocks[toScreenIndex]));
		}
		
		if(blocks[toScreenIndex] != null) {
			tempBlock4Show[toScreenIndex] = blocks[toScreenIndex];
		}
		blocks[toScreenIndex] = blocks[fromScreenIndex];
		blocks[fromScreenIndex] = null;
		
		screen[toScreenIndex][0] = toScreenIndex;
		screen[toScreenIndex][1] = screen[fromScreenIndex][1];

		screen[fromScreenIndex][0] = -1;
		screen[fromScreenIndex][1] = -1;
		
	}
	
	private void move2nd(int fromScreenIndex, int toScreenIndex) {
		// after reached		
		movingBlocksList.remove(movingBlocksList.indexOf(blocks[toScreenIndex]));
				
		for(int i = 0; i < numberOfBlocks; i++) {
			if(upgradeSign[i] == true) {
				blocks[i].upgrade();
				upgradeSign[i] = false;
				score += Math.pow(2, screen[i][1]);
			}
		}
		
		if(movingBlocksList.size() == 0) {
			blockIsMoving = false;
		}

	}
	
	private void moveLeft() {
		for(int i = 0; i < numberOfBlocks; i++) {
			if((i%4 != 0) && screen[i][0] != -1) {
				int destiny = i - 1;
				if((screen[destiny][0] == -1) || (screen[i][1] == screen[destiny][1])) {
					while(((destiny-1) >= 0) 
							&& ((screen[destiny-1][0] == -1)
									|| (screen[i][1] == screen[destiny-1][1]))
									&& ((destiny-1) % 4 != 3)
									&& (isBlockUpgraded(destiny-1) == false) ) {
						destiny = destiny-1;
					}
					move1st(i, destiny);
					blockMoved = true;
				}
			}
		}
		keyboard[0] = false;
	}
	
	private void moveRight() {
		for(int i = numberOfBlocks - 1; i >= 0; i--) {
			if(i % 4 != 3 && screen[i][1] != -1) {
				int destiny = i + 1;
				if((screen[destiny][0] == -1) || (screen[i][1] == screen[destiny][1])) {
					while(((destiny+1) <= 15) 
							&& ((screen[destiny+1][0] == -1)
									|| (screen[i][1] == screen[destiny+1][1]))
									&& ((destiny+1) % 4 != 0) 
									&& (isBlockUpgraded(destiny+1) == false) ) {
						destiny = destiny+1;
					}
					move1st(i, destiny);
					blockMoved = true;
				}
			}
		}
		keyboard[1] = false;
	}

	private void moveUp() {
		for(int m = 0; m < 4; m++) {
			for(int n = 0; n < 4; n++) {
				int i = m + 4 * n;	
				if((i >= 4) && screen[i][0] != -1) {
					int destiny = i-4;
					if((screen[destiny][0] == -1) || (screen[i][1] == screen[destiny][1])) {
						while(((destiny-4) >= 0) 
								&& ((screen[destiny-4][0] == -1) 
								|| (screen[i][1] == screen[destiny-4][1])) 
								&& (isBlockUpgraded(destiny-4) == false) ){
							destiny = destiny-4;
						}
						move1st(i, destiny);
						blockMoved = true;
					}
				}
			}
		}
		keyboard[2] = false;
	}


	private void moveDown() {
		for(int m = 4 - 1; m >= 0; m--) {
			for(int n = 4 - 1; n >= 0; n--) {
				int i = m + 4 * n;	
				if(i < (16 - 4) && screen[i][0] != -1) {
					int destiny = i+4;
					
					if((screen[destiny][0] == -1) || (screen[i][1] == screen[destiny][1])) {
						while(((destiny+4) <= 15) 
								&& ((screen[destiny+4][0] == -1) 
								|| (screen[i][1] == screen[destiny+4][1]))
								&& (isBlockUpgraded(destiny+4) == false) ){
							destiny = destiny+4;
						}
						move1st(i, destiny);
						blockMoved = true;
					}
				}
			}
		}
		keyboard[3] = false;
	}

	
	private void checkBlockMoving() {
		for(int i = 0; i < movingBlocksList.size(); i++) {
			Block blk = movingBlocksList.get(i);
			int[] pos = blk.getBlockXY();
			int[] desPos = blk.getBlockDestinyXY();
			if((pos[0] == desPos[0]) && (pos[1] == desPos[1])) {
				move2nd(blk.getBlockPastIndex(), blk.getBlockDestinyIndex());
			}
		}
	}
	
	public void paint(Graphics2D g2d) {
		for(int i = 0; i < numberOfBlocks; i++) {
			if(blocks[i] != null) {
				blocks[i].paint(g2d);
			}
		}

		// Bug fixed: show the blocks until they upgrade
		if(blockIsMoving == true) {
			for(int i = 0; i < numberOfBlocks; i++) {
				if(tempBlock4Show[i] != null) {
					tempBlock4Show[i].paint(g2d);
				}
			}
		}
	}
	
	public void update() {
		keyboardChecker();
		checkBlockMoving();
		if(generateRandomBlock == true && blockIsMoving == false) {
			makeRandomBlock();
			generateRandomBlock = false;
			blockMoved = false;
			
			for(int i = 0; i < numberOfBlocks; i++) {
				upgradedBlocks[i] = false;
			}
			
			for(int i = 0; i < numberOfBlocks; i++) {
				tempBlock4Show[i] = null;
			}
		}
	}
	
	private void keyboardChecker() {
		boolean makeNewOne = false;
		if(keyboard[0] == true) {
			moveLeft();
			makeNewOne = true;
		}
		else if(keyboard[1] == true) {
			moveRight();
			makeNewOne = true;
		}
		else if(keyboard[2] == true) {
			moveUp();
			makeNewOne = true;
		}
		else if(keyboard[3] == true) {
			moveDown();
			makeNewOne = true;
		}
		
		if(makeNewOne == true) {
			generateRandomBlock = true;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(blockIsMoving == false) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				keyboard[0] = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				keyboard[1] = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				keyboard[2] = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				keyboard[3] = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			keyboard[0] = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			keyboard[1] = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			keyboard[2] = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			keyboard[3] = false;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		int x, y;
		x = e.getX();
		y = e.getY();
		if(
				(x > buttonBound[0]) &&
				(y > buttonBound[1]) &&
				(x < buttonBound[2]+buttonBound[0]) &&
				(y < buttonBound[3]+buttonBound[1])
				) {
			Panel.reset();
		}
	}
	
	
//	private void printValues() {
//		for(int i = 0; i < numberOfBlocks; i++) {
//			if(i%4 != 3) {
//				System.out.print(screen[i][1] + " ");
//			}else {
//				System.out.print(screen[i][1] + " ");
//				System.out.println();
//			}
//		}
//		System.out.println();
//		System.out.println();
//	}
//	private void printBlocks() {
//		System.out.println("Blocks:");
//		for(int i = 0; i < numberOfBlocks; i++) {
//			if(i%4 != 3) {
//				System.out.print(blocks[i] + " ");
//			}else {
//				System.out.print(blocks[i]  + " ");
//				System.out.println();
//			}
//		}
//		System.out.println();
//		System.out.println();
//	}
//	
//	private void reverseLast() {
//		
//	}
	
	private boolean isBlockUpgraded(int screenIndex) {
		if(upgradedBlocks[screenIndex] == true) {
			return true;
		}
		return false;
	}
	
	public int getNumberOfBlocks() {
		return numberOfBlocks;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setButtonBound(int[] bounds) {
		buttonBound[0] = bounds[0];
		buttonBound[1] = bounds[1];
		buttonBound[2] = bounds[2];
		buttonBound[3] = bounds[3];
	}
	
	public boolean getGameIsOver() {
		return gameIsOver;
	}
}
