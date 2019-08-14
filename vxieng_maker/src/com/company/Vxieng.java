package com.company;

import java.util.ArrayList;
import java.util.List;

public class Vxieng {
    public List<Receiver> ReceiverList = new ArrayList<>();

    public void DispSingleElemAt(Integer i) {
        System.out.println("DispSingleElem at " + i.toString() + "th : ");
        System.out.println(ReceiverList.get(i).getDatasetDesig());
        System.out.println(ReceiverList.get(i).getAC_Param());
        System.out.println(ReceiverList.get(i).getAC_Param());
        System.out.println(ReceiverList.get(i).getAC_Param());
    }

    public void Organize() {
        Integer listSize = (Integer)ReceiverList.size();
        System.out.println("Begin Organizing \n");
        for (Integer i = 0 ; i < ReceiverList.size()-1 ; i ++){
            System.out.println("iteration : " + i.toString() + " | " + "List length : " + listSize.toString());
            // debugging statement

         if (ReceiverList.get(i).getDatasetDesig() == ReceiverList.get(i+1).getDatasetDesig()) {

         }
         //compare two Reveiver's DatasetDesignation. If they are same, then merge.


        }
    }
}
