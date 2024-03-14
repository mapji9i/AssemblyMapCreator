package coreCreator;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventObject;

import static coreCreator.Core.core;
import static coreCreator.CoreComponent.coreComponent;
import static coreCreator.MainForm.getMainForm;

public class PropertyTableModel extends DefaultTableModel {
    private static String [] header = {"Property", "Value","Show"};

    public PropertyTableModel() {
       super(null, header);
    }
    public PropertyTableModel(Object[][] data) {
        super(data, header);

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column==0 && row<4) return  false;
        return true;
    }
}
class PropertyTableRenderer extends JButton implements TableCellRenderer {
    public PropertyTableRenderer() {
         super("...");
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        return this;
    }
}
class PropertyTableActionCellEditor extends DefaultCellEditor {
    protected JButton button;
    private boolean clicked;
    private int rowIndex;
    public PropertyTableActionCellEditor() {
        super(new JCheckBox());
        button = new JButton("...");
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                        fireEditingStopped();
                                     }

        });
        setClickCountToStart(1);

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {


            clicked=true;
            rowIndex=row;
            return button;

    }

    @Override
    public Object getCellEditorValue() {
        if(clicked){
            if (coreComponent.getDrawingFieldName()==null || !coreComponent.getDrawingFieldName().equals(""+getMainForm().getPropertiesTableValueAt(rowIndex, 0))) {
                coreComponent.setDrawingFieldName("" + getMainForm().getPropertiesTableValueAt(rowIndex, 0));
                coreComponent.setDrawingFieldIndex(rowIndex);
            }else{
                coreComponent.setDrawingFieldName(null);
                coreComponent.setDrawingFieldIndex(-1);
            }
            core.repaintCore();
        }
        return super.getCellEditorValue();
    }

//    @Override
//    public boolean stopCellEditing() {
//        clicked=false;
//        return super.stopCellEditing();
//    }

//    @Override
//    protected void fireEditingStopped() {
//        super.fireEditingStopped();
//    }

}
class ValueCellEditor extends DefaultCellEditor{

    public ValueCellEditor() {
        super(new JTextField());
        setClickCountToStart(0);
        addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                getMainForm().applyProperties();
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });
    }

    @Override
    public Component getComponent() {
        System.out.println(super.getComponent().getClass().getName());
        return super.getComponent();
    }
}
