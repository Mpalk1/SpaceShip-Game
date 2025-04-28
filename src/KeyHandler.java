import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean leftPressed;
    public boolean rightPressed;
    public boolean enterPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(e.getKeyCode() == 10){
            enterPressed = true;
            System.out.println("enter pressed: " + enterPressed);
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
        if(e.getKeyCode() == 10){
            enterPressed = false;
            System.out.println("enter pressed: " + enterPressed);
        }
    }
}
