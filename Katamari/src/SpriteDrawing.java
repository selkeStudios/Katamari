import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpriteDrawing extends JPanel implements Icon, Observer
{
	   int height;
	   int width;
	   Player player;
	   
	   public SpriteDrawing(int panelWidth, Player player)
	   {
	      this.width = panelWidth;
	      this.height = panelWidth;
	      JLabel imageLabel = new JLabel(this);
	      this.add(imageLabel);
	      this.player = player;
	      player.register(this);
	   }
	
	@Override
	public void update() {
		this.repaint();
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
	      g.setColor(Color.RED);
	      g.fillOval(x + this.player.getX(), y + this.player.getY(), 50, 50);
	}

	@Override
	public int getIconWidth() {
		return this.width;
	}

	@Override
	public int getIconHeight() {
		return this.height;
	}

}
