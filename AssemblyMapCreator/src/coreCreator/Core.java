package coreCreator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static coreCreator.CoreComponent.coreComponent;
import static java.lang.Math.round;

public class Core {
    public static Core core;

    ArrayList<Assembly> assemblies;
    double zoneOuterRadius;
    double assemblyStep;
    double indent;
   // ArrayList<AssemblyGroup> assemblyGroups;

    MainForm mainForm;

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    double rotationAngle;
    ArrayList<Assembly> selection;


    public Core(MainForm mainForm) {

        this.assemblies = new ArrayList<>();

        this.zoneOuterRadius = mainForm.getCoreRadiusValue();

        this.assemblyStep = mainForm.getAssemblyStepValue();

        this.indent = mainForm.getIndentValue();

        //this.assemblyGroups = setDefaultGroup();

        this.mainForm=mainForm;

        this.rotationAngle=0;
        this.selection=new ArrayList<>();
        Core.core=this;
    }

//    private ArrayList<AssemblyGroup> setDefaultGroup(){
//        ArrayList<AssemblyGroup> result= new ArrayList<AssemblyGroup>();
//        result.add(new AssemblyGroup((double) 1,Color.GREEN));
//        result.add(new AssemblyGroup((double) 2,Color.BLUE));
//        result.add(new AssemblyGroup((double) 3,Color.RED));
//        result.add(new AssemblyGroup((double) 4,Color.ORANGE));
//        result.add(new AssemblyGroup((double) 5, Color.YELLOW));
//        return result;
//    }
    public String createExportString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Assembly assembly:assemblies){
            if(stringBuilder.isEmpty())
                stringBuilder.append(assembly.getHeader());
            stringBuilder.append(assembly.toString());
        }
        return  stringBuilder.toString();
    }
    public void clearSelection(){
        if (selection.size()!=0) {
            for (Assembly assembly : selection)
                assembly.getAssemblyComponent().fillColor = assembly.getGroupColor();
            selection.clear();
            repaintCore();
            mainForm.addPropertiesToTable();
        }
    }
    public void removeFromSelection(Assembly assembly){
        assembly.getAssemblyComponent().fillColor=assembly.getGroupColor();
        selection.remove(assembly);
        repaintCore();
        mainForm.addPropertiesToTable();
    }

    public void addToSelection(Assembly assembly){
        if(!AssemblyComponent.ctrlKeyPressed)
            core.clearSelection();
        assembly.getAssemblyComponent().fillColor=Color.cyan;
        selection.add(assembly);
        repaintCore();
        mainForm.addPropertiesToTable();
    }


    public double[][] generateCoordinates(){
        rotationAngle=0;
        this.assemblies.clear();
        coreComponent.removeAll();
        ArrayList<double[]> result= new ArrayList<double[]>();

        double x0, y0, dx, dy;

        int numCols, numRows;

        numCols= (int) ((round(zoneOuterRadius/(assemblyStep*Math.sin(Math.PI/6)))));
        if(numCols%2!=0)
            numCols+=1;
        numCols*=2;

        numRows=numCols;

        dx=assemblyStep;

        dy=assemblyStep*Math.cos(Math.PI/6);

        x0=-((int) numCols/2)*dx;

        y0=-((int) numCols/2)*dy;

        for(int row=0; row<=numRows; row++){
            for(int col=0; col<=numCols; col++){
                double[] tmpArr = new double [] {-1,0,0,0};
                tmpArr[1]=x0+dx*col;
                tmpArr[2]=y0;
                double [] newCoordinates = rotateCoordinates(tmpArr[1], tmpArr[2]=y0, 1, 1, rotationAngle);
                tmpArr[1] = newCoordinates[0];
                tmpArr[2] = newCoordinates[1];
                if(checkInnerPoint(tmpArr[1],tmpArr[2],zoneOuterRadius,indent)){
                    tmpArr[0]=-1;
                    result.add(tmpArr);
                    addAssembly(new Assembly(-1, tmpArr[1],tmpArr[2]));
                }
            }
            x0+=dx/2*Math.pow(-1,row);
            y0+=dy;

        }

        return result.toArray(new double[result.size()][4]);
    }
    public void removeAssembly(Assembly assembly){
        assemblies.remove(assembly);
        coreComponent.remove(assembly.getAssemblyComponent());
    }
    public void repaintCore(){
        CoreComponent.coreComponent.repaint();
//        for (Assembly assembly:assemblies)
//            assembly.getAssemblyComponent().repaint();
    }

    public void addAssembly(Assembly assembly){
        assemblies.add(assembly);
        coreComponent.add(assembly.getAssemblyComponent());
    }
    public void clearAssemblies(){
        selection.clear();
        assemblies.clear();
        coreComponent.removeAll();
    }

    static boolean checkInnerPoint(double x,double y, double radiusForCheck, double pitch){

        double radiusVector= Math.sqrt(Math.pow(x,2)+Math.pow(y,2));


        if (radiusForCheck-radiusVector>=pitch) {

            return true;
        }

        return false;
    }

    static double[] rotateCoordinates(double x, double y, int xMirrorkey, int yMirrorKey, double rotationAngle){
        double[] result = new double[2];

        double radiusVector= Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        double newAngle = Math.atan2(y,x)+rotationAngle;
        result[0]=xMirrorkey*(radiusVector*Math.cos(newAngle));
        result[1]=yMirrorKey*(radiusVector*Math.sin(newAngle));
//        result[0]=(double) round( xMirrorkey*(radiusVector*Math.cos(newAngle))*1000000)/1000000;
//        result[1]=(double) round(yMirrorKey*(radiusVector*Math.sin(newAngle))*1000000)/1000000;
        return result;
    }

    public double getAssemblyStep() {
        return assemblyStep;
    }

    public void setAssemblyStep(double assemblyStep) {
        this.assemblyStep = assemblyStep;
        mainForm.setAssemblyStepTextFieldValue(assemblyStep);

    }

    public double getIndent() {
        return indent;
    }

    public void setIndent(double indent) {
        this.indent = indent;
        mainForm.setIndentTextFieldValue(indent);

    }
    public double getZoneOuterRadius() {
        return zoneOuterRadius;
    }

    public void setZoneOuterRadius(double zoneOuterRadius) {
        this.zoneOuterRadius = zoneOuterRadius;
        mainForm.setCoreRadiusTextFieldValue(zoneOuterRadius);
        coreComponent.calcScale();
        //System.out.println(zoneOuterRadius);
    }
    public int getAssemblyCount(){
        return assemblies.size();
    }

    public void addAssemblies(Assembly[] assemblies) {
        for (Assembly assembly:assemblies) {
            this.assemblies.add(assembly);
            coreComponent.add(assembly.getAssemblyComponent());
        }
    }
}
