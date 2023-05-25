
import io.jbotsim.core.Topology;
import io.jbotsim.ui.painting.UIComponent;
import io.jbotsim.ui.painting.BackgroundPainter;

import java.awt.*;


public class AreaBackground implements BackgroundPainter {
    @Override
    public void paintBackground(UIComponent c, Topology tp) {
        Graphics2D g = (Graphics2D) c.getComponent();

        // Setting a background image
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = tk.getImage(getClass().getResource("/forest.jpg"));
        g.drawImage(image, 0, 0, null);
        /*
        g.setColor(Color.GRAY);

        g.drawLine(50, 50, tp.getWidth()-50,50);
        g.drawLine(50, 50, 50, tp.getHeight()-50);
        g.drawLine(tp.getWidth()-50, 50, tp.getWidth()-50, tp.getHeight()-50);
        g.drawLine(50, tp.getHeight()-50, tp.getWidth()-50, tp.getHeight()-50);*/
    }
}