package com.dcman58.Enemies.Boss;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.dcman58.Enemies.Gazer;
import com.dcman58.Enemies.GelPop;
import com.dcman58.Enemies.Tengu;
import com.dcman58.Entity.Enemy;
import com.dcman58.Entity.Explosion;
import com.dcman58.Entity.Player;
import com.dcman58.TileMap.TileMap;

@SuppressWarnings("all")
public class Satan extends Enemy {

	public BufferedImage[] sprites;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;

	private boolean active;
	private boolean finalAttack;

	private int step;
	private int stepCount;

	// attack pattern
	private int[] steps = { 0, 1, 0, 1, 2, 1, 0, 2, 1, 2 };

	//// attacks:
	// fly around throwing dark energy (0)
	// floor sweep (1)
	// crash down on floor to create shockwave (2)
	//// special:
	// after half hp, create shield
	// after quarter hp, bullet hell

	private Gazer[] shield;
	private double ticks;

	public Satan(TileMap tm, Player p, ArrayList<Enemy> enemies, ArrayList<Explosion> explosions) {
		super(tm);
		player = p;
		this.enemies = enemies;
		this.explosions = explosions;

		width = 40;
		height = 40;
		cwidth = 30;
		cheight = 30;
		health = maxHealth = 200;

		moveSpeed = 1.4;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Satan.png"));
			sprites = new BufferedImage[4];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		damage = 1;

		animation.setFrames(sprites);
		animation.setDelay(1);

		shield = new Gazer[2];

		step = 0;
		stepCount = 0;

	}

	public void setActive() {
		active = true;
	}

	public void update() {
		Tengu t;
		GelPop gp;
		if (health == 0)
			return;

		// restart attack pattern
		if (step == steps.length) {
			step = 0;
		}

		ticks++;

		if (flinching) {
			flinchCount++;
			if (flinchCount == 8)
				flinching = false;
		}

		x += dx;
		y += dy;

		animation.update();

		if (!active)
			return;

		////////////
		// special
		////////////
		if (health <= maxHealth / 2) {
			if (shield[0] == null) {
				shield[0] = new Gazer(tileMap);
				enemies.add(shield[0]);
			}
			if (shield[1] == null) {
				shield[1] = new Gazer(tileMap);
				enemies.add(shield[1]);
			}
			double pos = ticks / 32;
			shield[0].setPosition(x + 30 * Math.sin(pos), y + 30 * Math.cos(pos));
			pos += 3.1415;
			shield[1].setPosition(x + 30 * Math.sin(pos), y + 30 * Math.cos(pos));
		}

		if (!finalAttack && health <= maxHealth / 4) {
			stepCount = 0;
			finalAttack = true;
		}

		if (finalAttack) {
			stepCount++;
			if (stepCount == 1) {
				explosions.add(new Explosion(tileMap, (int) x, (int) y));
				x = -9000;
				y = 9000;
				dx = dy = 0;
			}
			if (stepCount == 60) {
				x = tileMap.getWidth() / 2;
				y = tileMap.getHeight() / 2;
				explosions.add(new Explosion(tileMap, (int) x, (int) y));
			}
			if (stepCount >= 90 && stepCount % 30 == 0) {
				gp = new GelPop(tileMap, player);
				gp.setPosition(x, y);
				gp.setVector(3 * Math.sin(stepCount / 32), 3 * Math.cos(stepCount / 32));
				enemies.add(gp);
			}
			if (stepCount == 90) {
				t = new Tengu(tileMap, player, enemies);
				t.setPosition(x, y);
				t.setVector(3 * Math.sin(stepCount / 32), 3 * Math.cos(stepCount / 32));
				enemies.add(t);
			}
			return;
		}

		////////////
		// attacks
		////////////

		// fly around dropping GelPops
		if (steps[step] == 0) {
			stepCount++;
			if (y > 60) {
				dy = -4;
			}
			if (y < 60) {
				dy = 0;
				y = 60;
				dx = -1;
			}
			if (y == 60) {
				if (dx == -1 && x < 60) {
					dx = 1;
				}
				if (dx == 1 && x > tileMap.getWidth() - 60) {
					dx = -1;
				}
			}
			if (stepCount % 60 == 0) {
				gp = new GelPop(tileMap, player);
				gp.setPosition(x, y);
				int dir = Math.random() < 0.5 ? 1 : -1;
				gp.setVector(dir, 0);
				enemies.add(gp);
			}
			if (stepCount == 559) {
				step++;
				stepCount = 0;
				right = left = false;
			}
		}
		// Spawn Tengu's
		else if (steps[step] == 1) {
			stepCount++;
			if (y > 60) {
				dy = -4;
			}
			if (y < 60) {
				dy = 0;
				y = 60;
				dx = -1;
			}
			if (y == 60) {
				if (dx == -1 && x < 60) {
					dx = 1;
				}
				if (dx == 1 && x > tileMap.getWidth() - 60) {
					dx = -1;
				}
			}
			if (stepCount % 80 == 0) {
				t = new Tengu(tileMap, player, enemies);
				t.setPosition(x, y);
				int dir = Math.random() < 0.5 ? 1 : -1;
				t.setVector(dir, 0);
				enemies.add(t);
			}
			if (stepCount == 559) {
				step++;
				stepCount = 0;
				right = left = false;
			}

		}
		// shockwave
		else if (steps[step] == 2) {
			stepCount++;
			if (stepCount == 1) {
				x = tileMap.getWidth() / 2;
				y = 40;
			}
			if (stepCount > 60 && stepCount < 90 && stepCount % 5 == 0 && dy == 0) {
				gp = new GelPop(tileMap, player);
				gp.setPosition(x, y);
				gp.setVector(-3, 0);
				enemies.add(gp);
			}
			if (stepCount == 120) {
				stepCount = 0;
				step++;
			}
		}
		t = new Tengu(tileMap, player, enemies);
		gp = new GelPop(tileMap, player);
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).isDead() && enemies.get(i).toString().contains("com.dcman58.Enemies.GelPop")) {
				this.health -= 1;
			} else if (enemies.get(i).isDead() && enemies.get(i).toString().contains("com.dcman58.Enemies.Tengu")) {
				this.health -= 4;
			}
		}

	}

	public void draw(Graphics2D g) {
		if (flinching) {
			if (flinchCount % 4 < 2)
				return;
		}
		super.draw(g);
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Health: " + health + "/" + maxHealth, 150, 230);
	}

}
