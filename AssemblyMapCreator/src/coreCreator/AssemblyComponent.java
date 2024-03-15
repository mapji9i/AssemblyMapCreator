package coreCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static coreCreator.Core.core;
import static coreCreator.CoreComponent.coreComponent;
import static coreCreator.MainForm.getMainForm;
import static java.awt.event.KeyEvent.*;
import static java.lang.Math.*;


public class AssemblyComponent extends JButton {
    Assembly assembly;
    static boolean ctrlKeyPressed=false;

    int x0,y0;

    public Color fillColor;
    private int [][] pointCoords;


    public AssemblyComponent(Assembly assembly) {
        super("");
        this.assembly = assembly;
        setBorder(null);
        setBorderPainted(false);
        setOpaque(true);
        setFocusPainted(false);
        setContentAreaFilled(false);
        this.fillColor = Color.WHITE;
        updateGeometry();

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Assembly tmpAssembly = ((AssemblyComponent) e.getSource()).assembly;
                if (!ctrlKeyPressed) core.clearSelection();

                if (!core.selection.contains(tmpAssembly)) {
                    core.addToSelection(tmpAssembly);
                } else {
                    core.removeFromSelection(tmpAssembly);
                }

            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == VK_CONTROL)
                    ctrlKeyPressed = true;
                moveOnMap(e);
                if (e.getKeyCode() == VK_DELETE || e.getKeyCode() == VK_X) {
                    getMainForm().deleteAssembly();
                }
                if (e.getKeyCode() == VK_ENTER || e.getKeyCode() == VK_E) {
                    getMainForm().editCurrentPropertyValueCell();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == VK_CONTROL)
                    ctrlKeyPressed = false;
            }
        });

    }

    public void updateGeometry(){
        coreComponent.calcScale();
        this.pointCoords = generatePoints(assembly.getX(), assembly.getY(), core.assemblyStep);
        setScaledSize();
        //this.fillColor = assembly.group.groupColor;
        getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        reposition();
    }

    private void moveOnMap(KeyEvent e) {
        double direction=0;
        Assembly assembly=null;
        switch (e.getKeyCode()) {
            case VK_UP:
            case VK_W:
                direction = core.rotationAngle % (Math.PI / 3) + Math.PI / 3;
                assembly=doStep(direction);
                break;
            case VK_DOWN:
            case VK_S:
                direction = core.rotationAngle % (Math.PI / 3) + 4 * Math.PI / 3;
                assembly=doStep(direction);
                break;
            case VK_LEFT:
            case VK_A:
                direction = core.rotationAngle % (Math.PI / 3) + Math.PI;
                assembly=doStep(direction);
                break;
            case VK_RIGHT:
            case VK_D:
                direction = core.rotationAngle % (Math.PI / 3);
                assembly=doStep(direction);
                break;
            case VK_SPACE:
            case VK_N:
               assembly=getNextId();
        }
        if(assembly!=null)
            core.addToSelection(assembly);
    }
    private Assembly getNextId(){
        Assembly selectedAssembly = core.selection.get(core.selection.size()-1);
        for (Assembly tmpassembly: core.assemblies){
            if (tmpassembly.getId()-1==selectedAssembly.getId()){
                return tmpassembly;
            }
        }
        return null;
    }


    public Assembly doStep(double direction){
        int coreCount=4;
        ArrayList<Assembly> selection = core.selection;
        if(selection.isEmpty()) return null;
        Assembly selectedAssembly = selection.get(selection.size()-1);

        FindAssembly[] findThreads = new FindAssembly[coreCount];
        ArrayList<Assembly> searchField = core.assemblies;
        int searchDelta=(searchField.size() /coreCount);
        int start=0,end=searchDelta;
        for (int i=0; i<coreCount; i++){
            findThreads[i]=new FindAssembly(searchField.subList(start,end).toArray(new Assembly[end-start]) ,selectedAssembly,direction);
            findThreads[i].start();
            start=end;
            end=(((searchField.size()-start)>searchDelta)?(end+searchDelta+1):(end+(searchField.size()-start)));
        }

        for (FindAssembly findThread : findThreads) {
            try {
                findThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (FindAssembly.findedKey) {
            return FindAssembly.getSearchResult();
//            if (!ctrlKeyPressed)
//                core.clearSelection();
            //core.addToSelection(FindAssembly.getSearchResult());
        }
        return null;
    }



    private void setOnCoordinate(int x, int y){
        setLocation(x-getWidth()/2, y-getHeight()/2);
    }

    private void setScaledSize(){
        Dimension size=getSizeFromVertex();
        setSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        this.x0=size.width/2;
        this.y0=size.height/2;
        setLayout(null);


    }
    @Override
     public void paint(Graphics g) {
        super.paint(g);
        this.pointCoords = generatePoints(assembly.getX(), assembly.getY(), core.assemblyStep);
        setScaledSize();
        reposition();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.white);
        String fieldname = coreComponent.getDrawingFieldName();
        MainForm form = MainForm.getMainForm();
        if (fieldname != null) {
            double value = (Double) assembly.getPropertyValueByName(fieldname);
            String numberFormat = "%6." + MainForm.getMainForm().getDigitAfterPoint() + "f";
            String valueToDraw = String.format(numberFormat
                    , value).replaceAll(",?0*$", "");
            //double[] minMaxValues=AssemblyProperty.getMinMaxValue(fieldname);
            if (!core.selection.contains(this.assembly)) {
                switch (form.getColorType()) {
                    case (0):
                        this.fillColor = Color.WHITE;
                        break;
                    case (1):
                        this.fillColor = getColor(10, value);
                        break;
                    case (2):
                        this.fillColor = assembly.getGroupColor();
                        break;
                }
            }
            drawHexagon(g2d, fillColor);
            if (form.getShowValueKey()) {
                int[] fontDimensions = findPropertyTextSize(g, getWidth(), getHeight());
                g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, fontDimensions[1]));
                g.drawString(valueToDraw, getWidth() / 2 - (fontDimensions[0] / 5) * valueToDraw.length() / 2, getHeight() / 2 + fontDimensions[1] / 2);
            }
        } else {
            if (!core.selection.contains(this.assembly))
                this.fillColor = assembly.getGroupColor();
            drawHexagon(g2d, fillColor);
        }


    }

    private Color getColor(int numberOfColors, double value) {
        String fieldname = coreComponent.getDrawingFieldName();
        double[] minMaxValues = AssemblyProperty.getMinMaxValue(fieldname);
        float linearValue = (float) (240 / (360 * (minMaxValues[1] - minMaxValues[0])) * (minMaxValues[1] - value));
        float delta = (float) 240 / (360 * (numberOfColors + 1));
        int numberOfRange = (int) (linearValue / delta);
        float h = (delta * numberOfRange);
        return Color.getHSBColor((float) (240 / (360 * (minMaxValues[1] - minMaxValues[0])) * (minMaxValues[1] - value)), 1f, 1f);
        //return Color.getHSBColor(h, 1f, 1f);
    }

    private void reposition() {
        int[] coordinates = coreComponent.convertCoordinates(assembly.getX(), assembly.getY());
        setOnCoordinate(coordinates[0], coordinates[1]);
    }

    private int[] findPropertyTextSize(Graphics g, int widht, int height) {
        double portion = MainForm.getMainForm().getTextFontPart();
        int textWidth = widht, textHeight = height;
        String stringToDraw = null;
        for (Assembly assembly : core.assemblies) {
            textWidth=widht;
            textHeight += 1;
            String numberFormat = "%6." + MainForm.getMainForm().getDigitAfterPoint() + "f";

            stringToDraw = String.format(numberFormat, 1.0);
            while (textWidth > 0.7 * widht) {
                textHeight -= 1;
                g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, textHeight));
                textWidth = g.getFontMetrics().charsWidth(stringToDraw.toCharArray(), 0, stringToDraw.length());
            }
        }
        //if (textHeight>portion*height)
        textHeight = (int) round(portion * height);
        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, textHeight));
        textWidth = g.getFontMetrics().charsWidth(stringToDraw.toCharArray(), 0, stringToDraw.length());
        return new int[]{textWidth, textHeight};
    }


    private int[][] generatePoints(double x0, double y0, double step){
        double scale = coreComponent.scale;
        int [][] result = new int[2][6];
        double radius=step/(2*Math.cos(Math.PI/6));
        double[] coords;
        for(int i=0; i<6;i++) {
            coords = new double[2];
            coords[1] = (radius * Math.cos(Math.PI / 3 * i) );
            coords[0]= (radius * Math.sin(Math.PI / 3 * i) );
            coords= Core.rotateCoordinates(coords[0],coords[1],1,1,core.rotationAngle);
            result[1][i]=(int) round(-coords[1]*scale+getHeight()/2);
            result[0][i]=(int) round(coords[0]*scale+getWidth()/2);
        }
        return result;
    }
    private void drawHexagon (Graphics g, Color color){
        int lineWidth = (int) ceil(Math.min(getHeight() * 0.03, getWidth() * 0.03));
        if(lineWidth<1) lineWidth=1;
        Graphics2D g2d=(Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(lineWidth));
        g2d.fillPolygon(pointCoords[0],pointCoords[1], 6);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(pointCoords[0],pointCoords[1], 6);


    }
    private Dimension getSizeFromVertex(){
        int max,min;
        int[] result = new int[2];

        for(int i=0; i<2;i++) {
            max = pointCoords[i][0];
            min = max;
            for (int j = 0;j<pointCoords[i].length;j++){
                if (max<pointCoords[i][j]) max=pointCoords[i][j];
                if (min>pointCoords[i][j]) min=pointCoords[i][j];
            }
            result[i]=max-min;
            if(result[i]%2==0)
                result[i]+=1;

        }
        return new Dimension(result[0],result[1]);
    }


}
class FindAssembly extends Thread{
    public static boolean findedKey=false;
    private final Assembly[] searchField;
    private final double searchDirection;
    private final Assembly searchOrigin;
    private static Assembly searchResult;
    FindAssembly(Assembly[] searchField, Assembly searchOrigin, double searchDirection){
        super();
        this.searchDirection=searchDirection;
        this.searchField=searchField;
        this.searchOrigin=searchOrigin;

    }

    @Override
    public void run() {
        super.run();
        for(Assembly assembly:searchField){
            if (findedKey) break;
                if ((abs(assembly.getX() - searchOrigin.getX() - core.assemblyStep * cos(searchDirection)) < 0.0001)
                        && (abs(assembly.getY() - searchOrigin.getY() - core.assemblyStep * sin(searchDirection)) < 0.0001) ){
                    searchResult=assembly;
                    findedKey=true;
                }
            }
    }

    public static Assembly getSearchResult() {
        //searchResult=null;
        findedKey=false;
        return searchResult;
    }
}
