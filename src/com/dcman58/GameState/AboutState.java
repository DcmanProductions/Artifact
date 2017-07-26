package com.dcman58.GameState;

import java.awt.Font;
import java.awt.Graphics2D;

import com.dcman58.Handlers.Keys;
import com.sun.prism.paint.Color;

import javafx.scene.input.KeyCode;

public class AboutState extends GameState {

	private static Font titleFont = new Font("Arial", Font.BOLD, 25);
	private static Font textFont = new Font("Arial", Font.PLAIN, 8);
	private static Color textColor = Color.WHITE;

//	GameStateManager gsm;
	
	public AboutState(GameStateManager gsm) {
		super(gsm);
//		this.gsm = gsm;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		if(Keys.isPressed(Keys.ESCAPE)||Keys.isPressed(Keys.BUTTON1))
			gsm.setState(0);
	}

	@Override
	public void draw(Graphics2D g) {
		g.clearRect(0, 0, 340, 280);
		g.setFont(titleFont);
		g.drawString("About Us", 10, 30);
		g.setFont(new Font("Arial", Font.PLAIN, 9));
		g.drawString("A Little Game Created by Drew Chase using the Java Programming", 10, 10+60);
		g.drawString("Language.Beta Tested By James Nickerson. Inspired by Mario and Temple", 5,20+60);
		g.drawString("run (kinda). Thanks for Playing my Creation!", 5, 30+60);
		g.drawString("Press [Space] Or [Escape] to Return", 5, 40+60);
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

}
