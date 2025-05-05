import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    // TODO dodać dzwięki
    private final int SCREEN_WIDTH = 1024;
    private final int SCREEN_HEIGHT = 762;

    KeyHandler KeyH = new KeyHandler();
    MouseMotionHandler MouseMotionH = new MouseMotionHandler();
    MouseClickHandler MouseClickH = new MouseClickHandler();
    public int FPS = 60;
    public ArrayList<Bullet> bullets = new ArrayList<>(); // TODO change this to linked list
    public long StartTime = System.currentTimeMillis();
    public double angle_temp_x;
    public double angle_temp_y;
    public double angle;
    public TextField debugField1 = new TextField();
    Enemy Enemy1 = new Enemy(50, 50, 64, 45, 100);

    public GamePanel(){
        super();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.addKeyListener(KeyH);
        this.addMouseMotionListener(MouseMotionH);
        this.addMouseListener(MouseClickH);
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.add(debugField1);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        AffineTransform startingTransform = g2D.getTransform();

        // Drawing player
        g2D.rotate(angle, Main.ship.center_x, Main.ship.center_y);
        g2D.drawImage(Main.ship.shipIcon.getImage(), Main.ship.pos_x, Main.ship.pos_y,Main.ship.width,Main.ship.height, null);
        g2D.setTransform(startingTransform);

        // Drawing bullets
        if(!bullets.isEmpty()){
            for(Bullet bullet: bullets) {
                if (bullet.hits == 0) {
                    g2D.setTransform(startingTransform);
                    g2D.rotate(bullet.rotation, bullet.center_x, bullet.center_y);
                    g2D.drawImage(bullet.icon.getImage(), bullet.pos_x, bullet.pos_y, bullet.width, bullet.height, null);
                    g2D.setPaint(Color.blue);
                }
            }
        }
        g2D.setTransform(startingTransform);

        //Drawing enemies
        if(Enemy1.HP > 0) {
            g2D.drawImage(Enemy1.icon.getImage(), Enemy1.pos_x, Enemy1.pos_y, Enemy1.width, Enemy1.height, null);
        }
    }



    Thread GameThread;

    public void startGameThread(){
        GameThread = new Thread(this);
        GameThread.start(); // starts the run() method
    }

    public void update(){
        Main.ship.center_x = Main.ship.pos_x + (Main.ship.width/2);
        Main.ship.center_y = Main.ship.pos_y + (Main.ship.height/2);

        //ship angle calculations

        if(MouseMotionH.pos_x > Main.ship.center_x && MouseMotionH.pos_y < Main.ship.center_y){ // 1 cwiartka
            angle_temp_x = MouseMotionH.pos_x - Main.ship.center_x;
            angle_temp_y = Main.ship.center_y - MouseMotionH.pos_y;
            angle = Math.atan2(angle_temp_x, angle_temp_y);
        }
        if(MouseMotionH.pos_x > Main.ship.center_x && MouseMotionH.pos_y > Main.ship.center_y){ // 2 cwiartka
            angle_temp_x = MouseMotionH.pos_x - Main.ship.center_x;
            angle_temp_y = MouseMotionH.pos_y - Main.ship.center_y;
            angle = Math.atan2(angle_temp_y, angle_temp_x) + Math.PI/2;

        }
        if(MouseMotionH.pos_x < Main.ship.center_x && MouseMotionH.pos_y > Main.ship.center_y){ // 3 cwiartka
            angle_temp_x = Main.ship.center_x - MouseMotionH.pos_x;
            angle_temp_y = MouseMotionH.pos_y - Main.ship.center_y;
            angle = Math.atan2(angle_temp_x, angle_temp_y) + Math.PI;

        }
        if(MouseMotionH.pos_x < Main.ship.center_x && MouseMotionH.pos_y < Main.ship.center_y){ //4 cwiartka
            angle_temp_x = Main.ship.center_x - MouseMotionH.pos_x;
            angle_temp_y = Main.ship.center_y - MouseMotionH.pos_y;
            angle = Math.atan2(angle_temp_y, angle_temp_x) + 3*Math.PI/2;

        }


        debugField1.setText("Mouse_x: " + MouseMotionH.pos_x + ", Mouse_y: " + MouseMotionH.pos_y + ", angle: " + Math.toDegrees(angle) + ", ship position: (" + Main.ship.pos_x + ", "
                + Main.ship.pos_y +  "), ship center: (" + Main.ship.center_x + ", " + Main.ship.center_y + ")" + "Mouse clicked: " + MouseClickH.mouseClicked);


        //input handling

        if(KeyH.leftPressed && Main.ship.pos_x > -10){
            Main.ship.pos_x -= Main.ship.speed;
        }
        if(KeyH.rightPressed && Main.ship.pos_x < 1024-63){
            Main.ship.pos_x += Main.ship.speed;
        }
        if(KeyH.downPressed && Main.ship.pos_y < 762-70){
            Main.ship.pos_y += Main.ship.speed;
        }
        if(KeyH.upPressed && Main.ship.pos_y > 0) {
            Main.ship.pos_y -= Main.ship.speed;
        }
        if(MouseClickH.mouseClicked){
            if(System.currentTimeMillis() - StartTime > 200){
                StartTime = System.currentTimeMillis();
                bullets.add(new Bullet(Main.ship.center_x-10, Main.ship.center_y, angle));
            }

        }

        if(!bullets.isEmpty()) {
            for (Bullet bullet : bullets) {
                if (bullet.pos_y > 762){
                    //bullets.remove(bullet);
                    System.out.println("bullet removed");
                }

                //Shooting
                bullet.pos_y -= bullet.speed;
                bullet.updateHitBox();
                System.out.println(bullet.HitBox);

                //Checking for hits
                if(bullet.hits == 0) {
                    if (bullet.HitBox.intersects(Enemy1.HurtBox)) {
                        Enemy1.HP -= 25;
                        System.out.println("enemy hit");
                        bullet.hits += 1;
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while(GameThread != null){
            //1. update() updates the information
            update();
            //2. repaint() built in java method to re-invoke paint() method
            repaint();

            try{
                //noinspection BusyWait
                Thread.sleep(1000L/FPS);
            }
            catch (InterruptedException e){
                System.out.println("interrupted");
            }
        }
    }

}
