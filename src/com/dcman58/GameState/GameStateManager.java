package com.dcman58.GameState;

import com.dcman58.Audio.JukeBox;
import com.dcman58.Main.GamePanel;

@SuppressWarnings("all")
public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;

	private PauseState pauseState;
	private boolean paused;

	public static final int NUMGAMESTATES = 16;
	public static final int MENUSTATE = 0;
	public static final int About = 1;
	public static final int LEVEL1ASTATE = 2;
	public static final int LEVEL1BSTATE = 3;
	public static final int LEVEL1CSTATE = 4;

	public static final int LEVEL2ASTATE = 5;
	public static final int LEVEL2BSTATE = 6;
	public static final int LEVEL2CSTATE = 7;
//	public static final int LEVEL2DSTATE = 8;

	public static final int ACIDSTATE = 15;

	public GameStateManager() {

		JukeBox.init();

		gameStates = new GameState[NUMGAMESTATES];

		pauseState = new PauseState(this);
		paused = false;

		currentState = /*LEVEL2ASTATE;*/MENUSTATE;
		loadState(currentState);

	}

	private void loadState(int state) {
		if (state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if (state == About)
			gameStates[state] = new AboutState(this);
		else if (state == LEVEL1ASTATE)
			gameStates[state] = new Level1AState(this);
		else if (state == LEVEL1BSTATE)
			gameStates[state] = new Level1BState(this);
		else if (state == LEVEL1CSTATE)
			gameStates[state] = new Level1CState(this);
		else if (state == LEVEL2ASTATE)
			gameStates[state] = new Level2AState(this);
		else if (state == LEVEL2BSTATE)
			gameStates[state] = new Level2BState(this);
		else if (state == LEVEL2CSTATE)
			gameStates[state] = new Level2CState(this);
		else if (state == ACIDSTATE)
			gameStates[state] = new AcidState(this);
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public void setPaused(boolean b) {
		paused = b;
	}

	public void update() {
		if (paused) {
			pauseState.update();
			return;
		}
		if (gameStates[currentState] != null)
			gameStates[currentState].update();
	}

	public void draw(java.awt.Graphics2D g) {
		if (paused) {
			pauseState.draw(g);
			return;
		}
		if (gameStates[currentState] != null)
			gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
//			g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}

}