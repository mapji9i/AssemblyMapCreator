package coreCreator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

import static coreCreator.Core.core;
import static java.lang.Math.round;

public class Assembly {



    private ArrayList<AssemblyProperty> properties = new ArrayList<>();

    private AssemblyComponent assemblyComponent;

    public Assembly(double id, double x, double y) {

        this.properties.add(new AssemblyProperty("id",(Double) id));
        this.properties.add(new AssemblyProperty("X",(Double) x));
        this.properties.add(new AssemblyProperty("Y",(Double) y));
        this.properties.add(new AssemblyGroup("Group",(Double) 1.0));
        this.assemblyComponent=new AssemblyComponent(this);

    }

    public Assembly(Object[][] propertiesNameAndValues){
        double value;
        for(Object[] nameAndValue:propertiesNameAndValues) {
            if (nameAndValue[0].equals("Group")) {
                value = (Double) nameAndValue[1];
                this.properties.add(new AssemblyGroup(nameAndValue));
                continue;
            }
            this.properties.add(new AssemblyProperty(nameAndValue));
        }
        this.assemblyComponent=new AssemblyComponent(this);
    }

    public double getId(){
        return (Double) this.properties.get(0).getValue();
    }
    public void setId(double id){
        this.properties.get(0).setValue(id);
    }

    public double getX(){
        return (double) this.properties.get(1).getValue();
    }
    public void setX(double x){
         this.properties.get(1).setValue(x);
    }

    public double getY(){
        return (double) this.properties.get(2).getValue();
    }
    public void setY(double y){
        this.properties.get(2).setValue(y);
    }

    public int getGroup(){
        return (int) this.properties.get(3).getValue();
    }
    public void setGroup(int groupId){
        this.properties.get(3).setValue(groupId);
    }

    public Color getGroupColor(){
        return ((AssemblyGroup) this.properties.get(3)).getGroupColor();
    }

    public AssemblyComponent getAssemblyComponent() {

        return assemblyComponent;
    }

    public Object getPropertyValueByName(String propertyName){
        for(AssemblyProperty property:properties)
            if(property.getName().equals(propertyName))
                return property.getValue();
        return null;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter();
        ArrayList<Double> values = new ArrayList<>();
        for(AssemblyProperty property:properties) {
            stringBuilder.append("%16.12f ");
            values.add((Double) property.getValue());

        }
        stringBuilder.append("\n");
        formatter.format(Locale.US,stringBuilder.toString(), values.toArray(new Double[values.size()]));
        return formatter.toString();
    }

    public String getHeader(){
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter();
        ArrayList<String> values = new ArrayList<>();
        for(AssemblyProperty property:properties) {
            stringBuilder.append("%16s ");
            values.add(property.getName());

        }
        stringBuilder.append("\n");
        formatter.format(Locale.US,stringBuilder.toString(), values.toArray(new String[values.size()]));
        return formatter.toString();
    }

    public ArrayList<AssemblyProperty> getProperties() {
        return this.properties;
    }

    public void addNewEmptyProperty(){
        this.properties.add(new AssemblyProperty("NewProperty",0.0));
    }
    public void removePropertyByIndex(int index){
        if(index>3)
            this.properties.remove(index);
    }


}
