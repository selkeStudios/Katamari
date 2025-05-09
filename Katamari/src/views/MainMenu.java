package views;

import controller.PlayerController;
import model.Player;
import model.ScoreBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URISyntaxException;

public class MainMenu {

    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            JFrame menuFrame = new JFrame("Katamari - Main Menu");
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuFrame.setSize(640, 480);
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setResizable(false);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(new Color(185, 209, 169));
                    g.fillRect(0, 0, getWidth(), getHeight());

                    g.setFont(new Font("Arial", Font.BOLD, 52));
                    g.setColor(new Color(50, 50, 150));
                    String title = "Katamari";
                    int titleWidth = g.getFontMetrics().stringWidth(title);
                    g.drawString(title, (getWidth() - titleWidth) / 2, 120);
                }
            };

            panel.setLayout(null); 

            JButton startButton = new JButton("Start Game");
            startButton.setFont(new Font("Arial", Font.BOLD, 24));
            startButton.setBounds(220, 200, 200, 50);
            panel.add(startButton);

            startButton.addActionListener((ActionEvent e) -> {
                try {
                    Player p1 = new Player(200, 240, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S);
                    Player p2 = new Player(440, 240, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
                    PlayerController pc1 = new PlayerController(p1);
                    PlayerController pc2 = new PlayerController(p2);
                    ScoreBoard scoreboard = new ScoreBoard();

                    BackgroundGUI gui = new BackgroundGUI(p1, p2, pc1, pc2, scoreboard);
                    gui.createGame();

                    menuFrame.dispose(); 
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
            });

            menuFrame.getContentPane().add(panel);
            menuFrame.setVisible(true);
        });
    }
}
