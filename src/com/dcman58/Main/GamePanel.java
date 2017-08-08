package com.dcman58.Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.dcman58.GameState.GameStateManager;
import com.dcman58.Handlers.Keys;

@SuppressWarnings("all")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	// dimensions
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int WIDTH = 320;
	public static int HEIGHT = 240;
	// public static int WIDTH =
	// Toolkit.getDefaultToolkit().getScreenSize().width;
	// public static int HEIGHT =
	// Toolkit.getDefaultToolkit().getScreenSize().height;
	// public static int SCALE=4;
	public static int SCALE = (Toolkit.getDefaultToolkit().getScreenSize().getHeight() >= 1080 && Toolkit.getDefaultToolkit().getScreenSize().getWidth() >= 1920) ? 4 : 2;
	// game thread
	private Thread thread;
	public boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	// image
	private BufferedImage image;
	private Graphics2D g;

	// game state manager
	private GameStateManager gsm;

	// other
	private boolean recording = false;
	private int recordingCount = 0;
	private boolean screenshot;

	public GamePanel() {
		super();
		Game game = new Game();
		game.icon();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		// setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	private void init() {

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		// image = new
		// BufferedImage((Toolkit.getDefaultToolkit().getScreenSize().width /
		// SCALE),
		// Toolkit.getDefaultToolkit().getScreenSize().height / SCALE,
		// BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		/*
		 * g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING,
		 * RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		 */

		running = true;
		gsm = new GameStateManager();

	}

	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		// game loop
		while (running) {
			start = System.nanoTime();

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			if (wait < 0)
				wait = 5;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private void update() {
		gsm.update();
		Keys.update();
	}

	private void draw() {
		gsm.draw(g);
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
		if (screenshot) {
			screenshot = false;
			try {
				java.io.File out = new java.io.File(System.getProperty("user.home") + "/Desktop/Artifact ScreenShot-" + System.nanoTime() + ".png");
				javax.imageio.ImageIO.write(image, "png", out);
			} catch (Exception e) {
			}
		}
		if (!recording)
			return;
		/*
		 * try { // java.io.File out = new //
		 * java.io.File(System.getProperty("user.home")+
		 * "/Desktop\\Artifact-Recordings\\frame" // + recordingCount + ".gif");
		 * // javax.imageio.ImageIO.write(image, "gif", out); //
		 * recordingCount++; try { // Temp file for the Recorder Date date = new
		 * Date(); DateFormat dateFormat = new
		 * SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); // FileWriter write = new
		 * FileWriter(new File(System.getProperty("user.home")+
		 * "/Desktop\\Artifact-Recordings\\frame",
		 * "GameCapture"+System.currentTimeMillis()+".mp4")); // File file = new
		 * File(System.getProperty("user.home")+"/Desktop\\", "
		 * GameCapture"+System.currentTimeMillis()+".mp4"); // File file = new
		 * File("GameCapture"+System.currentTimeMillis()+".mp4"); OutputStream
		 * out = new FileOutputStream(file); ScreenRecorder screenRecorder = new
		 * DesktopScreenRecorder(out, new GameCapture());
		 * screenRecorder.startRecording(); try { Thread.sleep(5000);
		 * screenRecorder.stopRecording(); // We reformat the video to .mov file
		 * RecordingConverter.main(new String[] { file.getAbsolutePath() }); }
		 * catch (InterruptedException e) { e.printStackTrace(); } out.close();
		 * } catch (FileNotFoundException e) { e.printStackTrace(); } try {
		 * //Temp file for the Recorder File file = new
		 * File(System.getProperty("user.home")+"/Desktop/GameCapture/",
		 * "GameCapture"+System.currentTimeMillis()+".png"); OutputStream out =
		 * new FileOutputStream(file); ScreenRecorder screenRecorder = new
		 * DesktopScreenRecorder(out,new GameCapture());
		 * screenRecorder.startRecording(); try { Thread.sleep(500);
		 * screenRecorder.stopRecording(); //We reformat the video to .mov file
		 * RecordingConverter.main(new String[]{file.getAbsolutePath()}); }
		 * catch(InterruptedException e) { e.printStackTrace(); } out.close(); }
		 * catch (FileNotFoundException e) { e.printStackTrace(); }
		 * 
		 * } catch (Exception e) {
		 * System.out.println("ERROR IN SCREEN RECORDER!!!   "+e.getMessage());
		 * }
		 */
	}

	public void keyTyped(KeyEvent key) {
	}

	public void keyPressed(KeyEvent key) {
		if (key.isControlDown()) {
			// if (key.getKeyCode() == KeyEvent.VK_R) {
			// recording = !recording;
			// return;
			// }
			if (key.getKeyCode() == KeyEvent.VK_S) {
				screenshot = true;
				return;
			}
		}
		Keys.keySet(key.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}

}