import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickHandler implements MouseListener {
    public boolean mouseClicked = false;


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked = true;
        System.out.println("clicked");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
