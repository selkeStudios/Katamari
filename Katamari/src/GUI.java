import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI 
{
	JFrame mainFrame;
	JPanel mainPanel;
	
	Player p1;
	Player p2;
	
	SpriteDrawing playerDrawing1;
	SpriteDrawing playerDrawing2;

	public GUI(Player p1, Player p2)
	{
		this.p1 = p1;
		this.p2 = p2;
	   mainFrame = new JFrame();
	   mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	   mainPanel = new JPanel();
	   mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	   
	   playerDrawing1 = new SpriteDrawing(400, p1);
	   playerDrawing2 = new SpriteDrawing(400, p2);
	   
	      // attach key listener to the frame: when the frame is in focus and
	      // any key is pressed, key listener will be called
	   //mainFrame.addKeyListener(keyListener);
	   
	   mainPanel.add(playerDrawing1);
	   mainPanel.add(playerDrawing2);

	   mainFrame.add(mainPanel);
	   mainFrame.pack();
	   mainFrame.setVisible(true);
	}

}
