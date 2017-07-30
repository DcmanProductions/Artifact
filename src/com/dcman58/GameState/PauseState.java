package com.dcman58.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.dcman58.Handlers.Keys;
import com.dcman58.Main.GamePanel;

public class PauseState extends GameState {

	private Font font;

	public PauseState(GameStateManager gsm) {

		super(gsm);

		// fonts
		font = new Font("Century Gothic", Font.PLAIN, 24);

	}

	public void init() {
	}

	public void update() {
		handleInput();
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused", 90, 90);
		g.setFont((new Font("Arial", Font.PLAIN, 10)));
		g.setColor(Color.RED);
		g.drawString("Press Space For Main Menu", 50, 150);
		g.setColor(Color.GREEN);
		g.drawString("Press Escape to Resume Playing", 50, 120);

	}

	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(false);
		if (Keys.isPressed(Keys.BUTTON1)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

}
