package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.json.*;

import javax.sound.sampled.ReverbType;
import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        /* take in a string from the user, and convert into JSONObject */
        JSONObject json;
        Vxieng vxieng;
        String inputJSON;
        Scanner scanner = new Scanner(System.in);
        System.out.println("YOU MUST CHANGE ALL \" \\n\"(whitespace + newline char) char to @");
        System.out.println("Enter the json : ");
        inputJSON = scanner.nextLine();

        try {

            json = new JSONObject(inputJSON);
            JSONArray jsonArray = json.getJSONArray("pageTables"); // this will store each pages
            vxieng = new Vxieng();

            for (int i = 0; i < jsonArray.length() ; i++ ) {
                System.out.println("Working on " + i + "th jsonArray===================================================================");
                FormatBlock((JSONObject) jsonArray.get(i), vxieng);
            }
            vxieng.DisplayAll();
            //System.out.println(vxieng.ReceiverList.size());
        } catch (JSONException error) {
            System.out.println("JSON Parsing error in Main() : " + error.toString());
        }
        /* ============================================================ */

        
    }

    private static void FormatBlock(JSONObject json, Vxieng vxieng) {
        JSONArray jsonArray = json.getJSONArray("tables");  // cut off one layer

        if (isJSONArrayEmpty(jsonArray.getJSONArray(1)) && isJSONArrayEmpty(jsonArray.getJSONArray(1))) {
            // if arrays 2nd and 3rd are empty, then this is where shit gets weird
            FormatWeird(jsonArray.getJSONArray(0).toString(), vxieng);
            return;
        }


        String frameDesignation = "";
        Receiver temp = new Receiver();
        for (int i = 0 ; i < jsonArray.length() ; i++) {
            JSONArray lala = jsonArray.getJSONArray(i);  // lala is an array.


            System.out.println("lala[" + i + "] = " + lala.toString());
            ArrayList<String> strList = new ArrayList<>(Arrays.asList(lala.toString().split(",")));
            for (int j = 0 ; j < strList.size() ; j++){
                System.out.println("strlist.get(" +j +") : " + strList.get(j));
            }
            if (isJSONArrayEmpty(lala) == true) {
                System.out.println("  == Empty!");
            }
            else {
                if (strList.get(1).contains("Frame Designation") && i ==0 ) {
                    frameDesignation = strList.get(3);
                }
                else if (strList.get(3).length() >2 && !strList.get(3).contains("Partition ID")) {  // when there is actually a thing to parse. for some reason, "" has length of 2...?
                    String dataSetDesig = strList.get(1);
                    String ACParam = strList.get(2);
                    String startBitPos = strList.get(4);
                    String dataType = strList.get(7);
                    String EEC = strList.get(8);
                    String min = strList.get(9);
                    String max = strList.get(10);
                    String unit = strList.get(14);
                    temp.addRow(dataSetDesig,ACParam,startBitPos,dataType,EEC,min,max,unit);
                    System.out.println("row list : " + temp.getRowList().toString());
                }
                System.out.println("");
            }
        }
        System.out.println("");
        temp.setFrameDesignation(frameDesignation);
        vxieng.ReceiverList.add(temp);
        System.out.println("****************************One Iteration of Format Block Done************************************");
        return;
    }

    private static void FormatWeird(String str, Vxieng vxieng){
        List<String> masterStrList = new ArrayList<>(Arrays.asList(str.split("@")));
        masterStrList.add("ENDLALALA"); // buffer
        List<String> firstStrList = new ArrayList<>();
        String FrameDesignations = "";
        // werid format will always have 2 charts in one page.
        int flag = 0;
        int strListFlag = 0;  // when set to zero, add onto the firstStrFlag, when set to one, add onto secondStrFlag
        for (int i = 0 ; i < masterStrList.size() ; i ++) {
            if (i > 0 ){
                if (masterStrList.get(i-1).contains("Transmittal Interval")) {
                    flag = 1;
                }
            }
            if (masterStrList.get(i).contains("Frame Designation") || masterStrList.get(i).contains("ENDLALALA") ){
                FrameDesignations = FrameDesignations + masterStrList.get(i) + " @ ";
                flag = 0;
            }
            if (flag == 1) {
                firstStrList.add(masterStrList.get(i).replace("\\n", "") + "\n");
            }
           //System.out.println("masterstrList at " + i + " : " +masterStrList.get(i));
        }
        FrameDesignations = FrameDesignations.replaceAll(" @ ", " ").replaceAll("Frame Designation", "").replaceAll("ENDLALALA", "");


        List<String> FrameDesigList = new ArrayList<>(Arrays.asList(FrameDesignations.split(" ")));
        FrameDesigList.remove(0);
        System.out.println("----------------------------------------------------------------------------");
/*
        for (int i = 0 ; i < firstStrList.size() ; i ++) {
            System.out.print("FirstStrList at " + i + " : " + firstStrList.get(i));
        }
*/
        String st = firstStrList.toString();
       // System.out.println(firstStrList.toString());
        if (st.charAt(1) != 'D' && st.charAt(2) != 's') {
            st = "Ds-NA" + st;
        }  // some fuckery has no Dataset designation. this will prevent system from breaking. fucking ass.

        List<String> secondStrList = new ArrayList<>(Arrays.asList(st.replaceAll("\n", "").split("Ds")));
        secondStrList.remove(0);  // first element is a dummy data


        Receiver temp1 = new Receiver(FrameDesigList.get(0));  // making receiver varaiable with receiver names in it
        Receiver temp2 = new Receiver(FrameDesigList.get(2));

        //System.out.println("st is" + st);
        //System.out.println("SecondStrList as string : " + secondStrList.toString());
        for (int i = 0 ; i< secondStrList.size()-1;i++) {
            System.out.println("SecondStrList at i : " + i + secondStrList.get(i));
        }

        secondStrList.set(secondStrList.size()-1, secondStrList.get(secondStrList.size()-1).substring(0, (secondStrList.get(secondStrList.size()-1)).lastIndexOf(" ")));
       // removing weird "","","", stuff so it looks clean.

        int flag2 = 0;
        for (int i = 0 ; i < secondStrList.size() ; i++) {
            String tester = secondStrList.get(i).substring(secondStrList.get(i).lastIndexOf(" ")+1);
            List<String> breakdownList = new ArrayList<>(Arrays.asList(secondStrList.get(i).split(",")));
            breakdownList.set(0,breakdownList.get(0).replaceAll(" Left ", "_L_").replaceAll(" Right ", "_R_"));
            if (breakdownList.get(breakdownList.size()-1).equals("") || breakdownList.get(breakdownList.size()-1).equals(" ") ) {
                breakdownList.remove(breakdownList.size()-1);  // last element is a dummy. remove it to make it look better
            }

            System.out.println("i : " + i);


            if (flag2 == 0) {
                System.out.println("Put it in First");
                String strr ="";

                for (int j = 0 ; j<breakdownList.size(); j++) {
                    strr += (breakdownList.get(j));
                }
                System.out.println(strr);
                String datasetDesig = "Ds" + strr.substring(0, strr.indexOf(" "));
                strr = strr.substring(datasetDesig.length()-1, strr.length()-1);
                String ACParam;
                ArrayList<String> ParamStuff = new ArrayList<>(Arrays.asList(strr.split(" ")));

                for (int l = 0 ; l < ParamStuff.size()-1 ; l++){
                    System.out.print( " |i : " + l + " = " + ParamStuff.get(l));
                }
                System.out.println("");
                ACParam = ParamStuff.get(0);
                int k = 1;
                while (!ParamStuff.get(k).matches("[0-9]+") && (!ParamStuff.get(k).equals("37")||!ParamStuff.get(k).equals("36")||!ParamStuff.get(k).equals("35")||!ParamStuff.get(k).equals("34")||!ParamStuff.get(k).equals("33"))) {
                    ACParam += ParamStuff.get(k);
                    k++;
                }
                if (ParamStuff.get(k).length() != 2) {
                    ACParam += ParamStuff.get(k);
                    k++;
                }
                k++;
                String startBitPos = ParamStuff.get(k++);
                String DataType = ParamStuff.get(k+2); k+=3;
                String EEC = ParamStuff.get(k++);
                String MN = ParamStuff.get(k++);
                String MX = ParamStuff.get(k++);
                k+=3;
                String Unit = ParamStuff.get(k);
                if (!ParamStuff.get(k+1).replaceAll(" ","").matches("[0-9]+")){ // if next to unit is not numerica, then it must be that goddamn cut off unit.
                    Unit += ParamStuff.get(k+1);
                }
                System.out.println("Parsing result : " + datasetDesig + " | AC param : " + ACParam + " | stat bit:" + startBitPos + " | datatype : " + DataType + " | EEC : " + EEC +
                        " | Min : " + MN + " | Max : " + MX + " | Units : " + Unit);

                temp1.addRow(datasetDesig,ACParam,startBitPos,DataType,EEC,MN,MX,Unit);
                vxieng.ReceiverList.add(temp1);

            }
            else if (flag2 == 1){
                System.out.println("Put it in Second");
                String strr ="";

                for (int j = 0 ; j<breakdownList.size(); j++) {
                    strr += (breakdownList.get(j));
                }
                System.out.println(strr);
                String datasetDesig = "Ds" + strr.substring(0, strr.indexOf(" "));
                strr = strr.substring(datasetDesig.length()-1, strr.length()-1);
                String ACParam;
                ArrayList<String> ParamStuff = new ArrayList<>(Arrays.asList(strr.split(" ")));

                for (int l = 0 ; l < ParamStuff.size()-1 ; l++){
                    System.out.print( " |i : " + l + " = " + ParamStuff.get(l));
                }
                System.out.println("");
                ACParam = ParamStuff.get(0);
                int k = 1;
                while (!ParamStuff.get(k).matches("[0-9]+") && (!ParamStuff.get(k).equals("37")||!ParamStuff.get(k).equals("36")||!ParamStuff.get(k).equals("35")||!ParamStuff.get(k).equals("34")||!ParamStuff.get(k).equals("33"))) {
                    ACParam += ParamStuff.get(k);
                    k++;
                }
                if (ParamStuff.get(k).length() != 2) {
                    ACParam += ParamStuff.get(k);
                    k++;
                }

                k++;
                String startBitPos = ParamStuff.get(k++);
                String DataType = ParamStuff.get(k+2); k+=3;
                String EEC = ParamStuff.get(k++);
                String MN = ParamStuff.get(k++);
                String MX = ParamStuff.get(k++);
                k+=3;
                String Unit = ParamStuff.get(k);
                if (!ParamStuff.get(k+1).replaceAll(" ","").matches("[0-9]+")){ // if next to unit is not numerica, then it must be that goddamn cut off unit.
                    Unit += ParamStuff.get(k+1);
                }
                System.out.println("Parsing result : " + datasetDesig + " | AC param : " + ACParam + " | stat bit:" + startBitPos + " | datatype : " + DataType + " | EEC : " + EEC +
                        " | Min : " + MN + " | Max : " + MX + " | Units : " + Unit);
                temp2.addRow(datasetDesig,ACParam,startBitPos,DataType,EEC,MN,MX,Unit);
                vxieng.ReceiverList.add(temp2);
            }


            for (int j = 0 ; j < breakdownList.size();j++){
                if (breakdownList.get(j).contains(FrameDesigList.get(2))) {
                    flag2 = 1;
                }
                //System.out.println("   j : "+ j + breakdownList.get(j));
            }
        }
        System.out.println("");


    }

    private static boolean isJSONArrayEmpty (JSONArray arr) {
        for (int i = 0 ; i < arr.length() ; i++) {
            if (!arr.get(i).toString().equals("")) {
                return false;
            }
        }
        return true;
    }
}