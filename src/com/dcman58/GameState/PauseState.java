package com.dcman58.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Calendar;

import javax.imageio.ImageIO;

import com.dcman58.Audio.JukeBox;
import com.dcman58.Entity.PlayerSave;
import com.dcman58.Entity.UI.MenuItem;
import com.dcman58.Handlers.Keys;
import com.dcman58.Handlers.MouseHandler;
import com.dcman58.Main.GamePanel;

@SuppressWarnings("all")
public class PauseState extends GameState {

	private BufferedImage head;

	private int currentChoice = 0;
	private String[] options = { "Resume", "Main Menu", "Quit" };
	private MenuItem[] menuItems = new MenuItem[options.length];

	private Color titleColor;
	private Font titleFont;
	private Font subTitleFont;

	private Font font;
	private Font font2;
	private PlayerSave ps;

	private MouseHandler mh;

	public PauseState(GameStateManager gsm) {

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
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		// draw title
		g.setColor(new Color(255, 152, 92));
		g.setFont(titleFont);
		g.drawString("PAUSED:", ((GamePanel.WIDTH / 2) - (titleFont.getSize() + 20)), 50);

		g.setFont(font);
		g.setColor(Color.WHITE);

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

	}

	private void select() {
		JukeBox.stop("mainmenu");
		if (currentChoice == 0) {
			JukeBox.play("menuselect");
			gsm.setPaused(false);
		} else if (currentChoice == 1) {
			JukeBox.play("menuselect");
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		} else if (currentChoice == 2) {
			System.exit(0);
		}
	}

	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(false);
		if (Keys.isPressed(Keys.BUTTON1)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}
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
