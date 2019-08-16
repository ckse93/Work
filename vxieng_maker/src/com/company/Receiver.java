package com.company;

import org.json.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Receiver {
    private String frameDesignation;
    public List<Dictionary> rowList = new ArrayList<>();

    public Receiver(String str){  // initializer with a string input.
        frameDesignation = str;
    }

    public void addRow(String datasetDesig, String AC_Param, String startBit, String dataType, String EEC, String MIN,
                       String MAX, String unit){
        Dictionary temp = new Hashtable();
        temp.put("Dataset Designation", datasetDesig);
        temp.put("AC Parameter", AC_Param);
        temp.put("Start Bit Position", startBit);
        temp.put("Data Type", dataType);
        temp.put("EEC Mnemonic", EEC);
        temp.put("Range Min", MIN);
        temp.put("Range Max", MAX);
        temp.put("Units",unit);
        rowList.add(temp);
        System.out.println("addRow Done");
    }

    public List<Dictionary> getRowList() {
        return rowList;
    }

    public Dictionary getDictionaryAt(int i) {
        return rowList.get(i);
    }

    public String getFrameDesignation(){
        return frameDesignation;
    }
}
