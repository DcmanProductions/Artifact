package com.dcman58.Main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game {

	public static boolean isFullscreen = true;
	public static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public static JFrame window;
	public com.dcman58.Main.Game game;
	static GamePanel gp = new GamePanel();

	public static void main(String[] args) {
		window = new JFrame("Artifact: The Journey Unraveled");
		window.add(new GamePanel());
		window.setUndecorated(true);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

	}

	public void icon() {
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Sprites/Other/Artifact-Icon.png"));
		ImageIcon icon = new ImageIcon(image);
		window.setIconImage(icon.getImage());
	}

}
