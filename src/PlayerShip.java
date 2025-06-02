import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class PlayerShip extends JPanel implements Updatable {
    ImageIcon shipIcon = new ImageIcon("assets/sprites/Ship.png");
    int pos_x = 460;
    int pos_y = 600;
    int width = 32;
    int height = 32;
    int speed = 7;
    int center_x;
    int center_y;
    double rotation;
    int HP = 1000000;
    Rectangle HitBox = new Rectangle(pos_x, pos_y, width, height);
    private final GamePanel gp;
    boolean isAlive;
    String selectedGun = "default";
    HashMap<String, Integer> cooldowns = new HashMap<>();

    public PlayerShip(GamePanel Gamepanel) {
        this.gp = Gamepanel;
        isAlive = true;
        cooldowns.put("default", 350);
        cooldowns.put("rifle", 200);
        cooldowns.put("shotgun", 650);
        cooldowns.put("sniper", 1500);
    }

//    @Override
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics2D g2D = (Graphics2D)g;
//
//    }

    @Override
    public void setup() {

    }

    @Override
    public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        updatePosX();
        updatePosY();
        updateCenterX();
        updateCenterY();
        updateRotation();
        updateGun();
        updateHitBox();
        updateShoot();
        updateHit();
        isAlive();
    }

    private void updateHitBox(){
        this.HitBox.setLocation(this.pos_x, this.pos_y);
    }

    private void updatePosX() {
        if (gp.KeyH.leftPressed && this.pos_x > 0) {
            this.pos_x -= this.speed;
        }
        if (gp.KeyH.rightPressed && this.pos_x < GamePanel.SCREEN_WIDTH - this.width) {
            this.pos_x += this.speed;
        }

    }

    private void updatePosY() {
        if (gp.KeyH.downPressed && this.pos_y < GamePanel.SCREEN_HEIGHT - this.height) {
            this.pos_y += this.speed;
        }
        if (gp.KeyH.upPressed && this.pos_y > 0) {
            this.pos_y -= this.speed;
        }
    }

    private void updateCenterX() {
        this.center_x = this.pos_x + (this.width / 2);
    }

    private void updateCenterY() {
        this.center_y = this.pos_y + (this.height / 2);
    }

    private void updateRotation() {
        double angle_temp_x;
        double angle_temp_y;
        if (gp.MouseMotionH.pos_x > this.center_x && gp.MouseMotionH.pos_y < this.center_y) { // 1 cwiartka
            angle_temp_x = gp.MouseMotionH.pos_x - this.center_x;
            angle_temp_y = this.center_y - gp.MouseMotionH.pos_y;
            this.rotation = Math.atan2(angle_temp_x, angle_temp_y);
        }
        if (gp.MouseMotionH.pos_x > this.center_x && gp.MouseMotionH.pos_y > this.center_y) { // 2 cwiartka
            angle_temp_x = gp.MouseMotionH.pos_x - this.center_x;
            angle_temp_y = gp.MouseMotionH.pos_y - this.center_y;
            this.rotation = Math.atan2(angle_temp_y, angle_temp_x) + Math.PI / 2;
        }
        if (gp.MouseMotionH.pos_x < this.center_x && gp.MouseMotionH.pos_y > this.center_y) { // 3 cwiartka
            angle_temp_x = this.center_x - gp.MouseMotionH.pos_x;
            angle_temp_y = gp.MouseMotionH.pos_y - this.center_y;
            this.rotation = Math.atan2(angle_temp_x, angle_temp_y) + Math.PI;
        }
        if (gp.MouseMotionH.pos_x < this.center_x && gp.MouseMotionH.pos_y < this.center_y) { // 4 cwiartka
            angle_temp_x = this.center_x - gp.MouseMotionH.pos_x;
            angle_temp_y = this.center_y - gp.MouseMotionH.pos_y;
            this.rotation = Math.atan2(angle_temp_y, angle_temp_x) + 3 * Math.PI / 2;
        }
    }

    private void updateShoot() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (gp.MouseClickH.mouseClicked) {
            gp.BulletM.spawnPlayerBullet();
        }
    }

    private void updateHit() {
        Iterator<Bullet> it_bullet = gp.BulletM.EnemyBullets.iterator();
        while (it_bullet.hasNext()) {
            Bullet bullet = it_bullet.next();
            if (this.HitBox.intersects(bullet.HitBox)) {
                bullet.updateHits();
                HP -= 25;
            }
        }
        Iterator<Enemy> it_enemy = gp.EnemyM.enemies.iterator();
        while(it_enemy.hasNext()){
            Enemy enemy = it_enemy.next();
            if(this.HitBox.intersects(enemy.HurtBox)){
                this.HP -= 25;
            }
        }

    }

    private void isAlive() {
        if (this.HP <= 0) {
            this.isAlive = false;
        }
    }

    private void updateGun(){
        this.selectedGun = gp.KeyH.selectedGun;
    }
}



