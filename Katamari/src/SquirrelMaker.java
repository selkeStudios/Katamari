import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class SquirrelMaker {

    private static Image leftSquirrelImg;
    private static Image rightSquirrelImg;
    private static final Random random = new Random();
    private static final int WINWIDTH = 640; // These constants might be better in a separate config class
    private static final int WINHEIGHT = 480;

    static {
        try {
            leftSquirrelImg = ImageIO.read(SquirrelMaker.class.getResource("squirrel.png"));
            if (leftSquirrelImg != null) {
                rightSquirrelImg = flipImageHorizontally(leftSquirrelImg);
            }
        } catch (IOException e) {
            System.err.println("Error loading squirrel images: " + e.getMessage());
            // Handle image loading error:
            //  - Log the error (as done)
            //  - Set default images to prevent NullPointerExceptions, or
            //  - Throw a RuntimeException to stop the application from starting.
            leftSquirrelImg = null;  // Set to null to indicate loading failure
            rightSquirrelImg = null; // Set to null to indicate loading failure
        }
    }

    private static Image flipImageHorizontally(Image image) {
        if (image == null) {
            return null; // Handle null input
        }
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(image, width, 0, -width, height, null);
        g.dispose();
        return flippedImage;
    }

    private static Image scaleImage(Image image, int width, int height) {
        if (image == null) {
            return null; // Handle null input
        }
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private static double getRandomVelocity() {
        return (random.nextDouble() * 3) + 2; // Random speed between 2 and 5
    }

    public static Squirrel makeNewSquirrel(int cameraX, int cameraY) {
        if (leftSquirrelImg == null || rightSquirrelImg == null) {
            // If the images failed to load, return null or a default Squirrel.  This prevents a crash.
            return null; // Or return a default Squirrel object.
        }

        int size = random.nextInt(20) + 10;
        double speed = getRandomVelocity();
        int movex = (random.nextInt(2) == 0) ? (int) speed : (int) -speed;
        speed = getRandomVelocity();
        int movey = (random.nextInt(2) == 0) ? (int) speed : (int) -speed;
        Image surface = (movex > 0) ? scaleImage(rightSquirrelImg, size, size) : scaleImage(leftSquirrelImg, size, size);
        int bouncerate = random.nextInt(10) + 10;
        int bounceheight = random.nextInt(10) + 10;
        return new Squirrel(random.nextInt(WINWIDTH + 300) - 150 - cameraX,
                random.nextInt(WINHEIGHT + 300) - 150 - cameraY, size, size, surface,
                movex, movey, 0, bouncerate, bounceheight);
    }
}
