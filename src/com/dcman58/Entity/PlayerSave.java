package com.dcman58.Entity;

import com.dcman58.GameState.GameStateManager;

public class PlayerSave {
	
	private static int lives = 3;
	private static int health = 5;
	private static long time = 0;
	private static GameStateManager gsm;
	private static int currentState;
	
	public static void init() {
		lives = 3;
		health = 5;
		time = 0;
		currentState = gsm.LEVEL1ASTATE;
	}
	
	public static int getLives() { return lives; }
	public static void setLives(int i) { lives = i; }
	
	public static int getHealth() { return health; }
	public static void setHealth(int i) { health = i; }
	
	public static long getTime() { return time; }
	public static void setTime(long t) { time = t; }
	
	public static int getCurrentState(){
		return currentState;
	}
	public static void setCurrentState(int state){
		currentState = state;
	}
}
