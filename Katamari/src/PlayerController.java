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
	public void keyPressed(KeyEvent e) {
		player.move(0, 0);
	      if (e.getKeyCode() == KeyEvent.VK_RIGHT)
	      {
	    	  player.move(5, 0);
	      }
	      else if (e.getKeyCode() == KeyEvent.VK_LEFT)
	      {
	    	  player.move(-5, 0);
	      }
	      else if (e.getKeyCode() == KeyEvent.VK_UP)
	      {
	    	  player.move(0, -5);
	      }
	      else if (e.getKeyCode() == KeyEvent.VK_DOWN)
	      {
	    	  player.move(0, 5);
	      }
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
