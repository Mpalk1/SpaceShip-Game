import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.sound.sampled.*;

public class GamePanel extends JPanel implements Runnable {

    private Thread GameThread;
    public static final int SCREEN_WIDTH = 1600;
    public static final int SCREEN_HEIGHT = 1000;

    public long StartTime = System.currentTimeMillis();
    public int FPS = 60;
    public double angle_temp_x;
    public double angle_temp_y;
    public double angle;
    public double angle_temp;

    EnemyManager EnemyM = new EnemyManager();
    KeyHandler KeyH = new KeyHandler();
    MouseMotionHandler MouseMotionH = new MouseMotionHandler();
    MouseClickHandler MouseClickH = new MouseClickHandler();
    public JLabel debugField1 = new JLabel();
    SoundManager SoundManager = new SoundManager();
    public final List<Bullet> bullets = Collections.synchronizedList(new ArrayList<>());
    JLabel score = new JLabel();
    int score_cnt;

    public GamePanel() {
        super();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.addKeyListener(KeyH);
        this.addMouseMotionListener(MouseMotionH);
        this.addMouseListener(MouseClickH);
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.add(debugField1);
        this.add(score);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        AffineTransform startingTransform = g2D.getTransform();

        // Drawing player
        g2D.rotate(angle, Main.ship.center_x, Main.ship.center_y);
        g2D.drawImage(Main.ship.shipIcon.getImage(), Main.ship.pos_x, Main.ship.pos_y, Main.ship.width, Main.ship.height, null);
        g2D.setTransform(startingTransform);

        // Drawing bullets
        if (!bullets.isEmpty()) {
            for (Bullet bullet : bullets) {
                if (bullet.hits == 0) {
                    g2D.setTransform(startingTransform);
                    g2D.rotate(bullet.rotation, bullet.center_x, bullet.center_y);
                    g2D.drawImage(bullet.icon.getImage(), (int) bullet.pos_x, (int) bullet.pos_y, bullet.width, bullet.height, null);
                    g2D.drawRect(bullet.HitBox.x, bullet.HitBox.y, bullet.HitBox.width, bullet.HitBox.height);
                }
            }
        }
        g2D.setTransform(startingTransform);

        //Drawing enemies
        if (!EnemyM.enemies.isEmpty()) {
            for (Enemy enemy : EnemyM.enemies) {
                g2D.drawImage(enemy.icon.getImage(), enemy.pos_x, enemy.pos_y, null);
                g2D.drawRect(enemy.HurtBox.x, enemy.HurtBox.y, enemy.HurtBox.width, enemy.HurtBox.height);
            }
        }
    }


    public void startGameThread() {
        GameThread = new Thread(this);
        setup();
        GameThread.start(); // starts the run() method
    }

    public void setup() {
        System.out.println("setup started");
        for (int i = 0; i < 5; i++) {
            EnemyM.spawnEnemyRandom(100);
        }
        score_cnt = 0;
        debugField1.setBounds(SCREEN_WIDTH/2-250, 10, 1000, 10);
        score.setFont(new Font("Tahoma", Font.PLAIN, 20));
        score.setBounds(0,0,300,20);
        score.setForeground(Color.red);
    }

    public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Main.ship.center_x = Main.ship.pos_x + (Main.ship.width / 2);
        Main.ship.center_y = Main.ship.pos_y + (Main.ship.height / 2);
        score.setText("SCORE: " + score_cnt);

        //ship angle calculations

        if (MouseMotionH.pos_x > Main.ship.center_x && MouseMotionH.pos_y < Main.ship.center_y) { // 1 cwiartka
            angle_temp_x = MouseMotionH.pos_x - Main.ship.center_x;
            angle_temp_y = Main.ship.center_y - MouseMotionH.pos_y;
            angle_temp = Math.atan2(angle_temp_x, angle_temp_y);
            angle = Math.atan2(angle_temp_x, angle_temp_y);
        }
        if (MouseMotionH.pos_x > Main.ship.center_x && MouseMotionH.pos_y > Main.ship.center_y) { // 2 cwiartka
            angle_temp_x = MouseMotionH.pos_x - Main.ship.center_x;
            angle_temp_y = MouseMotionH.pos_y - Main.ship.center_y;
            angle_temp = Math.atan2(angle_temp_x, angle_temp_y);
            angle = Math.atan2(angle_temp_y, angle_temp_x) + Math.PI / 2;

        }
        if (MouseMotionH.pos_x < Main.ship.center_x && MouseMotionH.pos_y > Main.ship.center_y) { // 3 cwiartka
            angle_temp_x = Main.ship.center_x - MouseMotionH.pos_x;
            angle_temp_y = MouseMotionH.pos_y - Main.ship.center_y;
            angle_temp = Math.atan2(angle_temp_x, angle_temp_y);
            angle = Math.atan2(angle_temp_x, angle_temp_y) + Math.PI;

        }
        if (MouseMotionH.pos_x < Main.ship.center_x && MouseMotionH.pos_y < Main.ship.center_y) { //4 cwiartka
            angle_temp_x = Main.ship.center_x - MouseMotionH.pos_x;
            angle_temp_y = Main.ship.center_y - MouseMotionH.pos_y;
            angle_temp = Math.atan2(angle_temp_x, angle_temp_y);
            angle = Math.atan2(angle_temp_y, angle_temp_x) + 3 * Math.PI / 2;

        }


        debugField1.setText("Mouse_x: " + MouseMotionH.pos_x + ", Mouse_y: " + MouseMotionH.pos_y + ", angle: " + Math.toDegrees(angle) + ", ship position: (" + Main.ship.pos_x + ", "
                + Main.ship.pos_y + "), ship center: (" + Main.ship.center_x + ", " + Main.ship.center_y + ")" + "Mouse clicked: " + MouseClickH.mouseClicked);


        //input handling

        if (KeyH.leftPressed && Main.ship.pos_x > 0) {
            Main.ship.pos_x -= Main.ship.speed;
        }
        if (KeyH.rightPressed && Main.ship.pos_x < SCREEN_WIDTH - Main.ship.width) {
            Main.ship.pos_x += Main.ship.speed;
        }
        if (KeyH.downPressed && Main.ship.pos_y < SCREEN_HEIGHT - Main.ship.height) {
            Main.ship.pos_y += Main.ship.speed;
        }
        if (KeyH.upPressed && Main.ship.pos_y > 0) {
            Main.ship.pos_y -= Main.ship.speed;
        }
        if (MouseClickH.mouseClicked) {
            if (System.currentTimeMillis() - StartTime > 200) {
                StartTime = System.currentTimeMillis();
                bullets.add(new Bullet(Main.ship.center_x - 10, Main.ship.center_y, angle));
                SoundManager.playShootingSound();
            }

        }
        synchronized (bullets) {
            if (!bullets.isEmpty()) {
                Iterator<Bullet> it_bullets = bullets.iterator();
                while (it_bullets.hasNext()) {
                    Bullet bullet = it_bullets.next();
                    bullet.updateCenterX();
                    bullet.updateCenterY();
                    if (bullet.shouldRemove()) {
                        it_bullets.remove();
                        System.out.println("bullet removed");
                    }

                    //Shooting
                    bullet.pos_x += Math.cos(bullet.rotation - Math.PI / 2) * bullet.speed;
                    bullet.pos_y += Math.sin(bullet.rotation - Math.PI / 2) * bullet.speed;
                    bullet.updateHitBox();

                    //Checking for hits
                    synchronized (EnemyM.enemies) {
                        if (bullet.hits == 0) {
                            Iterator<Enemy> it_enemies = EnemyM.enemies.iterator();
                            while (it_enemies.hasNext()) {
                                Enemy enemy = it_enemies.next();
                                if (enemy.HurtBox.intersects(bullet.HitBox)) {
                                    enemy.HP -= 25;
                                    SoundManager.playEnemyHit();
                                    System.out.println("enemy hit");
                                    bullet.hits += 1;
                                }
                                if (enemy.HP <= 0) {
                                    it_enemies.remove();
                                    score_cnt += 25;
                                }
                            }
                        }
                        if (EnemyM.enemies.isEmpty()) {
                            for (int i = 0; i < 5; i++) {
                                EnemyM.spawnEnemyRandom(100);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while (GameThread != null) {
            //1. update() updates the information
            try {
                update();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
            //2. repaint() built in java method to re-invoke paint() method
            repaint();

            try {
                //noinspection BusyWait
                Thread.sleep(1000L / FPS);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
        }
    }

}
