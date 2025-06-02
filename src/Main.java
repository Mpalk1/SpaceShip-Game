import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


public class Main {

    public static PlayerShip ship;


    public static void main(String[] args) {
        JFrame window = new JFrame("SpaceShip-Game");
        window.setSize(GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        //window.setLayout(null);
        GamePanel gamePanel = new GamePanel();
        ship = new PlayerShip(gamePanel);
        List<Object> gowno = Collections.synchronizedList(new Stack<>());
        window.add(gamePanel);
        window.pack();
        gamePanel.startGameThread();
        window.setVisible(true);
        System.out.println(ship.hashCode());
    }
}
