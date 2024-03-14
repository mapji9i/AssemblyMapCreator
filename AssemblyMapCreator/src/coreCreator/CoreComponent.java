package coreCreator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static coreCreator.Core.core;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_W;
import static java.lang.Math.*;

public class CoreComponent extends JPanel {
    public static CoreComponent coreComponent;

    public String getDrawingFieldName() {
        return drawingFieldName;
    }

    public void setDrawingFieldName(String drowingFieldName) {
        this.drawingFieldName = drowingFieldName;
    }

    private String drawingFieldName=null;

    public int getDrawingFieldIndex() {
        return drawingFieldIndex;
    }

    public void setDrawingFieldIndex(int drawingFieldIndex) {
        this.drawingFieldIndex = drawingFieldIndex;
    }

    private int drawingFieldIndex=-1;
    public double scale;
    private double x0;
    private double y0;
    public CoreComponent(Core core) {


        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder title = BorderFactory.createTitledBorder(lineBorder,"Core image");
        setBorder(title);
        setLayout(null);
        CoreComponent.coreComponent=this;
       // addListeners();

    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        calcScale();
        Graphics2D g2d=(Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.clearRect(20, 20, getWidth()-40, getHeight()-40);
        g2d.setColor(Color.white);
        g2d.fillRect(20, 20, getWidth()-40, getHeight()-40);
        g2d.setColor(Color.black);
        int [] centerCoordinate = convertCoordinates(0,0);
        GraphicsUtils.drawCircle(g2d,centerCoordinate[0],centerCoordinate[1],scaleLength(core.getZoneOuterRadius()));
        g.setFont(new Font("TimesRoman", Font.BOLD,  20));
        if(drawingFieldName!=null)
            g.drawString("Property: "+drawingFieldName, 50,50);
    }

    public void calcScale(){
        double coreRadius=core.getZoneOuterRadius();
        scale=Math.min((this.getWidth()/2-50)/coreRadius, (this.getHeight()/2-50)/coreRadius);

    }
    public int scaleLength(double length){
        int result = (int) Math.round(scale*length);
        return result;
    }
    public int[] convertCoordinates(double x,double y){
        int [] result = new int[] {
                (int) Math.round(this.getWidth()/2+scaleLength(x)),
                (int) Math.round(this.getHeight()/2-scaleLength(y))
                                };
        return result;
    }

    @Override
    public Component add(Component comp) {

        if(comp.getClass().getSimpleName().equals("AssemblyComponent"))
            ((AssemblyComponent) comp).updateGeometry();
        return super.add(comp);
    }
}
