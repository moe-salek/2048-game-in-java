package com.salek;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image {
	
	static BufferedImage background, icon;
	static BufferedImage[] numberList = new BufferedImage[16];
	
	public static void load() throws IOException {
		icon = ImageIO.read(Image.class.getResource("/icon.png"));
		background = ImageIO.read(Image.class.getResource("/background.png"));
		numberList[0] = ImageIO.read(Image.class.getResource("/n2.png"));
		numberList[1] = ImageIO.read(Image.class.getResource("/n4.png"));
		numberList[2] = ImageIO.read(Image.class.getResource("/n8.png"));
		numberList[3] = ImageIO.read(Image.class.getResource("/n16.png"));
		numberList[4] = ImageIO.read(Image.class.getResource("/n32.png"));
		numberList[5] = ImageIO.read(Image.class.getResource("/n64.png"));
		numberList[6] = ImageIO.read(Image.class.getResource("/n128.png"));
		numberList[7] = ImageIO.read(Image.class.getResource("/n256.png"));
		numberList[8] = ImageIO.read(Image.class.getResource("/n512.png"));
		numberList[9] = ImageIO.read(Image.class.getResource("/n1024.png"));
		numberList[10] = ImageIO.read(Image.class.getResource("/n2048.png"));
		numberList[11] = ImageIO.read(Image.class.getResource("/n4096.png"));
		numberList[12] = ImageIO.read(Image.class.getResource("/n8192.png"));
		numberList[13] = ImageIO.read(Image.class.getResource("/n16384.png"));
		numberList[14] = ImageIO.read(Image.class.getResource("/n32768.png"));
		numberList[15] = ImageIO.read(Image.class.getResource("/n65536.png"));
	}
}
