package com.dcman58.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.dcman58.Audio.JukeBox;
import com.dcman58.Entity.PlayerSave;
import com.dcman58.Handlers.Keys;
import com.dcman58.Main.GamePanel;

@SuppressWarnings("all")
public class MenuState extends GameState {

	private BufferedImage head;

	private int currentChoice = 0;
	private String[] options = { "Start", "About", "Quit" };

	private Color titleColor;
	private Font titleFont;
	private Font subTitleFont;

	private Font font;
	private Font font2;
	private PlayerSave ps;

	public MenuState(GameStateManager gsm) {

		super(gsm);

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
		g.setColor(new Color(255,152,92));
		g.setFont(titleFont);
		g.drawString("A R T I F A C T:", 20, 50);

		g.setFont(subTitleFont);
		g.setColor(new Color(255, 255, 162));
		g.drawString("T H E  J O U R N E Y ", 80, 75);
		g.drawString("U N R A V E L E D", 60, 100);

		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Start", 145, 165);
		g.drawString("About Us and Controls", 100, 190);
		g.drawString("Quit", 145, 215);

		// draw floating head
		if (currentChoice == 0)
			g.drawImage(head, 125, 154, null);
		else if (currentChoice == 1)
			g.drawImage(head, 80, 174, null);
		else if (currentChoice == 2)
			g.drawImage(head, 125, 200, null);

		// other
		g.setFont(font2);
		g.drawString("2017 Drew Chase", 10, 232);

	}

	private void select() {
		if (currentChoice == 0) {
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setState(PlayerSave.LoadLevel());
		} else if(currentChoice == 1){
			gsm.setState(GameStateManager.About);
		}else if (currentChoice == 2) {
		
			System.exit(0);
		}
	}

	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER))
			select();
		if (Keys.isPressed(Keys.UP)) {
			if (currentChoice > 0) {
				JukeBox.play("menuoption", 0);
				currentChoice--;
			}
		}
		if (Keys.isPressed(Keys.DOWN)) {
			if (currentChoice < options.length - 1) {
				JukeBox.play("menuoption", 0);
				currentChoice++;
			}
		}
	}

}
