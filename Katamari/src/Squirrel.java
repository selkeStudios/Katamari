import java.awt.Image;

public class Squirrel {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image surface;
    private int movex;
    private int movey;
    private int gravity;
    private int bouncerate;
    private int bounceheight;

    public Squirrel(int x, int y, int width, int height, Image surface, int movex, int movey, int gravity, int bouncerate, int bounceheight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.surface = surface;
        this.movex = movex;
        this.movey = movey;
        this.gravity = gravity;
        this.bouncerate = bouncerate;
        this.bounceheight = bounceheight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getSurface() {
        return surface;
    }

    public int getMovex() {
        return movex;
    }

    public int getMovey() {
        return movey;
    }

    public int getGravity() {
        return gravity;
    }

    public int getBouncerate() {
        return bouncerate;
    }

    public int getBounceheight() {
        return bounceheight;
    }

     public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMovex(int movex) {
        this.movex = movex;
    }

    public void setMovey(int movey) {
        this.movey = movey;
    }


    // Add methods for updating squirrel position, handling collisions, etc. as needed.
    // For example:
    public void updatePosition() {
        x += movex;
        y += movey;
        //  Add boundary checks and bouncing logic here.
    }
}

