import javax.swing.*;
import java.awt.*;

public class Enemy {
    public int pos_x;
    public int pos_y;
    public int center_x;
    public int center_y;
    public final static int width = 32;
    public final static int height = 32;
    public ImageIcon icon;
    public Rectangle HurtBox;
    public int HP;

    public Enemy(int pos_x, int pos_y, int HP){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.icon = new ImageIcon("assets/sprites/enemy.png");
        this.center_x = pos_x + width/2;
        this.center_y = pos_y + height/2;
//        this.HurtBox = new Rectangle(this.center_x-30, this.center_y-30, 20, 20);
        this.HurtBox = new Rectangle(this.pos_x, this.pos_y, Enemy.width, Enemy.height);
        this.HP = HP;
    }


}
