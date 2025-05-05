import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class PlayerController implements KeyListener
{
	protected Player player;
    int speed = 10;
    
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

	public PlayerController(Player player)
	{
		this.player = player;
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
        if (e.getKeyCode() == player.getPlayerKey(0)) {
            leftPressed = true;
        }
        if (e.getKeyCode() == player.getPlayerKey(1)) {
            rightPressed = true;
        }
        if (e.getKeyCode() == player.getPlayerKey(2)) {
            upPressed = true;
        }
        if (e.getKeyCode() == player.getPlayerKey(3)) {
            downPressed = true;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{		
        if (e.getKeyCode() == player.getPlayerKey(0)) {
            leftPressed = false;
        }
        if (e.getKeyCode() == player.getPlayerKey(1)) {
            rightPressed = false;
        }
        if (e.getKeyCode() == player.getPlayerKey(2)) {
            upPressed = false;
        }
        if (e.getKeyCode() == player.getPlayerKey(3)) {
            downPressed = false;
        }
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public void updatePlayer()
	{   
        if (leftPressed) {
            player.move(-speed, 0);
        }
        if (rightPressed) {
            player.move(speed, 0);
        }
        if (upPressed) {
            player.move(0, -speed);
        }
        if (downPressed) {
            player.move(0, speed);
        }
        
        player.playerPosCheck();
	}
	
}
