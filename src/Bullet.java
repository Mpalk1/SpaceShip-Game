import javax.swing.*;
import java.awt.*;

public class Bullet {
    ImageIcon icon = new ImageIcon("player_projectile.png");
    public int pos_x;
    public int pos_y;
    public int speed = 15;
    public int width = 20;
    public int height = 30;
    public double rotation;
    public int center_x;
    public int center_y;
    public double vx;
    public double vy;
    public Rectangle HitBox;
    public int hits;

//    public Thread cooldown;

    public Bullet(int pos_x, int pos_y, double rotation) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.rotation = rotation;
        center_x = pos_x + width/2;
        center_y = pos_y + height/2;
        this.vx = Math.cos(rotation) * speed;
        this.vy = Math.sin(rotation) * speed;
        this.HitBox = new Rectangle(this.pos_x, this.pos_y, this.width, this.height);
        this.hits = 0;

    }

    public void updateHitBox(){
        this.HitBox.setLocation(this.pos_x, this.pos_y);
    }

}