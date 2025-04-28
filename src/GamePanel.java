import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    KeyHandler KeyH = new KeyHandler();
    public int FPS = 60;
    public int bullet_pos_x1 = Main.ship.pos_x + 35;
    public int bullet_pos_y1 = Main.ship.pos_y + 35;
    public int bullet_pos_x2 = Main.ship.pos_x - 35;
    public int bullet_pos_y2 = Main.ship.pos_y + 40;
    public int bullet_speed = 10;
    public ArrayList<Bullet> bullets = new ArrayList<>();

    public GamePanel(){
        super();
        this.setPreferredSize(new Dimension(1024, 762));
        this.addKeyListener(KeyH);
        this.setFocusable(true);
        this.setBackground(Color.black);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); // nie wiem czemu bez tego nie dziala
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(Main.ship.shipIcon.getImage(), Main.ship.pos_x, Main.ship.pos_y,Main.ship.width,Main.ship.height, this);
        //g2D.dispose();
        g2D.setPaint(Color.white);
        g2D.drawLine(bullet_pos_x1, bullet_pos_y1, bullet_pos_x2, bullet_pos_y2);
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
        if(KeyH.leftPressed && Main.ship.pos_x > -10){
            Main.ship.pos_x -= Main.ship.speed;
            System.out.println("pressed a");
        }
        if(KeyH.rightPressed && Main.ship.pos_x < 1024-70){
            Main.ship.pos_x += Main.ship.speed;
            System.out.println("pressed d");
        }
        if(KeyH.enterPressed){
            bullets.add(new Bullet(Main.ship.pos_x + 25, Main.ship.pos_y));
        }
        if(!bullets.isEmpty()){
            for(Bullet bullet: bullets){
                if(bullet.pos_y > 762){
                    bullets.remove(bullet);
                }
                bullet.pos_y -= bullet.speed;
            }
        }
        // TODO: ADD A COOLDOWN FOR SHOOTING
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
