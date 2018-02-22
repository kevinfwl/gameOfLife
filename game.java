 
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import com.david.entity.Player;
import com.david.main.input.KeyboardInput;

public class Game extends Canvas implements Runnable {
	
	/**
	 * The handler of this Game.
	 */
	public static GameHandler gameHandler;
	
	/**
	 * The height of this Game's window.
	 */
	public static final int HEIGHT = 768;
	
	/**
	 * The title of this Game
	 */
	public static final String TITLE = "Super Mario";
	
	/**
	 * The width of this Game's window.
	 */
	public static final int WIDTH = 1366;
	
	// The thread in which this Game will run in.
	private Thread thread;
	
	// The state of the thread.
	private boolean running = false;
	
	/**
	 * Initializes the thread in which this Game will be run in.
	 */
	public synchronized void start() {
		if (running) return;
		running = true;
		thread = new Thread(this, "Game Thread");
		thread.start();
	}
	
	/**
	 * Stops the thread in which this Game is being run in.
	 */
	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
	}

	/**
	 * Code that will be run when this Game is initialized.
	 */
	public void run() {
		init();
		requestFocus();
		//System's current time in nanoseconds.
		long lastTime = System.nanoTime();
		
		// System's current time in milliseconds.
		long timer = System.currentTimeMillis();
		
		// Change in value.
		double delta = 0;
		
		// Nanosecond value.
		double ns = 1000000000.0 / 60.0;
		
		// Frames per second of this Game.
		int frames = 0;
		
		// Updates per second of this Game.
		int ticks = 0;

		while (running) {
			long now = System.nanoTime();
			
			// The time it took for java to initialize lastTime and reach this line of code.
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				updateDisplay();
				ticks++;
				delta--;
			}
			renderGraphics();
			frames++;
			
			/*
			 * Runs this code if it took java more than 1 second to reach this line of code
			 * after it initializes timer.
			 */
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(frames + "Frames Per Second " + ticks + " Updates Per Second.");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}

	/**
	 * Renders the graphics of this Game.
	 */
	public void renderGraphics() {
		BufferStrategy bufferStrategy = getBufferStrategy();
		if (bufferStrategy == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics graphics = bufferStrategy.getDrawGraphics();
		
		// Displays the background.
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		// Displays the Player.
		gameHandler.renderGraphics(graphics);
		graphics.dispose();
		bufferStrategy.show();
	}
	
	/**
	 * Updates the display of this Game.
	 */
	public void updateDisplay() {
		gameHandler.updateDisplay();
	}
	
	/*
	 * constructors
	 */
	
	/**
	 * Creates a Game object with fixed dimensions.
	 */
	public Game() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
	}
	
	// Initializes the handler of this Game.
	private void init() {
		gameHandler = new GameHandler();
		
		addKeyListener(new KeyboardInput());
		
		// Creates a Player object.
		gameHandler.addEntity(new Player(200, 400, 64, 64, true, Type.player, gameHandler));
	}
	
	/**
	 * Creates the window of this Game.
	 * 
	 * @param arguments not used
	 */
	public static void main(String[] arguments) {
		Game game = new Game();
		JFrame mainFrame = new JFrame(TITLE);
		mainFrame.add(game);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		game.start();
	}
}
