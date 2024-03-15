package coreCreator;


import java.util.HashMap;

public class AssemblyProperty {
    private static final HashMap maxValues = new HashMap<String, Double>();
    private static final HashMap minValues = new HashMap<String, Double>();
    private String name;
    private Object value;

    public AssemblyProperty(String name, Object value) {
        this.name = name;
        this.value = value;
        updateMinMAxValues();
    }

    public AssemblyProperty(Object[] nameAndValue) {
        setFieldsFromRow(nameAndValue);
    }

    public String getName() {
        return  this.name;
    }

    public void setName(String name) {
        this.name = name;
        updateMinMAxValues();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        updateMinMAxValues();
    }

    public Object[] getTableRow() {
        return new Object[]{this.name, this.value};
    }

    public void setFieldsFromRow(Object[] row) {
        if (!row[1].equals("<Different values>")) {
            this.name = (String) row[0];
            this.value = row[1];
            updateMinMAxValues();
        }
    }

    private void updateMinMAxValues() {
        if (minValues.containsKey(this.name) && maxValues.containsKey(this.name)) {
            double maxValue = (double) maxValues.get(this.name);
            double minValue = (double) minValues.get(this.name);
            if ((Double) this.value > maxValue) maxValues.replace(this.name, this.value);
            if ((Double) this.value < minValue) minValues.replace(this.name, this.value);
        } else {
            maxValues.put(this.name, this.value);
            minValues.put(this.name, this.value);
        }

    }

    public static double[] getMinMaxValue(String fieldName) {
        double[] result = new double[2];
        result[0] = (double) minValues.get(fieldName);
        result[1] = (double) maxValues.get(fieldName);
        return result;
    }


}
