import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

public class Enemy implements Updatable{
    public int pos_x;
    public int pos_y;
    public int center_x;
    public int center_y;
    public final static int width = 32;
    public final static int height = 32;
    public ImageIcon icon;
    public Rectangle HurtBox;
    public int HP;
    public double rotation;
    public int speed;
    PlayerShip player;
    public Random randomizer;
    public double cooldown;
    GamePanel gp;
    double start_time;

    public Enemy(int pos_x, int pos_y, int HP, int speed, PlayerShip player, GamePanel gp){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.icon = new ImageIcon("assets/sprites/enemy.png");
        this.center_x = pos_x + width/2;
        this.center_y = pos_y + height/2;
//        this.HurtBox = new Rectangle(this.center_x-30, this.center_y-30, 20, 20);
        this.HurtBox = new Rectangle(this.pos_x, this.pos_y, Enemy.width, Enemy.height);
        this.HP = HP;
        this.speed = speed;
        this.player = player;
        this.randomizer = new Random();
        this.cooldown = randomizer.nextDouble(0.7, 3.5);
        this.gp = gp;
        this.start_time = System.currentTimeMillis()/1000.0;
    }

    @Override
    public void setup() {

    }

    @Override
    public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        calculateRotation();
        updateCenterX();
        updateCenterY();
        updateCollision();
    }

    public void calculateRotation(){
        double angle_temp_x;
        double angle_temp_y;
        if (center_x < Main.ship.center_x && center_y > Main.ship.center_y) { // 1 cwiartka
            angle_temp_x = Main.ship.center_x - center_x;
            angle_temp_y = center_y - Main.ship.center_y;
            this.rotation = Math.atan2(angle_temp_x, angle_temp_y);
        }
        if (center_x < Main.ship.center_x && center_y < Main.ship.center_y) { // 2 cwiartka
            angle_temp_x = Main.ship.center_x - center_x;
            angle_temp_y = Main.ship.center_y - center_y;
            this.rotation = Math.atan2(angle_temp_y, angle_temp_x) + Math.PI / 2;
        }
        if (center_x > Main.ship.center_x && center_y < Main.ship.center_y) { // 3 cwiartka
            angle_temp_x = center_x - Main.ship.center_x;
            angle_temp_y = Main.ship.center_y - center_y;
            this.rotation = Math.atan2(angle_temp_x, angle_temp_y) + Math.PI;

        }
        if (center_x > Main.ship.center_x && center_y > Main.ship.center_y) { //4 cwiartka
            angle_temp_x = center_x - Main.ship.center_x;
            angle_temp_y = center_y - Main.ship.center_y;
            this.rotation = Math.atan2(angle_temp_y, angle_temp_x) + 3 * Math.PI / 2;
        }
    }

    public void updateCenterX(){
        this.center_x = this.pos_x + width/2;
    }

    public void updateCenterY(){
        this.center_y = this.pos_y + height/2;
    }

    public void updateCollision() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        synchronized (gp.BulletM.playerBullets){
            Iterator<Bullet> it_bullets = gp.BulletM.playerBullets.iterator();
            while(it_bullets.hasNext()){
                Bullet bullet = it_bullets.next();
                if(bullet.hits == 0) {
                    if (this.HurtBox.intersects(bullet.HitBox)) {
                        gp.SoundManager.playEnemyHit();
                        this.HP -= bullet.damage;
                        bullet.updateHits();
                    }
                }
            }
        }
    }
    public boolean shouldRemove(){
        return (this.HP <= 0);
    }

}
