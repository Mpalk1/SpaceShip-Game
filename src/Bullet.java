import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Iterator;

public class Bullet implements Updatable {
    public static File player_bullet = new File("assets/sprites/player_projectile.png");
    public static File enemy_bullet = new File("assets/sprites/enemy_projectile.png");
    ImageIcon icon;
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
    GamePanel gp;
    public int damage;


    public Bullet(int pos_x, int pos_y, double rotation, GamePanel gp, File icon, int dmg, int speed) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.rotation = rotation;
        center_x = pos_x + (double) width / 2;
        center_y = pos_y + (double) height / 2;
        this.HitBox = new Rectangle((int) this.pos_x, (int) this.pos_y, this.width, this.height);
        this.hits = 0;
        this.gp = gp;
        this.icon = new ImageIcon(String.valueOf(icon));
        this.damage = dmg;
        this.speed = speed;
    }

    @Override
    public void setup() {

    }

    @Override
    public void update() {
        updatePosX();
        updatePosY();
        updateCenterX();
        updateCenterY();
        updateHitBox();

    }

    public void updatePosX() {
        this.pos_x += Math.cos(this.rotation - Math.PI / 2) * this.speed;
    }

    public void updatePosY() {
        this.pos_y += Math.sin(this.rotation - Math.PI / 2) * this.speed;
    }

    public void updateHitBox() {
        this.HitBox.setLocation((int) this.pos_x, (int) this.pos_y);
    }

    public void updateCenterX() {
        this.center_x = this.pos_x + (double) this.width / 2;
    }

    public void updateCenterY() {
        this.center_y = this.pos_y + (double) this.height / 2;
    }

    public void updateHits() {
        this.hits++;
    }


    public boolean shouldRemove() {
        return (this.center_y > GamePanel.SCREEN_HEIGHT || this.center_y < 0 || this.center_x < 0 || this.center_x > GamePanel.SCREEN_WIDTH || hits != 0);
    }


}