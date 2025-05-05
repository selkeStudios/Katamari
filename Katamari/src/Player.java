import java.util.ArrayList;
// import java.awt.event.KeyEvent;

public class Player implements Subject
{
	protected int x;
	protected int y;
	protected ArrayList<Observer> observers = new ArrayList<Observer>();
	private int[] keys = new int[4];
	private int score;
	
	private int maxTopAndLeftBound = 12;
	private int maxRightBound = 612;
	private int maxBottomBound = 450;
	   
	public Player(int left, int right, int up, int down)
	{
		this.x = 0;
		this.y = 0;
		
		this.keys[0] = left;
	    this.keys[1] = right;
	    this.keys[2] = up;
	    this.keys[3] = down;
	}

	public Player(int x, int y, int left, int right, int up, int down)
	{
	   this.x = x;
	   this.y = y;
	   
	   this.keys[0] = left;
	   this.keys[1] = right;
	   this.keys[2] = up;
	   this.keys[3] = down;
	}

	public int getX()
	{
	   return x;
	}

	public int getY()
	{
	   return y;
	}

	public void move(int xDistance, int yDistance)
	{
	   this.x += xDistance;
	   this.y += yDistance;
	   
	   notifyObservers();
	}
	
	public int getPlayerKey(int index)
	{
		return keys[index];
	}

	public void addToScore(int points) {
		this.score += points;
		notifyObservers();
	}

	public int getScore() {
		return score;
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
		for (int i = 0; i < observers.size(); ++i)
		{
			observers.get(i).update();
		}
	}
	
	public void playerPosCheck()
	{
		if (this.x <= maxTopAndLeftBound)
		{
			this.x = maxTopAndLeftBound;
		}

		if (this.x >= maxRightBound)
		{
			this.x = maxRightBound;
		}
		
		if (this.y <= maxTopAndLeftBound)
		{
			this.y = maxTopAndLeftBound;
		}

		if (this.y >= maxBottomBound)
		{
			this.y = maxBottomBound;
		}
	}
}
