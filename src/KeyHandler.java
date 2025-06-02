import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean leftPressed;
    public boolean rightPressed;
    public boolean enterPressed;
    public boolean downPressed;
    public boolean upPressed;
    public String selectedGun = "default";

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == '1'){
            selectedGun = "default";
            System.out.println(selectedGun);
        }
        if(e.getKeyChar() == '2'){
            selectedGun = "rifle";
            System.out.println(selectedGun);
        }
        if(e.getKeyChar() == '3'){
            selectedGun = "shotgun";
            System.out.println(selectedGun);
        }
        if(e.getKeyChar() == '4'){
            selectedGun = "sniper";
            System.out.println(selectedGun);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            downPressed= true;
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            upPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'a'){
            leftPressed = false;
        }
        if(e.getKeyChar() == 'd'){
            rightPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            downPressed= false;
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            upPressed = false;
        }
    }
}
