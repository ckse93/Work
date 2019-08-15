package com.company;

import java.util.ArrayList;
import java.util.List;

public class Vxieng {
    public List<Receiver> ReceiverList = new ArrayList<>();

    public void DispSingleElemAt(Integer i) {
        Receiver temp = ReceiverList.get(i);
        System.out.println(temp.getFrameDesignation());  //  displaying frame designation.
        for (int j = 0 ; j < temp.rowList.size() ; j++) {
            System.out.println(temp.rowList.get(j).elements());
        }
    }
}
