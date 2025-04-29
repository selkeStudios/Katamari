import java.net.URISyntaxException;
import java.awt.event.KeyEvent;

public class Driver
{
	public static void main(String[] args) throws URISyntaxException 
	{		
		Player p1 = new Player(200, 240, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S);
		Player p2 = new Player(440, 240, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
		
		PlayerController pc1 = new PlayerController(p1);
		PlayerController pc2 = new PlayerController(p2);
		
		ScoreBoard scoreboard = new ScoreBoard();
		BackgroundGUI gui = new BackgroundGUI(p1, p2, pc1, pc2, scoreboard);
		gui.createGame();
	}
}