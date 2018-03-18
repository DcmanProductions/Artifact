package com.dcman58.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.dcman58.Audio.JukeBox;
import com.dcman58.Enemies.DarkEnergy;
import com.dcman58.Entity.ArtifactPickupTopLeft;
import com.dcman58.Entity.Enemy;
import com.dcman58.Entity.EnergyParticle;
import com.dcman58.Entity.Explosion;
import com.dcman58.Entity.HUD;
import com.dcman58.Entity.Player;
import com.dcman58.Entity.PlayerSave;
import com.dcman58.Entity.Boss.Satan;
import com.dcman58.Handlers.Keys;
import com.dcman58.Handlers.Debug;
import com.dcman58.Main.GamePanel;
import com.dcman58.TileMap.Background;
import com.dcman58.TileMap.TileMap;

@SuppressWarnings("all")
public class Level2CState extends GameState {

	private Background background;

	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnergyParticle> energyParticles;
	private ArrayList<Explosion> explosions;

	private HUD hud;
	ArrayList<ArtifactPickupTopLeft> artifactPickupTL;
	private Satan satan;

	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	private boolean eventPortal;
	private boolean flash;
	private boolean eventBossDead;

	public Level2CState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	public void init() {

		// backgrounds
		background = new Background("/Backgrounds/Stars.png", 0.5, 0);

		PlayerSave.LoadArtifactHUD();

		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/heaventileset.png");
		tileMap.loadMap("/Maps/level2c.map");
		tileMap.setPosition(140, 0);
		tileMap.setTween(1);

		// player
		player = new Player(tileMap);
		player.setPosition(50, 190);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());

		// explosions
		explosions = new ArrayList<Explosion>();

		// enemies
		enemies = new ArrayList<Enemy>();
		populateEnemies();

		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();

		// init player
		player.init(enemies, energyParticles);

		// hud
		hud = new HUD(player);

		// start event
		eventStart = blockInput = true;
		tb = new ArrayList<Rectangle>();
		eventStart();

		// sfx
		JukeBox.load("/SFX/teleport.mp3", "teleport");
		JukeBox.load("/SFX/explode.mp3", "explode");
		JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");

		// music
		JukeBox.load("/Music/level1boss.mp3", "level1boss");
		JukeBox.load("/Music/fanfare.mp3", "fanfare");

	}

	private void populateEnemies() {
		enemies.clear();
		satan = new Satan(tileMap, player, enemies, explosions);
		satan.setPosition(-9000, 9000);
		enemies.add(satan);
	}

	public void update() {

		// check keys
		handleInput();

		// check if player dead event should start
		if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}

		if (satan.isDead()) {
			eventBossDead = true;
		}

		// play events
		if (eventStart)
			eventStart();
		if (eventDead)
			eventDead();
		if (eventFinish)
			eventFinish();
		if (eventPortal)
			eventPortal();
		if (eventBossDead)
			eventBossDead();

		// move backgrounds
		background.setPosition(tileMap.getx(), tileMap.gety());

		// update player
		player.update();

		// update tilemap
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());
		tileMap.update();
		tileMap.fixBounds();

		// update enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if (e.isDead() || e.shouldRemove()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
			}
		}

		// update explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if (explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}

	}

	public void draw(Graphics2D g) {

		// draw background
		background.draw(g);

		// draw tilemap
		tileMap.draw(g);

		// draw enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		// draw explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}

		// draw player
		player.draw(g);

		// draw hud
		hud.draw(g);

		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for (int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
		// artifactPickupTL.get(0).draw(g);

		// flash
		if (flash) {
			g.setColor(java.awt.Color.WHITE);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}

	}

	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(true);
		if (blockInput || player.getHealth() == 0)
			return;
		player.setUp(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.BUTTON1]);
		player.setDashing(Keys.keyState[Keys.BUTTON2]);
		if (Keys.isPressed(Keys.BUTTON3))
			player.setAttacking();
		if (Keys.isPressed(Keys.BUTTON4))
			player.setCharging();
	}

	///////////////////////////////////////////////////////
	//////////////////// EVENTS
	///////////////////////////////////////////////////////

	// reset level
	private void reset() {
		player.reset();
		player.setPosition(50, 190);
		populateEnemies();
		eventStart = blockInput = true;
		eventCount = 0;
		eventStart();
	}

	// level started
	private void eventStart() {
		eventCount++;
		if (eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			JukeBox.stop("level2");
		}
		if (eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if (eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			eventPortal = blockInput = true;
			tb.clear();

		}
	}

	// player has died
	private void eventDead() {
		eventCount++;
		if (eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if (eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if (eventCount >= 120) {
			if (player.getLives() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
			} else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
			}
		}
	}

	// finished level
	private void eventFinish() {
		eventCount++;
		Debug.Log("Event Finish Level Initilized. Currnet Count:" + eventCount);
		if (eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount > 1) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if (eventCount >= 60) {
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			JukeBox.stop("level1boss");
			gsm.setState(GameStateManager.MENUSTATE);
		}

	}

	private void eventPortal() {
		eventCount++;
		if (eventCount == 1) {

		}
		if (eventCount > 60 && eventCount < 180) {
			energyParticles.add(new EnergyParticle(tileMap, 157, 107, (int) (Math.random() * 4)));
		}
		if (eventCount >= 160 && eventCount <= 180) {
			if (eventCount % 4 == 0 || eventCount % 4 == 1)
				flash = true;
			else
				flash = false;
		}
		if (eventCount == 181) {
			tileMap.setShaking(false, 0);
			flash = false;
			player.setEmote(Player.SURPRISED);
		}
		if (eventCount == 240) {
		}
		if (eventCount == 300) {
			player.setEmote(Player.NONE);
		}
		if (eventCount == 360) {
			flash = true;
			satan.setPosition(160, 160);
			DarkEnergy de;
			for (int i = 0; i < 20; i++) {
				de = new DarkEnergy(tileMap);
				de.setPosition(160, 160);
				de.setVector(Math.random() * 10 - 5, Math.random() * -2 - 3);
				enemies.add(de);
			}
		}
		if (eventCount == 362) {
			flash = false;
			JukeBox.loop("level1boss", 0, 60000, JukeBox.getFrames("level1boss") - 4000);
		}
		if (eventCount == 420) {
			eventPortal = blockInput = false;
			eventCount = 0;
			satan.setActive();
		}

	}

	public void eventBossDead() {
		eventCount++;
		
		PlayerSave.ResetGame();
		
		//PlayerSave.hasTopLeft = true;
		//PlayerSave.Save(GameStateManager.LEVEL3ASTATE, 3, true, PlayerSave.getHasBottomLeft(), PlayerSave.getHasTopRight(), PlayerSave.getHasBottomRight());
		if (eventCount == 1) {
			player.stop();
			JukeBox.stop("level1boss");
			enemies.clear();
		}
		if (eventCount <= 120 && eventCount % 15 == 0) {
			explosions.add(new Explosion(tileMap, satan.getx(), satan.gety()));
			JukeBox.play("explode");
		}
		if (eventCount == 180) {
			JukeBox.play("fanfare");
		}
		if (eventCount == 390) {
			eventBossDead = false;
			eventCount = 0;
			eventFinish = true;
		}
	}

}