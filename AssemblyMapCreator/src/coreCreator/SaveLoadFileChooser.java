package coreCreator;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SaveLoadFileChooser extends JFileChooser {
    private static File currentDirectory=new File("~");

    public static File getCurrentFile() {
        return currentFile;
    }

    private static File currentFile=null;

    SaveLoadFileChooser(){
        super();
        super.setSelectedFile(currentFile);
        super.setCurrentDirectory(currentDirectory);
    }

    @Override
    public File getSelectedFile() {
        File result = super.getSelectedFile();
        currentFile=result;
        if(result!=null)
            currentDirectory=result.getParentFile();
        return result;
    }
    public File getFileToLoad(){
        return super.getSelectedFile();
    }


}
