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

        GamePanel gamePanel = new GamePanel();
        TextField tutorial = new TextField("wasd - move, enter - shoot");
        gamePanel.add(tutorial);


        window.add(gamePanel);
        window.pack();
        gamePanel.startGameThread();
        window.setVisible(true);
    }
}
