import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.ArrayList;


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
    private ArrayList<Obstacle> obstacles;
private static final int NUM_OBSTACLES = 30;

    
    public BackgroundGUI(Player p1, Player p2, PlayerController pC1, PlayerController pC2) throws URISyntaxException 
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

        try {
            leftSquirrelImg = ImageIO.read(new URI("https://inventwithpython.com/squirrel.png").toURL());
            rightSquirrelImg = ImageIO.read(new URI("https://inventwithpython.com/squirrel.png").toURL());
            
        } catch (IOException e) {
            System.err.println("Error loading images from the web: " + e.getMessage());
        }

        random = new Random();
        timer = new Timer(5000 / FPS, this);
        timer.start();

        obstacles = new ArrayList<>();
for (int i = 0; i < NUM_OBSTACLES; i++) {
    obstacles.add(new Obstacle(WINWIDTH, WINHEIGHT));
}

    }

    private void setBackgroundColor() {
		
	}

	public void createGame() {
        SwingUtilities.invokeLater(() -> {   
        	JFrame frame = new JFrame("Katamari");
        	
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
            
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
       
        g.setColor(new Color(52, 99, 49));
        g.fillRect(0, 0, WINWIDTH, WINHEIGHT); 
        
        ball.checkCollisions(p1);
        ball.checkCollisions(p2);
        
    	g.setColor(Color.RED);
	    g.fillOval(this.ball.getX(), this.ball.getY(), this.ball.getScale(), this.ball.getScale());

        for (Obstacle o : obstacles) {
            o.draw(g, this);
        }
        
	    
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
        if (grassImages != null && grassImages.length > 0) { 
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

        for (Obstacle o : obstacles) {
            o.move(WINWIDTH, WINHEIGHT);
        }
        
        checkObstacleCollisions();
        
        repaint(); 
    }

    private void checkObstacleCollisions() {
        boolean ballHeldByPlayer = ball.isHeldBy(p1) || ball.isHeldBy(p2);
    
        if (!ballHeldByPlayer) {
            return; 
        }
    
        ArrayList<Obstacle> collected = new ArrayList<>();
    
        for (Obstacle o : obstacles) {
            if (ball.getBounds().intersects(o.getBounds())) {
                collected.add(o);
            }
        }
    
        obstacles.removeAll(collected);
    }

  
    
    
    

	@Override
	public void update() {
		this.repaint();
	}
}
