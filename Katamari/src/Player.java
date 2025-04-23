public class Player {
	protected int x;
	protected int y;
	   
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
	}
}
