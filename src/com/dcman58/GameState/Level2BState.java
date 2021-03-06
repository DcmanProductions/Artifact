package com.dcman58.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.dcman58.Audio.JukeBox;
import com.dcman58.Enemies.DarkEnergy;
import com.dcman58.Enemies.Gazer;
import com.dcman58.Enemies.GelPop;
import com.dcman58.Enemies.Tengu;
import com.dcman58.Entity.ArtifactPickupBottomLeft;
import com.dcman58.Entity.Enemy;
import com.dcman58.Entity.EnemyProjectile;
import com.dcman58.Entity.EnergyParticle;
import com.dcman58.Entity.Explosion;
import com.dcman58.Entity.HUD;
import com.dcman58.Entity.Player;
import com.dcman58.Entity.PlayerSave;
import com.dcman58.Entity.Teleport;
import com.dcman58.Entity.Title;
import com.dcman58.Handlers.Keys;
import com.dcman58.Main.GamePanel;
import com.dcman58.TileMap.Background;
import com.dcman58.TileMap.TileMap;

@SuppressWarnings("all")
public class Level2BState extends GameState {

	private Background sky;
	// private Background clouds;
	// private Background mountains;

	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<ArtifactPickupBottomLeft> artifactPickupBL;
	// private ArrayList<ArtifactPickupTopLeft> artifactPickupTL;
	// private ArrayList<ArtifactPickupBottomRight> artifactPickupBR;
	// private ArrayList<ArtifactPickupTopRight> artifactPickupTR;
	private ArrayList<EnemyProjectile> eprojectiles;
	private ArrayList<EnergyParticle> energyParticles;
	private ArrayList<Explosion> explosions;

	private HUD hud;
	private BufferedImage HeavenText;
	private Title title;
	private Title subtitle;
	private Teleport teleport;

	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;

	public Level2BState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	public void init() {
		PlayerSave.Save(GameStateManager.LEVEL2ASTATE, PlayerSave.LoadArtifactHUD(), PlayerSave.getHasTopLeft(), PlayerSave.getHasBottomLeft(), PlayerSave.getHasTopRight(), PlayerSave.getHasBottomRight());

		// backgrounds
		sky = new Background("/Backgrounds/Stars.png", 0);
		// clouds = new Background("/Backgrounds/clouds.gif", 0.1);
		// mountains = new Background("/Backgrounds/mountains.gif", 0.2);

		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/heaventileset.png");
		// tileMap.loadTiles("/Tilesets/firetileset.png");

		tileMap.loadMap("/Maps/level2b.map");
		tileMap.setPosition(0, 0);
		tileMap.setBounds(tileMap.getWidth() - 1 * tileMap.getTileSize(), tileMap.getHeight() - 2 * tileMap.getTileSize(), 0, 0);
		tileMap.setTween(1);

		// player
		player = new Player(tileMap);
		player.setPosition(73, 191);
		// player.setPosition(3900, 191);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());

		// Loads The Hud
		hud = new HUD(player);

		artifactPickupBL = new ArrayList<ArtifactPickupBottomLeft>();

		// enemies
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();
		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();

		// init player
		player.init(enemies, energyParticles);

		// explosions
		explosions = new ArrayList<Explosion>();

		// title and subtitle
		try {
			HeavenText = ImageIO.read(getClass().getResourceAsStream("/HUD/HeavenHUD.png"));
			title = new Title(HeavenText.getSubimage(0, 0, 300, 35));
			title.sety(60);
			subtitle = new Title(HeavenText.getSubimage(0, 36, 300, 30));
			subtitle.sety(85);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// teleport
		teleport = new Teleport(tileMap);
		teleport.setPosition(3700, 185);

		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();

		// sfx
		JukeBox.load("/SFX/teleport.mp3", "teleport");
		JukeBox.load("/SFX/explode.mp3", "explode");
		JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");

		// music
		JukeBox.load("/Music/level1v2.mp3", "level2");
		JukeBox.loop("level2", 600, JukeBox.getFrames("level2") - 2200);

	}

	private void populateEnemies() {
		enemies.clear();

		GelPop gp;
		Gazer g;
		DarkEnergy de;
		Tengu t;

		gp = new GelPop(tileMap, player);
		gp.setPosition(3749, 191);
		enemies.add(gp);

		gp = new GelPop(tileMap, player);
		gp.setPosition(1247, 101);
		enemies.add(gp);

		gp = new GelPop(tileMap, player);
		gp.setPosition(1247, 191);
		enemies.add(gp);

		gp = new GelPop(tileMap, player);
		gp.setPosition(1786, 191);
		enemies.add(gp);

		gp = new GelPop(tileMap, player);
		gp.setPosition(1738, 191);
		enemies.add(gp);

		gp = new GelPop(tileMap, player);
		gp.setPosition(2684, 191);
		enemies.add(gp);

		gp = new GelPop(tileMap, player);
		gp.setPosition(3373, 191);
		enemies.add(gp);

		gp = new GelPop(tileMap, player);
		gp.setPosition(3337, 191);
		enemies.add(gp);

		g = new Gazer(tileMap);
		g.setPosition(2071, 101);
		enemies.add(g);

		g = new Gazer(tileMap);
		g.setPosition(3075, 101);
		enemies.add(g);

		ArtifactPickupBottomLeft bottomLeftArtifact;
		bottomLeftArtifact = new ArtifactPickupBottomLeft(tileMap);
		bottomLeftArtifact.setPosition(1131, 191);

		if (!PlayerSave.getHasBottomLeft()) {
			artifactPickupBL.add(bottomLeftArtifact);
		}

	}

	public void update() {

		// check keys
		handleInput();
		
		

		PlayerSave.LoadArtifactHUD();

		// check if end of level event should start
		if (player.intersects(teleport)) {
			eventFinish = blockInput = true;
		}

		// check if player dead event should start
		if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}

//		System.out.println("Player Location: X:" + player.getx() + " Y: " + player.gety());

		// play events
		if (eventStart)
			eventStart();
		if (eventDead)
			eventDead();
		if (eventFinish)
			eventFinish();

		// move title and subtitle
		if (title != null) {
			title.update();
			if (title.shouldRemove())
				title = null;
		}
		if (subtitle != null) {
			subtitle.update();
			if (subtitle.shouldRemove())
				subtitle = null;
		}

		// move backgrounds
		// clouds.setPosition(tileMap.getx(), tileMap.gety());
		// mountains.setPosition(tileMap.getx(), tileMap.gety());

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
			if (e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
			}
		}

		// update enemy projectiles
		for (int i = 0; i < eprojectiles.size(); i++) {
			EnemyProjectile ep = eprojectiles.get(i);
			ep.update();
			if (ep.shouldRemove()) {
				eprojectiles.remove(i);
				i--;
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
		if (PlayerSave.getHasBottomRight()) {
			// System.out.println("In has Bottom Rights = " +
			// PlayerSave.getHasBottomRight());
			hud.showBottomRight = true;
		}

		if (player.intersects(new Rectangle(1131, 191, 32, 32)) && !artifactPickupBL.isEmpty() && !hud.showBottomLeft) {
			System.out.println("interact reached");
			hud.showBottomLeft = true;
			PlayerSave.hasBottomLeft = true;
			PlayerSave.Save(GameStateManager.LEVEL2ASTATE, 1, PlayerSave.getHasTopLeft(), PlayerSave.getHasBottomLeft(), PlayerSave.getHasTopRight(), PlayerSave.getHasBottomRight());
			System.out.println("Collected Bottom Left");
			artifactPickupBL.remove(0);
		}

		// update teleport
		teleport.update();

	}

	public void draw(Graphics2D g) {
		// draw background
		sky.draw(g);

		// draw tilemap
		tileMap.draw(g);

		// draw enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		for (int i = 0; i < artifactPickupBL.size(); i++) {
			artifactPickupBL.get(i).draw(g);
		}

		// draw enemy projectiles
		for (int i = 0; i < eprojectiles.size(); i++) {
			eprojectiles.get(i).draw(g);
		}

		// draw explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}

		// draw player
		player.draw(g);

		// draw teleport
		teleport.draw(g);

		// draw hud
		hud.draw(g);

		// draw title
		if (title != null)
			title.draw(g);
		if (subtitle != null)
			subtitle.draw(g);

		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for (int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
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
		player.setPosition(74, 161);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		tileMap.setShaking(false, 0);
		eventStart = true;
		eventStart();
		title = new Title(HeavenText.getSubimage(0, 0, 178, 20));
		title.sety(60);
		subtitle = new Title(HeavenText.getSubimage(0, 33, 91, 13));
		subtitle.sety(85);
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
			JukeBox.stop("level1");
		}
		if (eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if (eventCount == 30)
			title.begin();
		if (eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			subtitle.begin();
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
		if (eventCount == 1) {
			JukeBox.play("teleport");
			player.setTeleporting(true);
			player.stop();
		} else if (eventCount == 120) {
			tb.clear();
			tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount > 120) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
			JukeBox.stop("teleport");
		}
		if (eventCount == 180) {
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			JukeBox.stop("level2");
			gsm.setState(GameStateManager.LEVEL2CSTATE);
		}

	}

}
