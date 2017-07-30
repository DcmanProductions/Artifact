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

	public static String FileName = "game.save", fileContent;
	public static File objFile;
	public static PrintWriter writer;
	public static BufferedWriter bw;
	public static BufferedReader reader;
	public static String folderName = System.getProperty("user.home") + "/ArtifactSaveFiles/";

	public static void init() {
		lives = 3;
		health = 5;
		time = 0;
		System.out.println("Pieces: " + LoadArtifactHUD());
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
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public static void Save(int currentState, boolean hasBottomLeft, boolean
	// hasBottomRight, boolean hasTopRight,
	// boolean hasTopLeft) {
	// try {
	// FileWriter fw;
	// fw = new FileWriter(FileName);
	//
	// System.out.println("Saved File: level:" + currentState);
	// PrintWriter pw = new PrintWriter(fw);
	// pw.println("level:" + currentState);
	// pw.println("bLeft:" + hasBottomLeft);
	// pw.println("bRight:" + hasBottomRight);
	// pw.println("tRight:" + hasTopRight);
	// pw.println("tLeft:" + hasTopLeft);
	//
	// PlayerSave.hasBottomLeft = hasBottomLeft;
	// PlayerSave.hasBottomRight = hasBottomRight;
	// PlayerSave.hasTopLeft = hasTopLeft;
	// PlayerSave.hasTopRight = hasTopRight;
	// pw.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public static int LoadArtifactHUD() {
		FileReader fr;
		try {
			fr = new FileReader(FileName);
			BufferedReader br = new BufferedReader(fr);
			String text;
			while ((text = br.readLine()) != null) {
				if(text.startsWith("piece:"))
				return (Integer.parseInt(text.substring(6)));
			}

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

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

	public static int getLives() {
		return lives;
	}

	public static void setLives(int i) {
		lives = i;
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
}
