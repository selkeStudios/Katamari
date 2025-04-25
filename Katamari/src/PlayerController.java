import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class PlayerController implements KeyListener
{
	protected Player player;

	public PlayerController(Player player)
	{
		this.player = player;
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
	      if (e.getKeyCode() == player.getPlayerKey(0))
	      {
	    	  player.move(-5, 0);
	      }
	      else if (e.getKeyCode() == player.getPlayerKey(1))
	      {
	    	  player.move(5, 0);
	      }
	      else if (e.getKeyCode() == player.getPlayerKey(2))
	      {
	    	  player.move(0, -5);
	      }
	      else if (e.getKeyCode() == player.getPlayerKey(3))
	      {
	    	  player.move(0, 5);
	      }
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {		
	}
	
}
