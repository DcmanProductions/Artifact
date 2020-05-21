package com.dcman58.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Calendar;

import javax.imageio.ImageIO;

//import org.lwjgl.input.Mouse;

import com.dcman58.Audio.JukeBox;
import com.dcman58.Entity.PlayerSave;
import com.dcman58.Entity.UI.MenuItem;
import com.dcman58.Entity.UI.SliderItem;
import com.dcman58.Handlers.Keys;
import com.dcman58.Handlers.MouseHandler;
import com.dcman58.Main.GamePanel;

@SuppressWarnings("all")
public class MenuState extends GameState {

	private BufferedImage head;

	private int currentChoice = 0;
	private String[] options = { "Continue", "New Game", "About", "Quit" };
	private MenuItem[] menuItems = new MenuItem[options.length];

	private Color titleColor;
	private Font titleFont;
	private Font subTitleFont;

	private Font font;
	private Font font2;
	private PlayerSave ps;

	private MouseHandler mh;

	public MenuState(GameStateManager gsm) {

		super(gsm);

		JukeBox.load("/Music/mainmenu.mp3", "mainmenu");
		JukeBox.loop("mainmenu");

		JukeBox.stop("level1");
		JukeBox.stop("level1boss");
		JukeBox.stop("level1B");
		JukeBox.stop("level2");
		JukeBox.stop("level3");

		try {

			// load floating head
			head = ImageIO.read(getClass().getResourceAsStream("/Sprites/Other/Artifact.png"));

			// titles and fonts
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN, 28);
			subTitleFont = new Font("Times New Roman", Font.PLAIN, 18);
			font = new Font("Arial", Font.PLAIN, 14);
			font2 = new Font("Arial", Font.PLAIN, 10);

			// load sound fx
			JukeBox.load("/SFX/menuoption.mp3", "menuoption");
			JukeBox.load("/SFX/menuselect.mp3", "menuselect");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void init() {
	}

	public void update() {

		// check keys
		handleInput();

	}

	public void draw(Graphics2D g) {

		// draw bg
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		// draw title
		g.setColor(new Color(255, 152, 92));
		g.setFont(titleFont);
		g.drawString("A R T I F A C T:", 20, 50);

		g.setFont(subTitleFont);
		g.setColor(new Color(255, 255, 162));
		g.drawString("T H E  J O U R N E Y ", 80, 75);
		g.drawString("U N R A V E L E D", 60, 100);

		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		for (int i = 0; i < options.length; i++) {
			menuItems[i] = new MenuItem(g, options[i], ((GamePanel.WIDTH / 2) - (font.getSize() + 20)), ((GamePanel.HEIGHT / 2) + (font.getSize() * i) + 5 * i) + 18, font.getSize());
			menuItems[i].draw();
		}

		// draw floating head
		for (int i = 0; i < options.length; i++) {
			if (currentChoice == i) {
				menuItems[i].isSelected = true;
				menuItems[i].draw();
			} else {
				menuItems[i].isSelected = false;
			}
		}

		// other
		g.setFont(font2);
		g.drawString("2010-" + Calendar.getInstance().get(Calendar.YEAR) + " (c) Drew Chase", 10, 232);
//		new SliderItem(10, 232-50, GamePanel.WIDTH / 2, 10, -80, 0, 0, 8, g).draw();

	}

	private void select() {
		JukeBox.stop("mainmenu");
		if (currentChoice == 0) {
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setState(PlayerSave.LoadLevel());
		} else if (currentChoice == 1) {
			PlayerSave.ResetGame();
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setState(GameStateManager.LEVEL1ASTATE);
		} else if (currentChoice == 2) {
			gsm.setState(GameStateManager.About);
		} else if (currentChoice == 3) {
			System.exit(0);
		}
	}

	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER))
			select();
		if (Keys.isPressed(Keys.UP)) {
			JukeBox.play("menuoption", 0);
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if (Keys.isPressed(Keys.DOWN)) {
			JukeBox.play("menuoption", 0);
			currentChoice++;
			if (currentChoice >= options.length) {
				currentChoice = 0;
			}
		}
	}

}
