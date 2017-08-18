package com.dcman58.Entity.Pickups.Boosters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.dcman58.Entity.MapObject;
import com.dcman58.Entity.Player;
import com.dcman58.TileMap.TileMap;

public class BoosterPickupLife extends MapObject {

	Player player;
	private boolean remove = false;

	private BufferedImage[] sprites;

	public BoosterPickupLife(TileMap tm) {
		super(tm);
		player = new Player(tm);
		init();
	}

	public void init() {
		if (player.intersects(this)) {
			player.setLives(player.getLives()+1);
			remove = true;
		}
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Other/Boosters.png"));
			sprites = new BufferedImage[1];
			width = height = 4;
			sprites[0] = spritesheet.getSubimage(32, 0, 32, 32);
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
