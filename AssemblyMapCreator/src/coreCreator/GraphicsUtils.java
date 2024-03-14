package coreCreator;

import java.awt.*;

public class GraphicsUtils {
    public static void drawCircle(Graphics2D g, int x0, int y0, int radius){
        g.drawOval(x0-radius,y0-radius,radius*2,radius*2);
    }
}
