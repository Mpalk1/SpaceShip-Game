import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickHandler implements MouseListener {
    public boolean mouseClicked = false;
    public boolean shootingSound = false;


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked = true;
        shootingSound = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = false;
        shootingSound = false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
