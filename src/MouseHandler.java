import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseMotionListener {

    public double pos_x;
    public double pos_y;


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        pos_x = e.getX();
        pos_y = e.getY();
//        System.out.println("Mouse position -> x: " + e.getX() + ", y:" + e.getY());
    }
}
