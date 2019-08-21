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
        MergeDuplicates();   // 1. merge the duplicates
        if (ReceiverList.isEmpty()){
            System.out.println("ReceiverList is empty");
        }
        // 2. merge 2 receivers that have the same frame designations.
        for (int i = 0; i < ReceiverList.size()-1; i++){
            if (ReceiverList.get(i).getFrameDesignation().equals(ReceiverList.get(i+1).getFrameDesignation())){
                MergeReceivers(ReceiverList.get(i), ReceiverList.get(i+1));
                ReceiverList.remove(i+1);
            }
        }

        for (int i = 0 ; i < ReceiverList.size() ; i++) {
            Receiver temp = ReceiverList.get(i);
            System.out.println("Frame Designation : " + temp.getFrameDesignation() + ", at : " + i);
            for (int j = 0 ; j < temp.rowList.size() ; j++) {
                if (temp.rowList.get(j).get("AC Parameter").toString().length() > 2){
                    System.out.print("    Dataset Designation : " + temp.rowList.get(j).get("Dataset Designation").toString() + " | ");
                    System.out.print("AC Parameter : " + temp.rowList.get(j).get("AC Parameter") + " | ");
                    System.out.print("Start Bit Position : " + temp.rowList.get(j).get("Start Bit Position") + " | ");
                    System.out.print("Data Type : " + temp.rowList.get(j).get("Data Type") + " | ");
                    System.out.print("EEC Mnemonic: " + temp.rowList.get(j).get("EEC Mnemonic") + " | ");
                    System.out.print("Range Min : " + temp.rowList.get(j).get("Range Min") + " | ");
                    System.out.print("Range Max : " + temp.rowList.get(j).get("Range Max") + " | ");
                    System.out.print("Units : " + temp.rowList.get(j).get("Units") + "\n");
                    //System.out.println("Dataset desig is : " + temp.rowList.get(j).get("Dataset Designation").toString().toCharArray().toString());
                }
            }
        }
    }

    public void DisplayFormatted() {  // basically like DisplayAll, but this wil return the final vxieng string.
        MergeDuplicates();   // 1. merge the duplicates
        if (ReceiverList.isEmpty()){
            System.out.println("ReceiverList is empty");
        }
        // 2. merge 2 receivers that have the same frame designations.

        for (int i = 0; i < ReceiverList.size()-1; i++){
            if (ReceiverList.get(i).getFrameDesignation().equals(ReceiverList.get(i+1).getFrameDesignation())){
                MergeReceivers(ReceiverList.get(i), ReceiverList.get(i+1));
                ReceiverList.remove(i+1);
            }
        }


        for (int i = 0 ; i < ReceiverList.size() ; i++) {  // each iteration is for one Reveiver entity
            Receiver temp = ReceiverList.get(i);
            System.out.print("\n$RECEIVER "+ temp.getFrameDesignation() + " PORT=UNDECIDED ACTIVE=UNDECIDED\n");
            int bitnum = 0;
            int bitflag = 0;
            temp.addRow("","","","","","","","");  // dummy tail to avoid Null Ptr Exctption
            for (int j = 0 ; j < temp.rowList.size()-1 ; j++) {  // each iteration is for one row.
                if (temp.rowList.get(j).get("Dataset Designation").toString().equals(temp.rowList.get(j+1).get("Dataset Designation").toString())){
                    //if the dataset designation of jth is the same as +1th element.
                    if (bitflag == 0) {
                        System.out.println("$BITS");
                        bitflag = 1;
                    }
                    System.out.print(temp.rowList.get(j).get("EEC Mnemonic").toString() + "_" + temp.rowList.get(j).get("Start Bit Position") + " BITNUM=" + bitnum++ + " 0VAL= 1VAL= LABEL=\n");
                }
                else if (j!=0 && temp.rowList.get(j).get("Dataset Designation").toString().equals(temp.rowList.get(j-1).get("Dataset Designation").toString()) && !temp.rowList.get(j).get("Dataset Designation").toString().equals(temp.rowList.get(j+1).get("Dataset Designation").toString())){
                    /*
                    * case where j is not 0, jth element is same as j-1, but not same as j+1.
                    * this is where there are two bits operation is happening side by side.
                    * */
                    System.out.println(temp.rowList.get(j).get("EEC Mnemonic").toString() + temp.rowList.get(j).get("Start Bit Position") + " BITNUM=" + bitnum++ + " 0VAL= 1VAL= LABEL=");
                    System.out.println("$END-BITS");
                    bitflag = 0;
                }
                else {
                    if (bitflag == 1) {
                        System.out.println(temp.rowList.get(j).get("EEC Mnemonic").toString() + temp.rowList.get(j).get("Start Bit Position") + " BITNUM=" + bitnum++ + " 0VAL= 1VAL= LABEL=");
                        System.out.println("$END-BITS");
                        bitflag = 0;
                    }
                    System.out.print("$FDS "+ temp.rowList.get(j).get("Dataset Designation") + " STATUS=UNDECIDED\n");
                    System.out.print(temp.rowList.get(j).get("AC Parameter") + " TYPE=" + temp.rowList.get(j).get("Data Type") +
                            " SIZE=UNDECIDED ADDRESS=UNDECIDED ");

                    if (!temp.rowList.get(j).get("Range Min").equals("")){
                        System.out.print("SIGNALRANGE=\"" + temp.rowList.get(j).get("Range Min") + " " + temp.rowList.get(j).get("Range Max") + " " + temp.rowList.get(j).get("Units") + "\"\n");
                    }else {
                        System.out.print("\n");
                    }
                    bitnum = 0;
                    System.out.print("$END-FDS\n");
                }
            }

            if (bitflag == 1) {
                System.out.println(temp.rowList.get(temp.rowList.size()-1).get("EEC Mnemonic").toString() + "_" + temp.rowList.get(temp.rowList.size()-1).get("Start Bit Position") + " BITNUM=" + bitnum++ + " 0VAL= 1VAL= LABEL=");
                System.out.println("$END-BITS");
            }
            System.out.print("$END-RECEIVER\n*\n*");
        }
    }


    public void DisplayFormatted_backup() {  // basically like DisplayAll, but this wil return the final vxieng string.
        MergeDuplicates();   // 1. merge the duplicates
        if (ReceiverList.isEmpty()){
            System.out.println("ReceiverList is empty");
        }
        // 2. merge 2 receivers that have the same frame designations.

        for (int i = 0; i < ReceiverList.size()-1; i++){
            if (ReceiverList.get(i).getFrameDesignation().equals(ReceiverList.get(i+1).getFrameDesignation())){
                MergeReceivers(ReceiverList.get(i), ReceiverList.get(i+1));
                ReceiverList.remove(i+1);
            }
        }

        for (int i = 0 ; i < ReceiverList.size() ; i++) {  // each iteration is for one Reveiver entity
            Receiver temp = ReceiverList.get(i);
            System.out.print("\n$RECEIVER "+ temp.getFrameDesignation() + " PORT=UNDECIDED ACTIVE=UNDECIDED\n");
            int bitnum = 0;
            int bitflag = 0;
            temp.addRow("","","","","","","","");  // dummy tail
            for (int j = 0 ; j < temp.rowList.size()-1 ; j++) {  // each iteration is for one row.
                if (temp.rowList.get(j).get("Dataset Designation").toString().equals(temp.rowList.get(j+1).get("Dataset Designation").toString())){
                    if (bitflag == 0) {
                        System.out.println("$BITS");
                        bitflag = 1;
                    }
                    System.out.print(temp.rowList.get(j).get("EEC Mnemonic").toString() + "_" + temp.rowList.get(j).get("Start Bit Position") + " BITNUM=" + bitnum++ + " 0VAL= 1VAL= LABEL=\n");
                }
                else {
                    if (bitflag == 1) {
                        System.out.println(temp.rowList.get(j).get("EEC Mnemonic").toString() + temp.rowList.get(j).get("Start Bit Position") + " BITNUM=" + bitnum++ + " 0VAL= 1VAL= LABEL=");
                        System.out.println("$END-BITS");
                        bitflag = 0;
                    }
                    System.out.print("$FDS "+ temp.rowList.get(j).get("Dataset Designation") + " STATUS=UNDECIDED\n");
                    System.out.print(temp.rowList.get(j).get("AC Parameter") + " TYPE=" + temp.rowList.get(j).get("Data Type") +
                            " SIZE=UNDECIDED ADDRESS=UNDECIDED ");

                    if (!temp.rowList.get(j).get("Range Min").equals("")){
                        System.out.print("SIGNALRANGE=\"" + temp.rowList.get(j).get("Range Min") + " " + temp.rowList.get(j).get("Range Max") + " " + temp.rowList.get(j).get("Units") + "\"\n");
                    }else {
                        System.out.print("\n");
                    }
                    bitnum = 0;
                    System.out.print("$END-FDS\n");
                }
            }
            if (bitflag == 1) {
                System.out.println(temp.rowList.get(temp.rowList.size()-1).get("EEC Mnemonic").toString() + "_" + temp.rowList.get(temp.rowList.size()-1).get("Start Bit Position") + " BITNUM=" + bitnum++ + " 0VAL= 1VAL= LABEL=");
                System.out.println("$END-BITS");
            }
            System.out.print("$END-RECEIVER\n*\n*");
        }
    }

    private void MergeDuplicates() {
        for (int i = 1 ; i < ReceiverList.size();i++){
            if (isReceiverEqual(ReceiverList.get(i-1), ReceiverList.get(i))){
                ReceiverList.remove(i);
                i--;
              //  System.out.println("removed");
            }
        }
    }

    private void MergeReceivers(Receiver A, Receiver B) {
        for (int i = 0 ; i  < B.rowList.size() ; i++){
            A.rowList.add(B.rowList.get(i));
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
