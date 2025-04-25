import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Driver
{
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
		
		Player p1 = new Player(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
		Player p2 = new Player(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S);
		
		PlayerController pc1 = new PlayerController(p1);
		PlayerController pc2 = new PlayerController(p2);
		
		GUI gui = new GUI(p1, p2, pc1, pc2);
	}

}