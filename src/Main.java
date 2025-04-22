import javax.swing.*;


public class Main {

    public static PlayerShip ship = new PlayerShip();


    public static void main(String[] args) {
        JFrame window = new JFrame("Space Invaders Clone");
        window.setSize(1024,762);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);
        gamePanel.startGameThread();
        window.setVisible(true);
    }
}
