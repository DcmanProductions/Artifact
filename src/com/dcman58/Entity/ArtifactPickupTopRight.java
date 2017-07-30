package com.dcman58.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.dcman58.TileMap.TileMap;

public class ArtifactPickupTopRight extends MapObject {

	Player player;
	private boolean remove = false;
	private HUD hud;

	private BufferedImage[] sprites;

	public ArtifactPickupTopRight(TileMap tm) {
		super(tm);
		player = new Player(tm);
		hud = new HUD(player);
		init();
	}

	public void init() {
		if (player.intersects(this)) {
			hud.showBottomLeft = true;
			remove = true;
		}
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Other/Artifact.png"));
			sprites = new BufferedImage[1];
			width = height = 4;
			sprites[0] = spritesheet.getSubimage(10, 0, 10, 10);
			animation.setFrames(sprites);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(){
		animation.update();
	}
	
	public boolean shouldRemove(){
		return remove;
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
	}

}
