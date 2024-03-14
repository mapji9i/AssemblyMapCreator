package coreCreator;


public class AssemblyProperty {

    private String name;
    private Object value;

    public AssemblyProperty(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public AssemblyProperty(Object[] nameAndValue){
        setFieldsFromRow(nameAndValue);
    }

    public String getName() {
        return  this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object[] getTableRow(){
        return new Object[] {this.name, this.value};
    }

    public void setFieldsFromRow(Object[] row){
        if(!row[1].equals("<Different values>")) {
            this.name = (String) row[0];
            this.value =row[1];
        }
    }



}
