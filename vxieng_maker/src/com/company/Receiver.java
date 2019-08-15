package com.company;

import org.json.*;

public class Receiver {
    private String DatasetDesig = "";
    private String AC_Param = "";
    private String StartBitPos;
    private String DataType;
    private String EEC;
    private String RangeMin;
    private String RangeMax;
    private String Units;


    // setters
    public void setDatasetDesig (String str) {
        DatasetDesig = str.replaceAll("\n", "").replaceAll(" ", "");
    }
    public void setAC_Param(String str) {
        AC_Param = str.replaceAll("\n", "").replaceAll(" ", "");
    }

    // getters
    public String getDatasetDesig() { return DatasetDesig;}
    public String getAC_Param() { return AC_Param;}
    public String getUnits() { return Units;}
    public String getEEC() { return EEC; }
    public String getRangeMax() { return RangeMax; }
    public String getRangeMin() { return RangeMin; }
    public String getDataType() { return DataType; }
    public String getStartBitPos() { return StartBitPos; }


    public void setRangeMin(String rangeMin) {
        RangeMin = rangeMin;
    }

    public void setRangeMax(String rangeMax) {
        RangeMax = rangeMax;
    }


    public void setStartBitPos(String startBitPos) {
        StartBitPos = startBitPos;
    }


    public void setDataType(String dataType) {
        DataType = dataType;
    }


    public void setEEC(String EEC) {
        this.EEC = EEC;
    }

    public void setUnits(String units) {
        Units = units;
    }
}
