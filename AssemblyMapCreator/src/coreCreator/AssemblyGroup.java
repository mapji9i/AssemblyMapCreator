package coreCreator;

import java.awt.*;

import static java.lang.Math.round;

public class AssemblyGroup extends AssemblyProperty{


    String friendlyName;
    Color groupColor;
    private final static  Color[] groupColors= new Color[]{
            Color.GREEN,  //1
            Color.ORANGE, //2
            Color.BLUE,   //3
            Color.YELLOW, //4
            Color.MAGENTA, //5
            Color.RED, //6
            Color.PINK, //7
            new Color(225, 198, 121), //8
            new Color(7, 221, 253), //9
            new Color(2, 255, 184), //10
            new Color(139, 67, 255), //11
            new Color(130,20,20), //12
            new Color(157, 255, 0), //13
            new Color(225, 150, 121), //14

            new Color(7, 100, 253), //15
            new Color(100, 150, 100), //16
            new Color(150, 100, 100),//17
            new Color(100,100,150), //18
            new Color(200, 100, 100), //19
            new Color(200, 200, 0) //20
    };


    AssemblyGroup (String name, Double value) {
        super("Group",value);
        this.groupColor = getGroupColorFromScale( (int) round((Double) this.getValue()));
    }
    AssemblyGroup (Object[] nameAndValue) {
        super(nameAndValue);
        this.groupColor = getGroupColorFromScale( (int) round((Double) this.getValue()));
    }



    public String getFriendlyName() {
        return this.friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public Color getGroupColor() {
        return groupColor;
    }

    public void setGroupColor(Color groupColor) {
        this.groupColor = groupColor;
    }
    public static Color getGroupColorFromScale(int groupId){
        if (groupId<=AssemblyGroup.groupColors.length)
            return groupColors[groupId-1];
        return Color.GRAY;
    }

    @Override
    public void setFieldsFromRow(Object[] row) {
        super.setFieldsFromRow(row);
        this.groupColor=getGroupColorFromScale( (int) round((Double) this.getValue()));
    }
}

