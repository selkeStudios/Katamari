package model;

import java.awt.Rectangle;
import java.util.ArrayList;

import util.Observer;
import util.Subject;

public class Ball implements Subject {
	private int x;
	private int y;
	protected int scale = 25;
	private boolean hasBeenPickedUpByPlayer;

	private Player p1;
	private Player p2;
	
	private int maxTopAndLeftBound = 12;
	private int maxRightBound = 612;
	private int maxBottomBound = 450;

	private Player currentPlayerHolding = null;
	private final ScoreBoard scoreBoard;

	protected ArrayList<Observer> observers = new ArrayList<Observer>();

	public Ball(int x, int y, Player p1, Player p2, ScoreBoard scoreBoard) {
		this.x = x;
		this.y = y;
		this.p1 = p1;
		this.p2 = p2;
		this.scoreBoard = scoreBoard;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getScale() {
		return scale;
	}

	public boolean getHasBeenPickedUpByPlayer() {
		return hasBeenPickedUpByPlayer;
	}

	public void playerPickedUpBall(Player p) {
		currentPlayerHolding = p;
		setHasBeenPickedUpByPlayer(true);
	}

	public void setHasBeenPickedUpByPlayer(boolean value) {
		hasBeenPickedUpByPlayer = value;
	}

	public void moveWithPlayer() {
		this.x = currentPlayerHolding.getX();
		this.y = currentPlayerHolding.getY();

		notifyObservers();
	}

	public void move(int xDistance, int yDistance) {
		this.x += xDistance;
		this.y += yDistance;

		notifyObservers();
	}

	@Override
	public void register(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (int i = 0; i < observers.size(); ++i) {
			observers.get(i).update();
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, scale, scale);
	}

	public boolean isHeldBy(Player p) {
		Rectangle playerBounds = new Rectangle(p.getX(), p.getY(), 3, 3);
		return playerBounds.intersects(this.getBounds());
	}

	public void checkCollisions(Player p) {
		if (hasBeenPickedUpByPlayer) {
			return;
		}

		if (p.getX() <= x + scale && p.getX() >= x && p.getY() <= y + scale && p.getY() >= y) {
			playerPickedUpBall(p);
			if (p == p1) {
				scoreBoard.incrementPlayer1Score(10);
				p1.addToScore(10);
			} else {
				scoreBoard.incrementPlayer2Score(10);
				p2.addToScore(10);
			}
		}

		notifyObservers();
	}

	public void dropBall() {
		hasBeenPickedUpByPlayer = false;
		this.y += 60;
		ballPosCheck();
	}

	public void updateBall() {
		if (hasBeenPickedUpByPlayer) {
			moveWithPlayer();
			ballPosCheck();
		}
	}
	
	public void ballPosCheck()
	{
		if (this.x <= maxTopAndLeftBound) {
			this.x = maxTopAndLeftBound;
		}

		if (this.x >= maxRightBound) {
			this.x = maxRightBound;
		}

		if (this.y <= maxTopAndLeftBound) {
			this.y = maxTopAndLeftBound;
		}

		if (this.y >= maxBottomBound) {
			this.y = maxBottomBound;
		}
	}
}
