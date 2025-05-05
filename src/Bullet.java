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
    public Rectangle HitBox;
    public int hits;
    public int relative_x;
    public int relative_y;

//    public Thread cooldown;

    public Bullet(int pos_x, int pos_y, double rotation) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.rotation = rotation;
        center_x = pos_x + width/2;
        center_y = pos_y + height/2;
        this.HitBox = new Rectangle(this.relative_x, this.pos_y, this.width, this.height);
        this.hits = 0;
        this.relative_x = pos_x;
        this.relative_y = pos_y;

    }

    public void updateHitBox(){
        this.HitBox.setLocation(this.relative_x, this.pos_y);
    }

}