package com.dcman58.Entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.dcman58.GameState.GameStateManager;
import com.dcman58.Handlers.Debug;

@SuppressWarnings("all")
public class PlayerSave {

	private static int lives = 3;
	private static int health = 5;
	private static long time = 0;
	private static int currentState;

	public static boolean hasBottomLeft;
	public static boolean hasBottomRight;
	public static boolean hasTopRight;
	public static boolean hasTopLeft;
	public static HUD hud;

	public static int healthBooster = 0;
	public static int lifeBooster = 0;

	private static String FileName = "game.save", fileContent;
	private static File objFile;
	private static PrintWriter writer;
	private static BufferedWriter bw;
	private static BufferedReader reader;
	private static String folderName = System.getProperty("user.home") + "/ArtifactSaveFiles/";

	public static void init() {
		lives = 3 + getLifeBooster();
		health = 5 + getHealthBooster();
		time = 0;
		// System.out.println("Pieces: " + LoadArtifactHUD());
		currentState = GameStateManager.LEVEL1ASTATE;

	}

	public static void Save(int currentState, int hasPiece) {
		FileWriter fw;
		try {
			fw = new FileWriter(FileName);
			System.out.println("Saved File: level:" + currentState);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("level:" + currentState);
			pw.println("piece:" + hasPiece);
			pw.println("topLeft:" + getHasTopLeft());
			pw.println("bottomLeft:" + getHasBottomLeft());
			pw.println("topRight:" + getHasTopRight());
			pw.println("bottomRight:" + getHasBottomRight());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void Save(int currentState, int hasPiece, boolean hasTopLeft, boolean hasBottomLeft, boolean hasTopRight, boolean hasBottomRight) {
		FileWriter fw;
		try {
			fw = new FileWriter(FileName);
			Debug.Log("Saved File: level:" + currentState);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("level:" + currentState);
			pw.println("piece:" + hasPiece);
			pw.println("topLeft:" + hasTopLeft);
			pw.println("bottomLeft:" + hasBottomLeft);
			pw.println("topRight:" + hasTopRight);
			pw.println("bottomRight:" + hasBottomRight);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void Save(int currentState, int hasPiece, boolean hasTopLeft, boolean hasBottomLeft, boolean hasTopRight, boolean hasBottomRight, int healthBoost, int lifeBoost) {
		FileWriter fw;
		try {
			fw = new FileWriter(FileName);
			Debug.Log("Saved File: level:" + currentState);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("level:" + currentState);
			pw.println("piece:" + hasPiece);
			pw.println("hb:" + healthBoost);
			pw.println("lb:" + lifeBoost);
			pw.println("topLeft:" + hasTopLeft);
			pw.println("bottomLeft:" + hasBottomLeft);
			pw.println("topRight:" + hasTopRight);
			pw.println("bottomRight:" + hasBottomRight);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int LoadArtifactHUD() {
		FileReader fr;
		try {
			fr = new FileReader(FileName);
			BufferedReader br = new BufferedReader(fr);
			String text;
			while ((text = br.readLine()) != null) {
				if (text.equals("topLeft:true")) {
					hasTopLeft = true;
				}
				if (text.equals("topLeft:false")) {
					hasTopLeft = false;
				}
				if (text.equals("bottomLeft:true")) {
					hasBottomLeft = true;
				}
				if (text.equals("bottomLeft:false")) {
					hasBottomLeft = false;
				}
				if (text.equals("topRight:true")) {
					hasTopRight = true;
				}
				if (text.equals("topRight:false")) {
					hasTopRight = false;
				}
				if (text.contains("bottomRight:true")) {
					// System.out.println("Bottom Right=true");
					hasBottomRight = true;
				}
				if (text.contains("bottomRight:false")) {
					// System.out.println("Bottom Right=false");
					hasBottomRight = false;
				}
				// if (text.startsWith("piece:"))
				// return (Integer.parseInt(text.substring(6)));
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public void LoadBoosters() {
		try {
			
			FileReader fr = new FileReader(FileName);
			BufferedReader br = new BufferedReader(fr);
			String text;
			while((text = br.readLine()) != null){
				if(text.contains("hb:")){
					healthBooster = (Integer.parseInt(text.substring(3)));
				}
				if(text.contains("lb:")){
					lifeBooster = (Integer.parseInt(text.substring(3)));
				}
			}

			
		} catch (Exception e) {

		}
	}

	public static int LoadLevel() {
		System.out.println("Loading Save File....\n");
		try {
			FileReader fr = new FileReader(FileName);
			BufferedReader br = new BufferedReader(fr);
			String text;
			while ((text = br.readLine()) != null) {
				System.out.println(text + "\n");
				return (Integer.parseInt(text.substring(6)));
			}

			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return currentState;
	}
	
	
	public static void ResetGame(){
		File fr = new File(FileName);
		if(fr.exists()){
			fr.delete();
		}
	}

	public static int getLives() {
		return lives;
	}

	public static void setLives(int i) {
		lives = i;
	}

	private static int getHealthBooster() {
		return healthBooster;
	}

	private static int getLifeBooster() {
		return lifeBooster;
	}

	public static int getHealth() {
		return health;
	}

	public static void setHealth(int i) {
		health = i;
	}

	public static long getTime() {
		return time;
	}

	public static void setTime(long t) {
		time = t;
	}

	public static int getCurrentState() {
		return currentState;
	}

	public static void setCurrentState(int state) {
		currentState = state;
	}

	public static boolean getHasBottomLeft() {
		// System.out.println("Bottom Left=" + hasBottomLeft);
		return hasBottomLeft;
	}

	public static boolean getHasBottomRight() {
		// System.out.println("Bottom Right=" + hasBottomRight);
		return hasBottomRight;
	}

	public static boolean getHasTopLeft() {
		// System.out.println("Top Left=" + hasTopLeft);
		return hasTopLeft;
	}

	public static boolean getHasTopRight() {
		// System.out.println("Top Right=" + hasTopRight);
		return hasTopRight;
	}
}