package com.dcman58.Main;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.dcman58.Handlers.Keys;

public class Game {

//	public static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public static JFrame window;
public com.dcman58.Main.Game game;
	public static void main(String[] args) {
		window = new JFrame("Artifact: The Journey Unraveled");
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
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

	public void icon() {
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Sprites/Other/Artifact-Icon.png"));
		ImageIcon icon = new ImageIcon(image);
		window.setIconImage(icon.getImage());
	}

}
