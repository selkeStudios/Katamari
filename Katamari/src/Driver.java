import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Driver
{
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
		
		Player p1 = new Player();
		Player p2 = new Player();
		
		GUI gui = new GUI(p1, p2);
		
	    while (true)
	    {
	        runGame();
	    }
	}
	
	public static void runGame()
	{
		
	}

}
