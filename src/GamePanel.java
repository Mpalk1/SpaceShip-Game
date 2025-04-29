import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    KeyHandler KeyH = new KeyHandler();
    MouseHandler MouseH = new MouseHandler();
    public int FPS = 60;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public long StartTime = System.currentTimeMillis();
    public double angle_temp_x;
    public double angle_temp_y;
    public double angle_tan;
    public double angle;


    public GamePanel(){
        super();
        this.setPreferredSize(new Dimension(1024, 762));
        this.addKeyListener(KeyH);
        this.addMouseMotionListener(MouseH);
        this.setFocusable(true);
        this.setBackground(Color.black);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); // nie wiem czemu bez tego nie dziala
        Graphics2D g2D = (Graphics2D)g;
        AffineTransform startingTransform = g2D.getTransform();
        g2D.rotate(angle, Main.ship.ship_center_x, Main.ship.ship_center_y); //jakies gowno tu sie dzieje
        g2D.drawImage(Main.ship.shipIcon.getImage(), Main.ship.pos_x, Main.ship.pos_y,Main.ship.width,Main.ship.height, null);
        //g2D.dispose();
        //g2D.setTransform(startingTransform);
        g2D.setPaint(Color.RED);
        if(!bullets.isEmpty()){
            for(Bullet bullet: bullets){
                g2D.fillRect(bullet.pos_x, bullet.pos_y, 20, 20);
            }
        }
    }


    Thread GameThread;

    public void startGameThread(){
        GameThread = new Thread(this);
        GameThread.start(); // starts the run() method
    }

    public void update(){
        int ship_center_x = Main.ship.pos_x + (Main.ship.width/2);
        int ship_center_y = Main.ship.pos_y+ (Main.ship.height/2);
        //ship angle calculations
        angle_temp_x = MouseH.pos_x - Main.ship.ship_center_x;
        angle_temp_y = MouseH.pos_y - Main.ship.ship_center_x;
        angle = Math.atan2(angle_temp_x, angle_temp_y);
        System.out.println("x: " + angle_temp_x + ", y: " + angle_temp_y + ", angle: " + angle);
        //input handling
        if(KeyH.leftPressed && Main.ship.pos_x > -10){
            Main.ship.pos_x -= Main.ship.speed;
            System.out.println("pressed a");
        }
        if(KeyH.rightPressed && Main.ship.pos_x < 1024-63){
            Main.ship.pos_x += Main.ship.speed;
            System.out.println("pressed d");
        }
        if(KeyH.downPressed && Main.ship.pos_y < 762-70){
            Main.ship.pos_y += Main.ship.speed;
            System.out.println("pressed d");
        }
        if(KeyH.upPressed && Main.ship.pos_y > 0){
            Main.ship.pos_y -= Main.ship.speed;
            System.out.println("pressed d");
        }
        if(KeyH.enterPressed){
            if(System.currentTimeMillis() - StartTime > 200){
                StartTime = System.currentTimeMillis();
                bullets.add(new Bullet(Main.ship.pos_x + 25, Main.ship.pos_y));
            }

        }
        if(!bullets.isEmpty()){
            for(Bullet bullet: bullets){
                if(bullet.pos_y > 762){
                    bullets.remove(bullet);
                }
                bullet.pos_y -= bullet.speed;
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
