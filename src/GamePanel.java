import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    KeyHandler KeyH = new KeyHandler();
    MouseMotionHandler MouseMotionH = new MouseMotionHandler();
    MouseClickHandler MouseClickH = new MouseClickHandler();
    public int FPS = 60;
    public ArrayList<Bullet> bullets = new ArrayList<>(); // TODO change this to linked list
    public long StartTime = System.currentTimeMillis();
    public double angle_temp_x;
    public double angle_temp_y;
    public double angle_tan;
    public double angle;
    public TextField debugField1 = new TextField();
    double bullet_angle;

    public GamePanel(){
        super();
        this.setPreferredSize(new Dimension(1024, 762));
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
        g2D.rotate(angle, Main.ship.center_x, Main.ship.center_y);
        //g2D.rotate(Math.PI/4, Main.ship.pos_x, Main.ship.pos_y);
        g2D.drawImage(Main.ship.shipIcon.getImage(), Main.ship.pos_x, Main.ship.pos_y,Main.ship.width,Main.ship.height, null);

        if(!bullets.isEmpty()){
            for(Bullet bullet: bullets){
                g2D.setTransform(startingTransform);
                g2D.rotate(bullet.rotation, bullet.pos_x, bullet.pos_y);
                g2D.drawImage(bullet.icon.getImage(), bullet.pos_x, bullet.pos_y, bullet.width, bullet.height, null); // TODO naprawic strzelanie
            }
        }
        g2D.setTransform(startingTransform);
    }


    Thread GameThread;

    public void startGameThread(){
        GameThread = new Thread(this);
        GameThread.start(); // starts the run() method
    }

    public void update(){
        Main.ship.center_x = Main.ship.pos_x + (Main.ship.width/2);
        Main.ship.center_y = Main.ship.pos_y + (Main.ship.height/2);
//        ship angle calculations
        if(MouseMotionH.pos_x > Main.ship.center_x && MouseMotionH.pos_y < Main.ship.center_y){ // 1 cwiartka
            angle_temp_x = MouseMotionH.pos_x - Main.ship.center_x;
            angle_temp_y = Main.ship.center_y - MouseMotionH.pos_y;
            bullet_angle = Math.atan2(angle_temp_x, angle_temp_y);
            angle = Math.atan2(angle_temp_x, angle_temp_y);
        }
        if(MouseMotionH.pos_x > Main.ship.center_x && MouseMotionH.pos_y > Main.ship.center_y){ // 2 cwiartka
            angle_temp_x = MouseMotionH.pos_x - Main.ship.center_x;
            angle_temp_y = MouseMotionH.pos_y - Main.ship.center_y;
            bullet_angle = Math.atan2(angle_temp_y, angle_temp_x);
            angle = Math.atan2(angle_temp_y, angle_temp_x) + Math.PI/2;

        }
        if(MouseMotionH.pos_x < Main.ship.center_x && MouseMotionH.pos_y > Main.ship.center_y){ // 3 cwiartka
            angle_temp_x = Main.ship.center_x - MouseMotionH.pos_x;
            angle_temp_y = MouseMotionH.pos_y - Main.ship.center_y;
            bullet_angle = Math.atan2(angle_temp_x, angle_temp_y);
            angle = Math.atan2(angle_temp_x, angle_temp_y) + Math.PI;

        }
        if(MouseMotionH.pos_x < Main.ship.center_x && MouseMotionH.pos_y < Main.ship.center_y){ //4 cwiartka
            angle_temp_x = Main.ship.center_x - MouseMotionH.pos_x;
            angle_temp_y = Main.ship.center_y - MouseMotionH.pos_y;
            bullet_angle = Math.atan2(angle_temp_y, angle_temp_x);
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
                bullets.add(new Bullet(Main.ship.center_x, Main.ship.center_y-35, angle));
            }

        }


        if(!bullets.isEmpty()) {
            for (Bullet bullet : bullets) {
                if (bullet.pos_y > 762 /*|| bullet.pos_y < 0*/) {
                    bullets.remove(bullet);
                    System.out.println("bullet removed");
                }
                bullet.pos_y -= bullet.speed;
//                switch(bullet.direction){
//                    case 1:
//                        bullet.pos_x += bullet.speed;
//                        //bullet.pos_y -= bullet.speed;
//                        break;
//                    case 2:
//                        bullet.pos_x += bullet.speed;
//                        bullet.pos_y += bullet.speed;
//                        break;
//                    case 3:
//                        bullet.pos_x -= bullet.speed;
//                        bullet.pos_y += bullet.speed;
//                        break;
//                    case 4:
//                        bullet.pos_x -= bullet.speed;
//                        bullet.pos_y -= bullet.speed;
//                        break;
//                }
//
//            }
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
