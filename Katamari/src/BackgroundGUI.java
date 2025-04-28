import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class BackgroundGUI extends JPanel implements ActionListener, Observer
{
    private static final int WINWIDTH = 640;
    private static final int WINHEIGHT = 480;
    private static final int FPS = 30;

    private Image leftSquirrelImg;
    private Image rightSquirrelImg;
    private Image[] grassImages; 
    private Timer timer;
    private Random random;
    
	Player p1;
	Player p2;
	
	PlayerController pC1;
	PlayerController pC2;
	
	Ball ball;
	
    public BackgroundGUI(Player p1, Player p2, PlayerController pC1, PlayerController pC2) 
    {	
		this.p1 = p1;
		this.p2 = p2;
		
		this.pC1 = pC1;
		this.pC2 = pC2;
		
		p1.register(this);
		p2.register(this);
		
		this.ball = new Ball(320, 240, this.p1, this.p2);
		
		ball.register(this);
    	
        setPreferredSize(new Dimension(WINWIDTH, WINHEIGHT));
        setBackgroundColor();
        setFocusable(true);
        requestFocusInWindow();

        grassImages = new Image[0]; // Initialize with an empty array in case of failure

        try {
            // Load images from the web
            leftSquirrelImg = ImageIO.read(new URL("https://inventwithpython.com/squirrel.png"));
            rightSquirrelImg = ImageIO.read(new URL("https://inventwithpython.com/squirrel.png"));
            grassImages = new Image[2];
            grassImages[0] = ImageIO.read(new URL("https://inventwithpython.com/grass1.png"));
            grassImages[1] = ImageIO.read(new URL("https://inventwithpython.com/grass2.png"));
        } catch (IOException e) {
            System.err.println("Error loading images from the web: " + e.getMessage());
            // grassImages is already an empty array, so no further action needed here
        }

        random = new Random();
        timer = new Timer(5000 / FPS, this);
        timer.start();
    }

    private void setBackgroundColor() {
		
	}

	public void createGame() {
        SwingUtilities.invokeLater(() -> {   
        	JFrame frame = new JFrame("Katamari");
        	
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                frame.setIconImage(ImageIO.read(new URL("https://inventwithpython.com/gameicon.png")));
            } catch (IOException e) {
                System.err.println("Error loading game icon from the web: " + e.getMessage());
                // Handle icon loading error
            }
            
            this.addKeyListener(pC1);
            this.addKeyListener(pC2);
            
            frame.getContentPane().add(this);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrass(g);
        
        ball.checkCollisions(p1);
        ball.checkCollisions(p2);
        
    	g.setColor(Color.RED);
	    g.fillOval(this.ball.getX(), this.ball.getY(), this.ball.getScale(), this.ball.getScale());
	    
        // Draw squirrels and other game elements here
        if (leftSquirrelImg != null) {
            g.drawImage(leftSquirrelImg, this.p1.getX(), this.p1.getY(), this);
        }
        if (rightSquirrelImg != null) {
            g.drawImage(rightSquirrelImg, this.p2.getX(), this.p2.getY(), this);
        }

        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.setColor(Color.BLACK);
    }

    private void drawGrass(Graphics g) {
        // Example: Draw random grass patches
        if (grassImages != null && grassImages.length > 0) { // Check if grassImages has elements
            for (int i = 0; i < 10; i++) {
                int x = random.nextInt(WINWIDTH);
                int y = random.nextInt(WINHEIGHT);
                int grassIndex = random.nextInt(grassImages.length);
                if (grassIndex < grassImages.length && grassImages[grassIndex] != null) {
                    g.drawImage(grassImages[grassIndex], x, y, this);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pC1.updatePlayer();
        pC2.updatePlayer();
        ball.updateBall();
        
        repaint(); 
    }

	@Override
	public void update() {
		this.repaint();
	}
}
