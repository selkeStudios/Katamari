import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Background extends JPanel implements ActionListener, Observer
{
    private JFrame mainFrame;
    private JPanel mainPanel;

    private Player p1;
    private Player p2;
   
    private PlayerController pC1;
    private PlayerController pC2;
    private Ball ball;
   
    public Background(Player p1, Player p2, PlayerController pC1, PlayerController pC2)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.pC1 = pC1;
        this.pC2 = pC2;
         
        p1.register(this);
        p2.register(this);
         
        this.ball = new Ball(320, 240, this.p1, this.p2);
        ball.register(this);

        this.mainFrame = new JFrame("Katamari");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.mainPanel = new JPanel();
        this.mainPanel.setBackground(new Color(227, 206, 245));
        this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.mainFrame.setSize(1000, 1000); 
        
        this.mainFrame.add(mainPanel);

     
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);

 
        this.setBackground(new Color(154, 232, 149));
             
        this.addKeyListener(pC1);
        this.addKeyListener(pC2);
        
         
        mainFrame.add(this);
        mainFrame.pack();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  

        ball.checkCollisions(p1);
        ball.checkCollisions(p2);

        g.setColor(new Color(154, 232, 149));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.RED); 
        g.fillOval(ball.getX(), ball.getY(), ball.getScale(), ball.getScale());
         try {
            SquirrelPlayer sqPlayer=new SquirrelPlayer();
            if(sqPlayer.leftSquirrelImg!=null){
               g.drawImage(sqPlayer.leftSquirrelImg, this.p1.getX(), this.p1.getY(), this);

            }
            if (sqPlayer.rightSquirrelImg != null) {
               g.drawImage(sqPlayer.rightSquirrelImg, this.p2.getX(), this.p2.getY(), this);
           }
         } catch (URISyntaxException e) {
            e.printStackTrace();
         }
         
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.setColor(Color.BLACK);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        pC1.updatePlayer();  
        pC2.updatePlayer();  
        ball.updateBall();   
        repaint();
    }

    @Override
    public void update()
    {
        this.repaint();  
    }
}
