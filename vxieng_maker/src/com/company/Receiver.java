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

    public Receiver(){}

    public void addRow(String datasetDesig, String AC_Param, String startBit, String dataType, String EEC, String MIN,
                       String MAX, String unit){
        Dictionary temp = new Hashtable();
        temp.put("Dataset Designation", datasetDesig.toString().replaceAll("\\\\n","").replaceAll(" ","").replaceAll("NA\\[","NA"));
        temp.put("AC Parameter", AC_Param.replaceAll("\\\\n","").replaceAll(" ",""));
        temp.put("Start Bit Position", startBit.replaceAll("\\\\n","").replaceAll(" ",""));
        temp.put("Data Type", dataType.replaceAll("\\\\n","").replaceAll(" ",""));
        temp.put("EEC Mnemonic", EEC.replaceAll("\\\\n","").replaceAll(" ",""));
        temp.put("Range Min", MIN.replaceAll("\\\\n","").replaceAll(" ",""));
        temp.put("Range Max", MAX.replaceAll("\\\\n","").replaceAll(" ",""));
        temp.put("Units",unit.replaceAll("\\\\n","").replaceAll(" ","").replaceAll("percent","%"));
        rowList.add(temp);
        //System.out.println("addRow Done");
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

    public void setFrameDesignation (String str){
        frameDesignation = str.replaceAll("\n","").replaceAll(" ","");
    }
}
