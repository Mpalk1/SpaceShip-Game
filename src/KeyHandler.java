import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean leftPressed;
    public boolean rightPressed;
    public boolean enterPressed;
    public boolean downPressed;
    public boolean upPressed;

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
