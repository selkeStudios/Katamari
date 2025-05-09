import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class BackgroundGUI extends JPanel implements ActionListener, Observer, ScoreListener {
	private static final int WINWIDTH = 640;
	private static final int WINHEIGHT = 480;

	private Image leftSquirrelImg;
	private Image rightSquirrelImg;

	Player p1;
	Player p2;

	PlayerController pC1;
	PlayerController pC2;

	Ball ball;

	private ArrayList<Obstacle> obstacles;
	private static final int NUM_OBSTACLES = 30;
	private GameTimer gameTimer;
	private ScoreBoard scoreBoard;

	public BackgroundGUI(Player p1, Player p2, PlayerController pC1, PlayerController pC2, ScoreBoard scoreBoard)
			throws URISyntaxException {
		this.p1 = p1;
		this.p2 = p2;
		this.scoreBoard = scoreBoard;

		this.pC1 = pC1;
		this.pC2 = pC2;

		p1.register(this);
		p2.register(this);
		this.scoreBoard.addScoreListener(this);

		this.ball = new Ball(320, 240, this.p1, this.p2, this.scoreBoard);
		ball.register(this);

		setPreferredSize(new Dimension(WINWIDTH, WINHEIGHT));
		setFocusable(true);
		requestFocusInWindow();

		try {
			leftSquirrelImg = ImageIO.read(new File("images/squirrel.png"));
			rightSquirrelImg = ImageIO.read(new File("images/squirrel.png"));

			

		} catch (IOException e) {
			System.err.println("Error loading images from the web: " + e.getMessage());
		}

		obstacles = new ArrayList<>();
		for (int i = 0; i < NUM_OBSTACLES; i++) {
			obstacles.add(new Obstacle(WINWIDTH, WINHEIGHT));
		}

		Timer timer = new Timer(30, this);
		timer.start();

		// Starting the timer
		this.gameTimer = new GameTimer();
		this.gameTimer.register(this);
		this.gameTimer.start();
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(new Color(52, 99, 49));
		g.fillRect(0, 0, WINWIDTH, WINHEIGHT);

		ball.checkCollisions(p1);
		ball.checkCollisions(p2);

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

		g.setFont(new Font("Monospaced", Font.BOLD, 32));
		g.setColor(Color.BLACK);

		g.drawString(gameTimer.getFormattedTime(), WINWIDTH - 120, 30);
		drawScoreBoard(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pC1.updatePlayer();
		pC2.updatePlayer();
		ball.updateBall();

		for (Obstacle o : obstacles) {
			o.move(WINWIDTH, WINHEIGHT);
		}

		checkObstacleCollisions();

		gameTimer.update();
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
				case GRASS1, GRASS2 -> {}
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
		g.setFont(new Font("Monospaced", Font.BOLD, 20));

		// Player 1 Score (left side)
		g.setColor(Color.RED);
		String p1Text = "Player 1 - " + scoreBoard.getPlayer1Score();
		g.drawString(p1Text, 20, 30);

		// Player 2 Score(right side)
		String p2Text = "Player 2 - " + scoreBoard.getPlayer2Score();
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
}
