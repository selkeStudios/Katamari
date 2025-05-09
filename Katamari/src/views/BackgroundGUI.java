package views;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.PlayerController;
import model.Ball;
import model.GameTimer;
import model.Obstacle;
import model.Player;
import model.ScoreBoard;
import util.Observer;
import util.ScoreListener;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class BackgroundGUI extends JPanel implements ActionListener, Observer, ScoreListener {
	/* ------------------- constants -------------------------- */
	private static final int WINWIDTH = 640;
	private static final int WINHEIGHT = 480;
	private static final int NUM_OBSTACLES = 30;

	/* ------------------- sprites -------------------------- */
	private Image leftSquirrelImg;
	private Image rightSquirrelImg;

	/* ------------------- game objects -------------------------- */
	private final Player p1;
	private final Player p2;
	private final PlayerController pC1;
	private final PlayerController pC2;
	private final Ball ball;
	private ScoreBoard scoreBoard;
	private GameTimer gameTimer;
	private ArrayList<Obstacle> obstacles = new ArrayList<>();

	/* ------------------- scoreboard -------------------------- */
	private final java.util.List<Timer> managedTimers = new ArrayList<>();
	private boolean paused = false;
	private boolean gameOver = false;
	private String winnerMessage = "";

	/* ------- original starting positions for new game ------ */
	private final int p1StartX, p1StartY, p2StartX, p2StartY;
	private final Font bigFont = new Font("SansSerif", Font.BOLD, 48);
	private final Font smallFont = new Font("SansSerif", Font.PLAIN, 20);

	private final Font pauseFont = new Font("SansSerif", Font.BOLD, 48);

	/*------------ custom fonts --------------------- */
	private Font hudFontLarge; // timer
	private Font hudFontMedium; // player scores
	private final Font hintFont = new Font("SansSerif", Font.BOLD, 16);

	public BackgroundGUI(Player p1, Player p2, PlayerController pC1, PlayerController pC2, ScoreBoard scoreBoard)
			throws URISyntaxException {
		this.p1 = p1;
		this.p2 = p2;
		this.pC1 = pC1;
		this.pC2 = pC2;
		this.scoreBoard = scoreBoard;

		p1StartX = p1.getX();
		p1StartY = p1.getY();
		p2StartX = p2.getX();
		p2StartY = p2.getY();

		p1.register(this);
		p2.register(this);
		this.scoreBoard.addScoreListener(this);

		this.ball = new Ball(320, 240, this.p1, this.p2, this.scoreBoard);
		ball.register(this);

		setPreferredSize(new Dimension(WINWIDTH, WINHEIGHT));
		setFocusable(true);

		/* ------------------- key bindings -------------------------- */
		// ESC -> pause / resume
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "togglePause");
		getActionMap().put("togglePause", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				togglePause();
			}
		});
		requestFocusInWindow();

		// ENTER -> if match is over, start a new one
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "restart");
		getActionMap().put("restart", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameOver) {
					startNewGame();
				}
			}
		});

		requestFocusInWindow();

		/* ------------------- graphics assets -------------------------- */
		try {
			leftSquirrelImg = ImageIO.read(new File("images/squirrel.png"));
			rightSquirrelImg = ImageIO.read(new File("images/squirrel.png"));

		} catch (IOException e) {
			System.err.println("Error loading images from the web: " + e.getMessage());
		}

		/* ------------------- obstacles -------------------------- */
		populateObstacles();

		/* ------------------- main animation timer -------------------------- */
		Timer timer = new Timer(30, this);
		managedTimers.add(timer);
		timer.start();

		/* ------------------- countdown timer -------------------------- */
		this.gameTimer = new GameTimer();
		this.gameTimer.register(this);
		this.gameTimer.start();

		/* ------------- Load custom HUD font --------------------- */
		try {
			Font base = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/DS-DIGI.TTF"));
			hudFontLarge = base.deriveFont(Font.BOLD, 36f);
			hudFontMedium = base.deriveFont(Font.PLAIN, 24f);
		} catch (Exception e) {
			System.err.println("Could not load custom font");
			hudFontLarge = new Font("Monospaced", Font.BOLD, 36);
			hudFontMedium = new Font("Monospaced", Font.BOLD, 24);
		}
	}

	public void createGame() {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Katamari");

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.addKeyListener(pC1);
			this.addKeyListener(pC2);

			frame.getContentPane().add(this);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.setResizable(false);
		});
	}

	// Painting
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(new Color(52, 99, 49));
		g.fillRect(0, 0, WINWIDTH, WINHEIGHT);

		ball.checkCollisions(p1);
		ball.checkCollisions(p2);

		if (!paused && !gameOver) {
			ball.checkCollisions(p1);
			ball.checkCollisions(p2);
		}

		// drawing ball, players and obstacles
		g.setColor(Color.RED);
		g.fillOval(this.ball.getX(), this.ball.getY(), this.ball.getScale(), this.ball.getScale());

		for (Obstacle o : obstacles) {
			o.draw(g, this);
		}

		if (leftSquirrelImg != null) {
			g.drawImage(leftSquirrelImg, this.p1.getX(), this.p1.getY(), this);
		}
		if (rightSquirrelImg != null) {
			g.drawImage(rightSquirrelImg, this.p2.getX(), this.p2.getY(), this);
		}

		// HUD - clock & scores
		g.setFont(hudFontLarge);
		g.setColor(Color.BLACK);
		g.drawString(gameTimer.getFormattedTime(), WINWIDTH - 120, 30);
		drawScoreBoard(g);

		g.setFont(hintFont);
		g.setColor(Color.ORANGE);
		String hint = "ESC to pause or resume game";
		int hintX = (WINWIDTH - g.getFontMetrics().stringWidth(hint)) / 2;
		g.drawString(hint, hintX, 22);

		// overlays
		if (paused || gameOver) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 160)); // dark veil
			g2.fillRect(0, 0, WINWIDTH, WINHEIGHT);
			g2.setColor(Color.WHITE);
			g2.setFont(pauseFont);

			if (paused) {
				g2.setFont(bigFont);
				centerText(g2, "PAUSED", WINHEIGHT / 2);
			} else { // gameOver
				g2.setFont(bigFont);
				centerText(g2, winnerMessage, WINHEIGHT / 2 - 20);
				g2.setFont(smallFont);
				centerText(g2, "Press ENTER to play again", WINHEIGHT / 2 + 25);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (paused || gameOver) { // Short-circuit while paused
			repaint();
			return;
		}

		pC1.updatePlayer();
		pC2.updatePlayer();
		ball.updateBall();

		for (Obstacle o : obstacles) {
			o.move(WINWIDTH, WINHEIGHT);
		}

		checkObstacleCollisions();

		gameTimer.update();
		if (gameTimer.getTimeRemainingInSeconds() <= 0) {
			triggerGameOver();
		}

		repaint();
	}

	private void checkObstacleCollisions() {
		boolean ballHeldByPlayer = ball.isHeldBy(p1) || ball.isHeldBy(p2);

		if (!ballHeldByPlayer) {
			return;
		}

		if (!ball.getHasBeenPickedUpByPlayer())
			return;

		ArrayList<Obstacle> collected = new ArrayList<>();

		for (Obstacle o : obstacles) {
			if (!ball.getBounds().intersects(o.getBounds()))
				continue;

			switch (o.getType()) {
				case GRASS1, GRASS2 -> {
				}
				case CARROT -> {
					addPoints(10);
					collected.add(o);
				}
				case CAT -> {
					addPoints(30);
					collected.add(o);
				}
				case BOMB -> {
					JOptionPane.showMessageDialog(this, "💣 Boom! You bumped into a bomb — careful!");
					ball.dropBall();
				}
			}
		}

		obstacles.removeAll(collected);
	}

	@Override
	public void update() {
		this.repaint();
	}

	private void drawScoreBoard(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(hudFontMedium);

		// Player 1 Score (left side)
		g.setColor(Color.RED);
		String p1Text = "P1 - " + scoreBoard.getPlayer1Score();
		g.drawString(p1Text, 20, 30);

		// Player 2 Score(right side)
		String p2Text = "P2 - " + scoreBoard.getPlayer2Score();
		// int p2Width = g2d.getFontMetrics().stringWidth(p2Text);
		g2d.setColor(Color.BLUE);
		g2d.drawString(p2Text, 20, 60);
	}

	@Override
	public void scoreChanged(int p1, int p2) {
		repaint();
	}

	private void addPoints(int points) {
		if (ball.isHeldBy(p1)) {
			scoreBoard.incrementPlayer1Score(points);
		} else if (ball.isHeldBy(p2)) {
			scoreBoard.incrementPlayer2Score(points);
		}
	}

	private void togglePause() {
		if (gameOver)
			return; // Cannot pause if game is over
		paused = !paused;

		if (paused) {
			for (Timer t : managedTimers) {
				t.stop();
			}
			gameTimer.stop();
		} else {
			pC1.resetKeyStates();
			pC2.resetKeyStates();
			for (Timer t : managedTimers) {
				t.start();
			}
			gameTimer.start();
		}
		repaint();
	}

	private void startNewGame() {
		scoreBoard.resetScores();
		populateObstacles();

		// resetting the players
		p1.setX(p1StartX);
		p1.setY(p1StartY);
		p2.setX(p2StartX);
		p2.setY(p2StartY);

		// reset ball
		ball.setHasBeenPickedUpByPlayer(false);
		ball.setX(320);
		ball.setY(240);

		// clear sticky input
		pC1.resetKeyStates();
		pC2.resetKeyStates();

		// reset and start timers
		gameTimer.reset();
		gameTimer.start();
		for (Timer t : managedTimers) {
			t.start();
		}

		paused = false;
		gameOver = false;
		winnerMessage = "";
	}

	private void populateObstacles() {
		obstacles.clear();
		for (int i = 0; i < NUM_OBSTACLES; i++) {
			obstacles.add(new Obstacle(WINWIDTH, WINHEIGHT));
		}
	}

	private void triggerGameOver() {
		gameOver = true;
		paused = false;

		for (Timer t : managedTimers) {
			t.stop();
		}
		gameTimer.stop();

		// deciding the winner
		int p1Score = scoreBoard.getPlayer1Score();
		int p2Score = scoreBoard.getPlayer2Score();

		winnerMessage = (p1Score > p2Score) ? "PLAYER 1 WINS!" : (p2Score > p1Score) ? "PLAYER 2 WINS!" : "IT'S A TIE!";
		repaint();
	}

	private void centerText(Graphics2D g2, String txt, int y) {
		FontMetrics fm = g2.getFontMetrics();
		int x = (WINWIDTH - fm.stringWidth(txt)) / 2;
		g2.drawString(txt, x, y);
	}
}
