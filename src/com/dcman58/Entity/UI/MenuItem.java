package com.dcman58.Entity.UI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.dcman58.Main.GamePanel;

public class MenuItem {

	public int startX, endX, startY, endY, width, height;
	public Graphics2D graphics;
	public String content;
	public boolean isSelected = false;

	public MenuItem(Graphics2D g, String text, int posX, int posY, int fontSize) {
		startX = posX;
		startY = posY;
		content = text;
		width = (content.length() * fontSize);
		height = fontSize;
		endX = (content.length() * fontSize) + startX;
		endY = fontSize + startY;
		graphics = g;
	}

	public void draw() {
		graphics.drawString(content, startX, startY);
		if (isSelected) {
			BufferedImage head;
			try {
				head = ImageIO.read(getClass().getResourceAsStream("/Sprites/Other/Artifact.png"));
				graphics.drawImage(head, startX - head.getWidth()-3 , startY - head.getHeight()+5, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
//			graphics.drawRect(startX - 3, startY - 12, width, height);

		}
	}

}
