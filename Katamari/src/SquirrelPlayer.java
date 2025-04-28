import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class SquirrelPlayer {
    public Image leftSquirrelImg;
    public Image rightSquirrelImg;

    public SquirrelPlayer() throws URISyntaxException{
        try {
            // Load images from the web
            leftSquirrelImg = ImageIO.read(new URI("https://inventwithpython.com/squirrel.png").toURL());
            rightSquirrelImg = ImageIO.read(new URI("https://inventwithpython.com/squirrel.png").toURL());
            
        } catch (IOException e) {
            System.err.println("Error loading images from the web: " + e.getMessage());
        }
    }
    
}
