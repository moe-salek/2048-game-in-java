package com.salek;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
	
    final static int GAME_WIDTH = 425;
    final static int GAME_HEIGHT = 415;
	final static int BAR_HEIGHT = 40;

	public static void main(String[] args) {	
		
	    long startTime, Time;
	    final long miliSec = 1000L;
	    final int fps = 60;
	    final int updateTime = (int) (miliSec / fps);
	    
    	try {
    		Image.load();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		Panel myPanel = new Panel();
		JFrame frame = new JFrame();  
    	frame.setIconImage(Image.icon);
		frame.setSize(GAME_WIDTH, GAME_HEIGHT + BAR_HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("2048 - a super-original game by @m-salek");
		frame.add(myPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
				
		try { 
			while(true) {
				startTime = System.nanoTime();
				myPanel.repaint();
				Time = (int) Math.ceil((double) (System.nanoTime() - startTime)/1000000);
				Thread.sleep(updateTime -  Time);
			}
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}

