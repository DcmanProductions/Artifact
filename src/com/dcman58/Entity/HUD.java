package com.dcman58.Entity;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {

	private Player player;

	private BufferedImage heart;
	private BufferedImage life;
	private BufferedImage Artifact;
	private BufferedImage bottomLeft, bottomRight, topLeft, topRight;
	public boolean showBottomLeft, showBottomRight, showTopLeft, showTopRight;

	public HUD(Player p) {
		player = p;
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/HUD/Hud.gif"));
			Artifact = ImageIO.read(getClass().getResourceAsStream("/Sprites/Other/Artifact.png"));

			bottomLeft = Artifact.getSubimage(0, 10, 10, 10);
			bottomRight = Artifact.getSubimage(10, 10, 10, 10);
			topLeft = Artifact.getSubimage(0, 0, 10, 10);
			topRight = Artifact.getSubimage(10, 0, 10, 10);
			heart = image.getSubimage(0, 0, 13, 12);
			life = image.getSubimage(0, 12, 12, 11);

			showBottomLeft = PlayerSave.getHasBottomLeft();
			 showTopLeft = PlayerSave.getHasTopLeft();
			showTopRight = PlayerSave.getHasTopRight();
			showBottomRight = PlayerSave.getHasBottomRight();

			// System.out.println("HasBottom Right="+PlayerSave.hasBottomRight);

			// showBottomLeft = true;
//			showTopLeft = true;
			// showTopRight = true;
			// showBottomRight = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < player.getHealth(); i++) {
			g.drawImage(heart, 10 + i * 15, 10, null);
		}
		for (int i = 0; i < player.getLives(); i++) {
			g.drawImage(life, 10 + i * 15, 25, null);
		}
		if (showBottomLeft)
			g.drawImage(bottomLeft, 10, 50, null);
		if (showBottomRight)
			g.drawImage(bottomRight, 20, 50, null);
		if (showTopLeft)
			g.drawImage(topLeft, 10, 40, null);
		if (showTopRight)
			g.drawImage(topRight, 20, 40, null);

		g.setColor(java.awt.Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString(player.getTimeToString(), 280, 15);
	}

}
