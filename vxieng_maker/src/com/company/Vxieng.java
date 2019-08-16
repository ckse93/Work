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

    public void DisplayAll() {
        MergeDuplicates();
        if (ReceiverList.isEmpty()){
            System.out.println("ReceiverList is empty");
        }
        for (int i = 0 ; i < ReceiverList.size() ; i++) {
            Receiver temp = ReceiverList.get(i);
            System.out.println("Frame Designation : " + temp.getFrameDesignation() + "at : " + i);
            for (int j = 0 ; j < temp.rowList.size() ; j++) {
                System.out.print("    Dataset Designation : " + temp.rowList.get(j).get("Dataset Designation") + " | ");
                System.out.print("AC Parameter : " + temp.rowList.get(j).get("AC Parameter") + " | ");
                System.out.print("Start Bit Position : " + temp.rowList.get(j).get("Start Bit Position") + " | ");
                System.out.print("Data Type : " + temp.rowList.get(j).get("Data Type") + " | ");
                System.out.print("EEC Mnemonic: " + temp.rowList.get(j).get("EEC Mnemonic") + " | ");
                System.out.print("Range Min : " + temp.rowList.get(j).get("Range Min") + " | ");
                System.out.print("Range Max : " + temp.rowList.get(j).get("Range Max") + " | ");
                System.out.print("Units : " + temp.rowList.get(j).get("Units") + "\n");
            }
        }
    }

    private void MergeDuplicates() {
        for (int i = 1 ; i < ReceiverList.size();i++){
            if (isReceiverEqual(ReceiverList.get(i-1), ReceiverList.get(i))){
                ReceiverList.remove(i);
                i--;
                System.out.println("removed");
            }
        }
    }

    private boolean isReceiverEqual(Receiver A, Receiver B){
        if (A.getFrameDesignation().equals(B.getFrameDesignation())){
            if (A.rowList.toString().equals(B.rowList.toString())){
                return true;
            }
        }
        return false;
    }
}
