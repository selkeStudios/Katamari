import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Obstacle {

	public enum ObstacleType {
		GRASS1, GRASS2, CARROT, CAT, BOMB
	}

	private final ObstacleType type;
	private final Image sprite;
	private static final int SIZE = 30;
	private int x, y;
	private int dx, dy;

	public Obstacle(int panelWidth, int panelHeight) {
		Random random = new Random();
		this.x = random.nextInt(panelWidth - SIZE);
		this.y = random.nextInt(panelHeight - SIZE);
		type = ObstacleType.values()[random.nextInt(ObstacleType.values().length)];
		sprite = loadSprite(switch (type) {
			case GRASS1 -> "images/grass1.png";
			case GRASS2 -> "images/grass2.png";
			case CARROT -> "images/carrot.png";
			case CAT -> "images/cat.png";
			case BOMB -> "images/bomb.png";
		});

		do {
			dx = random.nextInt(11) - 5;
			dy = random.nextInt(11) - 5;
		} while (dx == 0 && dy == 0);

		// try {
		// int index = random.nextInt(ObstacleType.values().length);
		// image = ImageIO.read(new File(IMAGE_PATHS[index]));
		// } catch (IOException e) {
		// System.err.println("Error loading obstacle image: " + e.getMessage());
		// }
	}

	public void move(int panelWidth, int panelHeight) {

		x += dx;
		y += dy;

		if (x <= 0 || x >= panelWidth - SIZE) {
			dx = -dx;
		}
		if (y <= 0 || y >= panelHeight - SIZE) {
			dy = -dy;
		}
	}

	public void draw(Graphics g, JPanel panel) {
		// if (image != null) {
		// g.drawImage(image, x, y, SIZE, SIZE, panel);
		// } else {
		// g.setColor(Color.MAGENTA);
		// g.fillOval(x, y, SIZE, SIZE);
		// }
		g.drawImage(sprite, x, y, SIZE, SIZE, panel);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, SIZE, SIZE);
	}

	public ObstacleType getType() {
		return type;
	}

	private static BufferedImage loadSprite(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println("Could not load sprite " + path + " - using magenta placholder");
			BufferedImage placeholder = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = placeholder.createGraphics();
			g.setColor(Color.MAGENTA);
			g.fillRect(0, 0, 32, 32);
			g.dispose();
			return placeholder;
		}
	}
}