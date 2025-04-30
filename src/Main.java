import javax.swing.*;
import java.awt.*;


public class Main {

    public static PlayerShip ship = new PlayerShip();


    public static void main(String[] args) {
        JFrame window = new JFrame("Space Invaders Clone");
        window.setSize(1024,762);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        //window.setLayout(null);
        GamePanel gamePanel = new GamePanel();


        window.add(gamePanel);
        window.pack();
        gamePanel.startGameThread();
        window.setVisible(true);
    }
}
