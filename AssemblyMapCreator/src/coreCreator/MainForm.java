package coreCreator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import static coreCreator.Core.core;
import static coreCreator.CoreComponent.coreComponent;
import static java.lang.Math.*;

public class MainForm {
    private JPanel mainPanel;

    private JPanel coreParameters;
    private JTextField coreRadiusTextField;
    private JPanel UpPanel;
    private JTextField assemblyStepTextField;
    private JLabel AssemblyStep;
    private JTextField indentTextField;
    private JButton FlipAxis;
    private JButton generateNumerationButton;
    private JButton rotatePlus60DegButton;
    private JButton rotateMinus60DegButton;
    private JButton mirrorXAxisButton;
    private JButton mirrorYAxisButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton exitButton;
    private JButton deleteAssemblyButton;
    public JLabel assemblyCount;
    private JButton generateAssebliesButton;
    private JPanel assemblyProperties;
    private JButton setPropertiesButton;
    private JButton addPropertyButton;
    private JScrollPane tableScrollPane;
    private JTable tableProperties;
    private JButton hidePropertyButton;
    private JButton saveAsButton;
    private JButton removePropertyButton;

    private JLabel textFormatLabel;
    private JComboBox colorType;
    private JCheckBox showValueCheckBox;
    private JSlider slider1;
    private JSpinner spinner1;


    public boolean getShowValueKey() {
        return showValueCheckBox.isSelected();
    }

    public int getColorType() {
        return colorType.getSelectedIndex();
    }

    private static JFrame frame;

    public static MainForm getMainForm() {
        return mainForm;
    }

    private static MainForm mainForm;

    public void visibleFormatAtributes(Boolean enable) {
        slider1.setEnabled(enable);
        spinner1.setEnabled(enable);
        if (!enable) {
            slider1.setValue(30);
            spinner1.setValue(4);
        }
    }

    public String getDigitAfterPoint() {
        return spinner1.getValue().toString();
    }

    public double getTextFontPart() {
        return slider1.getValue() / 100.0;
    }

    public MainForm() {
        mainForm = this;
        slider1.setMaximum(100);
        slider1.setMinimum(0);
        slider1.setValue(30);
        slider1.setEnabled(false);
        spinner1.setValue(4);
        spinner1.setEnabled(false);

        showValueCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                core.repaintCore();
            }
        });
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                core.repaintCore();
            }
        });
        spinner1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                core.repaintCore();
            }
        });
        colorType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                core.repaintCore();
            }
        });
        FlipAxis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[] newCoordinates;
                for (Assembly assembly : core.assemblies) {

                    newCoordinates = Core.rotateCoordinates(assembly.getX(), assembly.getY(), 1, -1, Math.PI / 2);
                    assembly.setX(newCoordinates[0]);
                    assembly.setY(newCoordinates[1]);


                }
                core.rotationAngle+=Math.PI/2;
                core.repaintCore();

            }
        });


        coreRadiusTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER)
                    updateCoreParameters();
                core.repaintCore();


            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        assemblyStepTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER)
                    updateCoreParameters();
                core.repaintCore();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        indentTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER)
                    updateCoreParameters();
                core.repaintCore();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


//        indentTune.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                int currvalue = ((JSlider) e.getSource()).getValue();
//
//                System.out.println(tuneDefaultValue);
//                indentTextField.setText(Double.toString(currvalue * tuneDefaultValue / 100));
//                updateCoreParameters();
//            }
//        });
        rotatePlus60DegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[] newCoordinates;
                for (Assembly assembly : core.assemblies) {

                    newCoordinates = Core.rotateCoordinates(assembly.getX(), assembly.getY(), 1, 1, Math.PI / 3);
                    assembly.setX(newCoordinates[0]);
                    assembly.setY(newCoordinates[1]);
                    core.rotationAngle+=Math.PI / 3;

                }
                core.repaintCore();

            }
        });
        rotateMinus60DegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[] newCoordinates;
                for (Assembly assembly : core.assemblies) {

                    newCoordinates = Core.rotateCoordinates(assembly.getX(), assembly.getY(), 1, 1, -Math.PI / 3);
                    assembly.setX(newCoordinates[0]);
                    assembly.setY(newCoordinates[1]);
                    core.rotationAngle-=Math.PI / 3;
                }
                core.repaintCore();

            }
        });
        mirrorXAxisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[] newCoordinates;
                for (Assembly assembly : core.assemblies) {

                    newCoordinates = Core.rotateCoordinates(assembly.getX(), assembly.getY(),  -1, 1, 0);
                    assembly.setX(newCoordinates[0]);
                    assembly.setY(newCoordinates[1]);

                }
                core.repaintCore();

            }
        });
        mirrorYAxisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[] newCoordinates;
                for (Assembly assembly : core.assemblies) {

                    newCoordinates = Core.rotateCoordinates(assembly.getX(), assembly.getY(),  1, -1, 0);
                    assembly.setX(newCoordinates[0]);
                    assembly.setY(newCoordinates[1]);

                }
                core.repaintCore();

            }
        });

        deleteAssemblyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAssembly();
            }
        });
        generateAssebliesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateCoreParameters();
                core.generateCoordinates();
                setCoreAssemblyCountView(String.valueOf(core.assemblies.size()));
                core.repaintCore();
                visibleFormatAtributes(true);
            }
        });

        generateNumerationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                double index=0;
                for(Assembly assembly:core.assemblies){
                    assembly.setId(++index);
                }
                coreComponent.setDrawingFieldName("id");
                coreComponent.setDrawingFieldIndex(0);
                core.repaintCore();
            }
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });


        hidePropertyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                coreComponent.setDrawingFieldName(null);
                core.repaintCore();
            }
        });
        setPropertiesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            applyProperties();

            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOperation(false);
            }
        });
        saveAsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveOperation(true);
            }
        });
        addPropertyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                for(Assembly assembly: core.assemblies)
                    assembly.addNewEmptyProperty();

                addPropertiesToTable();
            }
        });
        removePropertyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int removeRowIndex=tableProperties.getSelectedRow();
                for(Assembly assembly: core.assemblies)
                    assembly.removePropertyByIndex(removeRowIndex);
                addPropertiesToTable();
            }
        });
        tableProperties.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                    applyProperties();
            }
        });
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                core.clearAssemblies();

                String[][] dataFromFile= readFileOperation();
                if (dataFromFile!=null) {
                    Assembly[] assemblies=createAssemblyFromFileData(dataFromFile);
                    double[] coreParameters=getCoreRadiusAndStep(assemblies);
                    core.setAssemblyStep(coreParameters[1]);
                    core.setIndent(coreParameters[1]);
                    core.setZoneOuterRadius(coreParameters[0]);
                    core.setRotationAngle(coreParameters[2]);
                    core.addAssemblies(assemblies);
                    updateCoreAssemblyCountView();
                }
            }
        });
    }

    private void saveOperation(Boolean clearKey){
        File resultFile;
        if(SaveLoadFileChooser.getCurrentFile()==null || clearKey) {
            SaveLoadFileChooser fc = new SaveLoadFileChooser();
            fc.showSaveDialog(frame);
            resultFile = fc.getSelectedFile();
        }else{
            resultFile= SaveLoadFileChooser.getCurrentFile();
        }
        if (resultFile!=null){
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(resultFile));
                writer.write(core.createExportString());
                writer.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private String[][] readFileOperation()  {
        File resultFile;
        SaveLoadFileChooser fc = new SaveLoadFileChooser();
        fc.showOpenDialog(frame);
        resultFile = fc.getFileToLoad();
        ArrayList<String[]> allValuesFromFile= new ArrayList<>();
        String[] valuesFromFile;
        String tmpString;
        if (resultFile==null) return null;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(resultFile));
                while((tmpString=reader.readLine())!=null){
                    valuesFromFile=tmpString
                            .replaceAll("[,;]+"," ")
                            .replaceAll("^ +","")
                            .replaceAll(" $+","")
                            .split(" +");
                    allValuesFromFile.add(valuesFromFile);
                }
            }catch(IOException e){

            }
        return allValuesFromFile.toArray(new String[allValuesFromFile.size()]
                                                   [allValuesFromFile.get(0).length]);
    }

    private Assembly[] createAssemblyFromFileData(String[][] data){

        ArrayList<Assembly> assemblies=new ArrayList<>();
        Object[][] nameAndValue;
        for(int i=1; i< data.length;i++){
            nameAndValue = new Object[data[i].length][2];
            for(int j=0; j<data[i].length; j++){
                nameAndValue[j][0]=data[0][j];
                nameAndValue[j][1]=Double.parseDouble(data[i][j]);
            }
            assemblies.add(new Assembly(nameAndValue));
        }
        return assemblies.toArray(new Assembly[assemblies.size()]);
    }

    private double[] getCoreRadiusAndStep(Assembly[] assemblies){
        double[] result = new double[]{0,1000,2*PI};
        double[] radiusAndAngle = new double[2];
        for (Assembly assembly1:assemblies){
            for (Assembly assembly2:assemblies){
                radiusAndAngle=getRadiusVectorAndAngle(assembly1,assembly2);
                if (radiusAndAngle[0]==0) continue;
                if (result[0]<radiusAndAngle[0]) result[0]=radiusAndAngle[0];

                if (result[1]>radiusAndAngle[0]) {
                    result[1] = radiusAndAngle[0];
                    if (result[2] > radiusAndAngle[1] && radiusAndAngle[1] >= 0)
                        result[2] = radiusAndAngle[1];
                }
            }
            result[0]/=2;
            result[0]+=result[1];
        }
        return result;
    }

    private double[] getRadiusVectorAndAngle(Assembly assembly1, Assembly assembly2){
        double[] result = new double[2];
        double dx = assembly1.getX()-assembly2.getX();
        double dy = assembly1.getY()-assembly2.getY();
            result[0]=sqrt(pow(dx,2)+pow(dy,2));
            result[1]=atan2(dy,dx);
        return result;
    }

    void setCoreAssemblyCountView(String assemblyCountText){
        assemblyCount.setText("Assembly count="+assemblyCountText);
    }
    void updateCoreAssemblyCountView(){
        setCoreAssemblyCountView(String.valueOf(core.getAssemblyCount()));
    }
    public void updateCoreParameters() {
        core.setAssemblyStep(getAssemblyStepValue());
        core.setZoneOuterRadius(getCoreRadiusValue());
        core.setIndent(getIndentValue());
        core.generateCoordinates();
        setCoreAssemblyCountView(String.valueOf(core.assemblies.size()));
    }
    public double getCoreRadiusValue() {
        return Double.parseDouble(coreRadiusTextField.getText());
    }
    public double getAssemblyStepValue() {
        return Double.parseDouble(assemblyStepTextField.getText());
    }
    public double getIndentValue() {
        return Double.parseDouble(indentTextField.getText());
    }

    public void setCoreRadiusTextFieldValue(double value) {
        coreRadiusTextField.setText(String.valueOf(value));
    }
    public void setAssemblyStepTextFieldValue(double value){
        assemblyStepTextField.setText(String.valueOf(value));
    }
    public void setIndentTextFieldValue(double value) {
        indentTextField.setText(String.valueOf(value));
    }

    public void deleteAssembly(){
        ArrayList<Assembly> newAssemblies=core.assemblies;
        Assembly newSelectedAssembly=null;
        double direction=core.rotationAngle % (Math.PI / 3)-Math.PI / 3;
        int counter=0;
        while (newSelectedAssembly==null && counter<6) {
            direction += Math.PI / 3;
            newSelectedAssembly = core.assemblies.get(0).getAssemblyComponent().doStep(direction);
            counter++;
        }
        for(Assembly assembly: core.selection) {
            coreComponent.remove(assembly.getAssemblyComponent());
            newAssemblies.remove(assembly);
        }
        core.assemblies=newAssemblies;
        core.selection.clear();
        if(newSelectedAssembly!=null)
            core.addToSelection(newSelectedAssembly);
        core.repaintCore();
        setCoreAssemblyCountView(String.valueOf(core.assemblies.size()));
        AssemblyComponent.ctrlKeyPressed=false;
    }
    private void createUIComponents() {
    }

        // TODO: place custom component creation code here


    public static void main(String[] args) {
        String lookAndFeel="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        }catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                        + lookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            }

            catch (UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("
                        + lookAndFeel
                        + ") on this platform.");
                System.err.println("Using the default look and feel.");
            }

            catch (Exception e) {
                System.err.println("Couldn't get specified look and feel ("
                        + lookAndFeel
                        + "), for some reason.");
                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }

        MainForm mainForm = new MainForm();
        frame = new JFrame("MainForm");
        frame.setContentPane(mainForm.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CoreComponent coreComponent = new CoreComponent(new Core(mainForm));
        mainForm.updateTable(null);
        frame.add(coreComponent);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);



    }


    public static JFrame getFrame() {
        return frame;
    }

    public void addPropertiesToTable(){
        String[] header = new String[] {"Property", "Value"};
        ArrayList<Object[]> selectedPropertiesValues=new ArrayList<>();

        Object[] row ;

        for(AssemblyProperty property:core.assemblies.get(0).getProperties()) {
            row=new Object[2];
            row[0]= property.getName();
            for (Assembly assembly : core.selection) {
                if (row[1]==null) {

                    row[1] =assembly.getPropertyValueByName(property.getName());
                    continue;
                }

                switch (row[1].getClass().getSimpleName()) {
                    case "Integer":

                        if (Math.abs((Integer) row[1] - (Integer) assembly.getPropertyValueByName(property.getName())) > 0.00001)
                            row[1] = "<Different values>";
                        break;
                    case "Double":
                        if (Math.abs((Double) row[1] - (Double) assembly.getPropertyValueByName(property.getName())) > 0.00001)
                            row[1] = "<Different values>";
                        break;
                }


            }

            selectedPropertiesValues.add(row);
        }
        int propertiesCount=selectedPropertiesValues.size();
        Object[][] rowData = selectedPropertiesValues.toArray(new Object[propertiesCount][2]);
        updateTable(rowData);
    }
    public void editCurrentPropertyValueCell(){
        int row=coreComponent.getDrawingFieldIndex();
        int col=1;
        if (row!=-1){
            //tableProperties.requestFocus();
            tableProperties.editCellAt(row,col);
            //tableProperties.changeSelection(row, col, true, true);
        }
    }
    private void updateTable(Object[][] data){
        tableProperties.setModel(new PropertyTableModel(data));
        tableProperties.setGridColor(Color.LIGHT_GRAY);
        tableProperties.setShowHorizontalLines(true);
        tableProperties.setShowVerticalLines(true);
        TableColumn column= tableProperties.getColumnModel().getColumn(0);
       
        column.setPreferredWidth(60);
        column.setMaxWidth(60);
        column.setMinWidth(60);
        column= tableProperties.getColumnModel().getColumn(1);
        column.setCellEditor(new ValueCellEditor());
        column= tableProperties.getColumnModel().getColumn(2);
        column.setPreferredWidth(40);
        column.setMaxWidth(40);
        column.setMinWidth(40);
        column.setCellRenderer(new PropertyTableRenderer());
        column.setCellEditor(new PropertyTableActionCellEditor());

    }
    public void applyProperties(){
        Vector<Vector> tableValues=((DefaultTableModel)tableProperties.getModel()).getDataVector();
        Vector[] properties=tableValues.toArray(new Vector[tableValues.size()]);
        for (Assembly assembly:core.selection){
            int propertyIndex=-1;
            //assembly.getProperties().clear();
            for(Vector obj:properties){
                String value= String.valueOf(obj.get(1));
                propertyIndex++;
                if (value.equals("<Different values>")) continue;

                Object newValue = Double.parseDouble(value);
                obj.remove(1);
                obj.add(1,newValue);
                AssemblyProperty property = new AssemblyProperty(null,null);
                if(obj.get(0).equals("Group"))
                    property=new AssemblyGroup("Group",(double) 1);
                property.setFieldsFromRow(obj.toArray(new Object[2]));
                assembly.getProperties().remove(propertyIndex);
                assembly.getProperties().add(propertyIndex,property);
            }
        }
        addPropertiesToTable();
        core.repaintCore();
    }
    public Object getPropertiesTableValueAt(int row, int column){
        return tableProperties.getValueAt(row,column);
    }
}
