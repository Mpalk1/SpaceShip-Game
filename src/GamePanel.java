import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.sound.sampled.*;

public class GamePanel extends JPanel implements Runnable {

    // TODO: enemies walking towards player, enemies shooting at player - colliding with enemies, make objects updatable, make classes extend Jpanel - paintComponent() method

    private Thread GameThread;
    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 800;

    public int FPS = 60;
    double StartTimer;
    int score_cnt = 0;
    int time_limit = 60;

    EnemyManager EnemyM = new EnemyManager(this);
    KeyHandler KeyH = new KeyHandler();
    MouseMotionHandler MouseMotionH = new MouseMotionHandler();
    MouseClickHandler MouseClickH = new MouseClickHandler();
    SoundManager SoundManager = new SoundManager();
    BulletManager BulletM = new BulletManager(this);
    JLabel debugField1 = new JLabel();
    JLabel score = new JLabel();
    JLabel timer = new JLabel();
    JLabel HP = new JLabel();

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
        this.add(timer);
        this.add(HP);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        AffineTransform startingTransform = g2D.getTransform();

        // Drawing player
        g2D.rotate(Main.ship.rotation, Main.ship.center_x, Main.ship.center_y);
        g2D.drawImage(Main.ship.shipIcon.getImage(), Main.ship.pos_x, Main.ship.pos_y, Main.ship.width, Main.ship.height, null);
        g2D.drawRect(Main.ship.pos_x, Main.ship.pos_y, Main.ship.width, Main.ship.height);
        g2D.setTransform(startingTransform);

        // Drawing bullets
        if (!BulletM.playerBullets.isEmpty()) {
            for (Bullet bullet : BulletM.playerBullets) {
                g2D.setTransform(startingTransform);
                g2D.rotate(bullet.rotation, bullet.center_x, bullet.center_y);
                g2D.drawImage(bullet.icon.getImage(), (int) bullet.pos_x, (int) bullet.pos_y, bullet.width, bullet.height, null);
                //g2D.drawRect(bullet.HitBox.x, bullet.HitBox.y, bullet.HitBox.width, bullet.HitBox.height);

            }
        }
        g2D.setTransform(startingTransform);

        if(!BulletM.EnemyBullets.isEmpty()){
            for (Bullet bullet : BulletM.EnemyBullets) {
                g2D.setTransform(startingTransform);
                g2D.rotate(bullet.rotation, bullet.center_x, bullet.center_y);
                g2D.drawImage(bullet.icon.getImage(), (int) bullet.pos_x, (int) bullet.pos_y, bullet.width, bullet.height, null);
                //g2D.drawRect(bullet.HitBox.x, bullet.HitBox.y, bullet.HitBox.width, bullet.HitBox.height);
            }
        }
        g2D.setTransform(startingTransform);
        //Drawing enemies
        if (!EnemyM.enemies.isEmpty()) {
            for (Enemy enemy : EnemyM.enemies) {
                g2D.setTransform(startingTransform);
                g2D.rotate(enemy.rotation, enemy.center_x, enemy.center_y);
                g2D.drawImage(enemy.icon.getImage(), enemy.pos_x, enemy.pos_y, null);
                //g2D.drawRect(enemy.HurtBox.x, enemy.HurtBox.y, enemy.HurtBox.width, enemy.HurtBox.height);
            }
        }
        g2D.setTransform(startingTransform);
    }


    public void startGameThread() {
        GameThread = new Thread(this);
        setup();
        GameThread.start(); // starts the run() method
    }

    public void setup() {
        System.out.println("setup started");
        JOptionPane.showMessageDialog(null, "Time limit: " + time_limit + "s.\nTry to get the highest score possible.", "rules", JOptionPane.PLAIN_MESSAGE);
//        for (int i = 0; i < 5; i++) {
//            EnemyM.spawnEnemyRandom(100);
//        }
        EnemyM.spawnEnemy(200,200,100,3,3, Main.ship);
        score_cnt = 0;
        StartTimer = System.currentTimeMillis();
        debugField1.setBounds(SCREEN_WIDTH / 2 - 250, 10, 1000, 10);
        score.setFont(new Font("Thoma", Font.PLAIN, 20));
        score.setBounds(0, 0, 300, 20);
        score.setForeground(Color.WHITE);
        HP.setFont(new Font("Thoma", Font.PLAIN, 20));
        HP.setBounds(0, 60, 300, 20);
        HP.setForeground(Color.RED);
        timer.setFont(new Font("Thoma", Font.PLAIN, 20));
        timer.setBounds(0, 30, 300, 20);
        timer.setForeground(Color.BLUE);
        long t = System.currentTimeMillis();
        System.out.println(t);
        System.out.println((int)t);

    }

    public synchronized void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        score.setText("SCORE: " + score_cnt);
        timer.setText("TIME: " + (int) (System.currentTimeMillis() - StartTimer) / 1000);
        HP.setText("HEALTH: " + Main.ship.HP);
        if ((int) (System.currentTimeMillis() - StartTimer) / 1000 >= time_limit) {
            GameThread = null;
            JOptionPane.showMessageDialog(null, "Time limit exceeded.\nYour score: " + score_cnt, "game over", JOptionPane.PLAIN_MESSAGE);
        }
        if(!Main.ship.isAlive){
            GameThread = null;
            JOptionPane.showMessageDialog(null, "u ded lol", "game over", JOptionPane.PLAIN_MESSAGE);
        }
        debugField1.setText("Mouse_x: " + MouseMotionH.pos_x + ", Mouse_y: " + MouseMotionH.pos_y + ", angle: " + Math.toDegrees(Main.ship.rotation) + ", ship position: (" + Main.ship.pos_x + ", "
                + Main.ship.pos_y + "), ship center: (" + Main.ship.center_x + ", " + Main.ship.center_y + ")" + "Mouse clicked: " + MouseClickH.mouseClicked);

        Main.ship.update();
        BulletM.update();
        EnemyM.update();
    }

    @Override
    public void run() {
        while (GameThread != null) {
            //1. update() updates the information
            try {
                update();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException |
                     ConcurrentModificationException e) {
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
