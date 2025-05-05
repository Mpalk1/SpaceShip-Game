import javax.swing.*;
import java.awt.*;

public class Enemy {
    public int pos_x;
    public int pos_y;
    public int center_x;
    public int center_y;
    public int width;
    public int height;
    public ImageIcon icon;
    public Rectangle HurtBox;
    public int HP;

    public Enemy(int pos_x, int pos_y, int width, int height, int HP){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.icon = new ImageIcon("enemy.png");
        this.width = width;
        this.height = height;
        this.center_x = pos_x + width/2;
        this.center_y = pos_y + height/2;
        this.HurtBox = new Rectangle(this.pos_x, this.pos_y, this.width, this.height);
        this.HP = HP;
    }

}
