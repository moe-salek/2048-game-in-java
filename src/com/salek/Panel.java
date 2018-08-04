package com.salek;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Panel extends JPanel{

	private static Game game = new Game();
	final BufferedImage BACKGROUND = Image.background;
	private Color oldColor;
	private Color lightGrey = new Color(205, 205, 205);
	private Color redOne = new Color(200, 80, 100);
	private Color otherGrey = new Color(80, 80, 100);
	private Color gameOverColor = new Color(205, 205, 205, 200);

	private Stroke oldStroke;
	private Font font1, font2, font3;
	final int FONTSIZE1 = 16;
	final int FONTSIZE2 = 14;
	final int FONTSIZE3 = 26;
	final float THICKNES = 1.5F;
	static final int[] BARBOUND = {3, 3, Main.GAME_WIDTH - 6, 36};
	static final int[] RESETBUTTONBOUND = {Main.GAME_WIDTH-70, 7, 63, 28};
			
	public Panel() {
		game.setButtonBound(RESETBUTTONBOUND);
		addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) { }
			@Override
			public void keyPressed(KeyEvent e) {
				game.keyPressed(e);
			}
			@Override
			public void keyTyped(KeyEvent e) { }
		});
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				game.mouseClicked(e);
			}
		});
		setFocusable(true);

		font1 = new Font("Arial", Font.BOLD + Font.ITALIC, FONTSIZE1);
		font2 = new Font("Arial", Font.BOLD + Font.ITALIC, FONTSIZE2);
		font3 = new Font("Arial", Font.BOLD + Font.ITALIC, FONTSIZE3);

	}
	
	@Override
    public Dimension getPreferredSize()
    {
        return new Dimension(Main.GAME_WIDTH, Main.GAME_HEIGHT + Main.BAR_HEIGHT);
    }
	
	public static void reset() {
		game = new Game();
		game.setButtonBound(RESETBUTTONBOUND);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.drawImage(BACKGROUND, 0, Main.BAR_HEIGHT, null);
		
		oldColor = g2d.getColor();
		oldStroke = g2d.getStroke();
		
		g2d.setStroke(new BasicStroke(THICKNES));
		g2d.setColor(lightGrey);
		g2d.drawRoundRect(BARBOUND[0], BARBOUND[1], BARBOUND[2], BARBOUND[3], 5, 5);
		
		g2d.setColor(redOne);
		g2d.drawRoundRect(RESETBUTTONBOUND[0],
				RESETBUTTONBOUND[1],
				RESETBUTTONBOUND[2],
				RESETBUTTONBOUND[3],
				10, 10);
		g2d.setFont(font2);
		g2d.drawString("RESET",
				(RESETBUTTONBOUND[0]+RESETBUTTONBOUND[2]/2)-21,
				RESETBUTTONBOUND[1]+RESETBUTTONBOUND[3]/2+5);
		
		g2d.setColor(oldColor);
		g2d.setStroke(oldStroke);
		
		if(game.getGameIsOver() == true) {
			game.paint(g2d);
			oldColor = g2d.getColor();
			
			g2d.setColor(gameOverColor);
			g2d.fillRect(0,
					175,
					Main.GAME_WIDTH,
					125);
			
			g2d.setFont(font3);
			g2d.setColor(redOne);
			g2d.drawString("GAME OVER",
					135,
					244);
			
			g2d.setFont(font2);
			g2d.setColor(Color.darkGray);
			g2d.drawString("( click on the RESET button to start again )",
					75,
					270);
			
			g2d.setColor(oldColor);
		} else {
			game.update();
			game.paint(g2d);
		}
		
		g2d.setColor(otherGrey);
		g2d.setFont(font1);
        g2d.drawString(("SCORE: " + game.getScore()), 10 ,Main.BAR_HEIGHT/2 + FONTSIZE1/2); 
	}
	
}
