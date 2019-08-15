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
                FormatBlock((JSONObject) jsonArray.get(i), vxieng);
            }
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


        for (int i = 0 ; i < jsonArray.length() ; i++) {
            JSONArray lala = jsonArray.getJSONArray(i);  // lala is an array.
            System.out.print("lala[" + i + "] = " + lala.toString());
            if (isJSONArrayEmpty(lala) == true) {
                System.out.println("  == Empty!");
            }
            else {
                System.out.println("");
            }
        }
        System.out.println("");

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

           // System.out.println("masterstrList at " + i + " : " +masterStrList.get(i));
        }
        FrameDesignations = FrameDesignations.replaceAll(" @ ", " ").replaceAll("Frame Designation", "").replaceAll("ENDLALALA", "");


        List<String> FrameDesigList = new ArrayList<>(Arrays.asList(FrameDesignations.split(" ")));
        FrameDesigList.remove(0);
        System.out.println("----------------------------------------------------------------------------");
/*
        for (int i = 0 ; i < firstStrList.size() ; i ++) {
            System.out.print(i + " : " + firstStrList.get(i));
        }
*/
        String st = firstStrList.toString();
        List<String> secondStrList = new ArrayList<>(Arrays.asList(st.replaceAll("\n", "").split("Ds")));
        secondStrList.remove(0);  // first element is a dummy data


        Receiver temp1 = new Receiver(FrameDesigList.get(0));
        Receiver temp2 = new Receiver(FrameDesigList.get(2));


        /*
        StringBuilder sb1 =

        String tempDesignation1 = secondStrList.get(0).substring(0, secondStrList.get(0).indexOf(" "));
        String tempDesignation2 = secondStrList.get(1).substring(0, secondStrList.get(1).indexOf(" "));

        temp1.addRow("Ds"+tempDesignation1,"ACPARAM", "STARTBIT", "TYPE","EEC","MIN","MAX","UNITS");
        temp2.addRow("Ds"+tempDesignation2,"ACPARAM", "STARTBIT", "TYPE","EEC","MIN","MAX","UNITS");
        System.out.println( "Receiver's stored Frame designation : " + temp1.getFrameDesignation() + " and " + temp2.getFrameDesignation());
        System.out.println(temp1.rowList.get(0)+ "   |   " + temp2.rowList.get(0));
*/
        secondStrList.set(secondStrList.size()-1, secondStrList.get(secondStrList.size()-1).substring(0, (secondStrList.get(secondStrList.size()-1)).lastIndexOf(" ")));
        int flag2 = 0;
        for (int i = 0 ; i < secondStrList.size() ; i++) {
            String tester = secondStrList.get(i).substring(secondStrList.get(i).lastIndexOf(" ")+1);
            if (tester.equals(FrameDesigList.get(2))) {  // if this is same as the second Receiver's frame designation

            }


            System.out.println("second i : " + i + " = " + secondStrList.get(i));

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