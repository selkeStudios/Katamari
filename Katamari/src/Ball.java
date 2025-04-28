import java.util.ArrayList;

public class Ball implements Subject
{
	protected int x;
	protected int y;
	protected int scale = 25;
	private boolean hasBeenPickedUpByPlayer;
	
	Player p1;
	Player p2;
	
	Player currentPlayerHolding = null;
	
	protected ArrayList<Observer> observers = new ArrayList<Observer>();
	
	public Ball(int x, int y, Player p1, Player p2)
	{
		this.x = x;
		this.y = y;
		
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getScale()
	{
		return scale;
	}
	
	public boolean getHasBeenPickedUpByPlayer()
	{
		return hasBeenPickedUpByPlayer;
	}
	
	public void playerPickedUpBall(Player p)
	{
		currentPlayerHolding = p;
		setHasBeenPickedUpByPlayer(true);
	}
	
	public void setHasBeenPickedUpByPlayer(boolean value)
	{
		hasBeenPickedUpByPlayer = value;
	}
	
	public void moveWithPlayer()
	{
		this.x = currentPlayerHolding.getX();
		this.y = currentPlayerHolding.getY();
		
		notifyObservers();
	}
	
	public void move(int xDistance, int yDistance)
	{
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
		for (int i = 0; i < observers.size(); ++i)
		{
			observers.get(i).update();
		}
	}
	
	public void checkCollisions(Player p)
	{
		if(hasBeenPickedUpByPlayer)
		{
			return;
		}
		
        if(p.getX() <= x + scale && p.getX() >= x && p.getY() <= y + scale && p.getY() >= y)
        {
        	playerPickedUpBall(p);
        }
	}
	
	public void dropBall()
	{
		hasBeenPickedUpByPlayer = false;
		this.y += 20;
	}
	
	public void updateBall()
	{
        if(hasBeenPickedUpByPlayer)
        {
        	moveWithPlayer();
        }
	}
}
