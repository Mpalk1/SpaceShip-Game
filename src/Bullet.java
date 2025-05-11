import javax.swing.*;
import java.awt.*;

public class Bullet {
    ImageIcon icon = new ImageIcon("assets/sprites/player_projectile.png");
    public double pos_x;
    public double pos_y;
    public int speed = 15;
    public int width = 20;
    public int height = 30;
    public double rotation;
    public double center_x;
    public double center_y;
    public Rectangle HitBox;
    public int hits;
//    public int relative_x;
//    public int relative_y;

//    public Thread cooldown;

    public Bullet(int pos_x, int pos_y, double rotation) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.rotation = rotation;
        center_x = pos_x + (double) width /2;
        center_y = pos_y + (double) height /2;
        this.HitBox = new Rectangle((int)this.pos_x, (int)this.pos_y, this.width, this.height);
        this.hits = 0;
//        this.relative_x = pos_x;
//        this.relative_y = pos_y;

    }

    public void updateHitBox(){
        this.HitBox.setLocation((int)this.pos_x, (int)this.pos_y);
    }

    public void updateCenterX(){
        this.center_x = this.pos_x + (double) this.width /2;
    }
    public void updateCenterY(){
        this.center_y = this.pos_y + (double) this.height /2;
    }

    public boolean shouldRemove(){
        return (this.center_y > GamePanel.SCREEN_HEIGHT || this.center_y < 0 || this.center_x < 0 || this.center_x > GamePanel.SCREEN_WIDTH);
    }



}