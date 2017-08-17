package com.dcman58.Handlers;

import java.awt.event.KeyEvent;

// this class contains a boolean array of current and previous key states
// for the 10 keys that are used for this game.
// a key k is down when keyState[k] is true.

public class Keys {

	public static final int NUM_KEYS = 16;

	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];

	public static int UP = 0;
	public static int LEFT = 1;
	public static int DOWN = 2;
	public static int RIGHT = 3;
	public static int BUTTON1 = 4;
	public static int BUTTON2 = 5;
	public static int BUTTON3 = 6;
	public static int BUTTON4 = 7;
	public static int ENTER = 8;
	public static int ESCAPE = 9;
	public static int F11 = 10;

//	public static Controller controller;O

	public static void keySet(int i, boolean b) {

		// System.setProperty("org.lwjgl.librarypath", new
		// File("natives").getAbsolutePath());
		// try {
		// Controllers.create();
		// } catch (LWJGLException e) {
		// e.printStackTrace();
		// }
		//
		// Controllers.poll();
		// for (int n = 0; n < Controllers.getControllerCount(); n++) {
		// controller = Controllers.getController(n);
		// if (controller.getName().contains("Pad") ||
		// controller.getName().contains("pad") ||
		// controller.getName().contains("Controller") ||
		// controller.getName().contains("controller")) {
		// for (int j = 0; j < controller.getButtonCount(); j++) {
		//
		// for (int l = 0; l < controller.getAxisCount(); l++) {
		// System.out.println("Axis Name:" + controller.getAxisName(l));
		// controller.setDeadZone(l, 0.3f);
		// System.out.println(j + " : " + controller.getButtonName(j));
		// while (true) {
		// controller.poll();
		// keyState[BUTTON1] = controller.isButtonPressed(2);
		// keyState[ENTER] = controller.isButtonPressed(9);
		// keyState[ESCAPE] = controller.isButtonPressed(8);
		//
		// keyState[BUTTON2] = controller.isButtonPressed(7);
		//
		// if (controller.getYAxisValue() > 0) {
		// keyState[DOWN] = b;
		// } else if (controller.getYAxisValue() < 0) {
		// keyState[UP] = b;
		// } else if (controller.getYAxisValue() == 0) {
		// keyState[DOWN] = false;
		// keyState[UP] = false;
		// }
		//
		// if (controller.getXAxisValue() > 0) {
		// keyState[RIGHT] = b;
		// } else if (controller.getXAxisValue() < 0) {
		// keyState[LEFT] = b;
		// } else if (controller.getXAxisValue() == 0) {
		// keyState[RIGHT] = false;
		// keyState[LEFT] = false;
		// }
		//
		// }
		// }
		//
		//
		// }
		// } else {
		
		// Moves Icon in Main Menu Go Up
		if (i == KeyEvent.VK_UP)
			keyState[UP] = b;
		// Moves Player Left
		else if (i == KeyEvent.VK_LEFT)
			keyState[LEFT] = b;
		// Moves Icon in Main Menu Go Down
		else if (i == KeyEvent.VK_DOWN)
			keyState[DOWN] = b;
		// Moves Player Right
		else if (i == KeyEvent.VK_RIGHT)
			keyState[RIGHT] = b;
		// Jump
		else if (i == KeyEvent.VK_SPACE)
			keyState[BUTTON1] = b;
		// Sprint
		else if (i == KeyEvent.VK_SHIFT)
			keyState[BUTTON2] = b;
		//
		else if (i == KeyEvent.VK_E)
			keyState[BUTTON3] = b;
		else if (i == KeyEvent.VK_F)
			keyState[BUTTON4] = b;
		if (i == KeyEvent.VK_ENTER)
			keyState[ENTER] = b;
		else if (i == KeyEvent.VK_ESCAPE)
			keyState[ESCAPE] = b;
		else if (i == KeyEvent.VK_F11)
			keyState[F11] = b;
		// }
		//
		// }
	}

	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}

	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}

	public static boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i])
				return true;
		}
		return false;
	}

}
