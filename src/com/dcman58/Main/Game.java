package com.dcman58.Main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import com.dcman58.Handlers.Keys;

public class Game {

	public static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public static void main(String[] args) {
		JFrame window = new JFrame("Alien Hunter");
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		if (!isFullscreen()) {
			window.pack();
		}
		if (isFullscreen()) {
			window.setUndecorated(true);
			window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public static boolean isFullscreen() {
		if (Keys.isPressed(Keys.F11) && !isFullscreen()) {
			return true;
		}
		if (Keys.isPressed(Keys.F11) && isFullscreen()) {
			return false;
		}
		return false;
	}

}
