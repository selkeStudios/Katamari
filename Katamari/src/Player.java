import java.util.ArrayList;

public class Player implements Subject
{
	protected int x;
	protected int y;
	protected ArrayList<Observer> observers = new ArrayList<Observer>();
	   
	public Player()
	{
		this.x = 0;
		this.y = 0;
	}

	public Player(int x, int y)
	{
	   this.x = x;
	   this.y = y;
	}

	public void setX(int x)
	{
	   this.x = x;
	}

	public void setY(int y)
	{
	   this.y = y;
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
}
