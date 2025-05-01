import javax.swing.*;

public class Bullet {
    ImageIcon icon = new ImageIcon("player_projectile.png");
    public int pos_x;
    public int pos_y;
    public int speed = 5;
    public int width = 50;
    public int height = 50;
    public double rotation;
    public int center_x;
    public int center_y;

//    public Thread cooldown;

    public Bullet(int pos_x, int pos_y, double rotation) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.rotation = rotation;
        center_x = pos_x/2;
        center_y = pos_y/2;
    }
}