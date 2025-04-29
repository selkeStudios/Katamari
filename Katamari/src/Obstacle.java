import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Obstacle {
    private int x, y;
    private int dx, dy;
    private int size;
    private Image image;
    private static final String[] IMAGE_URLS = {
        "https://inventwithpython.com/cat.png",
        "https://inventwithpython.com/grass1.png",
        "https://inventwithpython.com/grass2.png",


    };
    
    public Obstacle(int panelWidth, int panelHeight) {
        Random random = new Random();
        this.size = 30; 
        this.x = random.nextInt(panelWidth - size);
        this.y = random.nextInt(panelHeight - size);

        do {
            dx = random.nextInt(11) - 5; 
            dy = random.nextInt(11) - 5;
        } while (dx == 0 && dy == 0);

        try {
            int index = random.nextInt(IMAGE_URLS.length);
            image = ImageIO.read(new URI(IMAGE_URLS[index]).toURL());
                } catch (IOException | URISyntaxException e) {
            System.err.println("Error loading obstacle image: " + e.getMessage());
        }
    }

    public void move(int panelWidth, int panelHeight) {
        x += dx;
        y += dy;

        if (x <= 0 || x >= panelWidth - size) {
            dx = -dx;
        }
        if (y <= 0 || y >= panelHeight - size) {
            dy = -dy;
        }
    }

    public void draw(Graphics g, JPanel panel) {
        if (image != null) {
            g.drawImage(image, x, y, size, size, panel);
        } else {
            g.setColor(Color.MAGENTA);
            g.fillOval(x, y, size, size);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
