/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.DBpediaExtraction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * text separation and save them using UTF-8 without BOM encoding.
 * @author hong.dong
 */
public class txtSeparation {
    public static void main(String args []){
        txtSeparation ts = new txtSeparation();
        ts.txtSepUTFwoBOM("C:\\Users\\hong.dong\\Documents\\NetBeansProjects\\DBpediaHierarchyExtraction\\", "article_categories_en.ttl", 4);
    }
    
    // number separation, from 50 to 13,13,12,12.
    public int[] numSep(int sum, int num){
        int[] a = new int[num];
        int each = sum/num;
        int mod = sum % num;
        for (int i=0;i<num;i++){
            if (mod > i){
                a[i] = each + 1;
            }else{            
                a[i] = each;
            }
        }
        return a;
    }
    
    
    //@param num the number of separation
    //encoding as UTF-8 without BOM
    //in each separated text file, the first line will be "#separated file N",
    //where N is the current no. of the text file.
    public void txtSepUTFwoBOM(String path, String inputFileName, int num){
        File file = new File(path + inputFileName);
        
        //for (int i=1;i<=num;i++){
        //    File fileW = new File(path + "[" + i + "]" + inputFileName);            
        //}
        
        BufferedReader reader = null; // FMTF stands for fileMultiword_Tags_and_Forms.
        //BufferedWriter writer = null;
        
        OutputStreamWriter osw = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            //writer = new BufferedWriter(new FileWriter(fileW));
            
            String tempString = null;
            
            // get the line number
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                line++;
            }
            
            //reader.close();
            
            //line = line - 1;
            //int line_each = line/num; // 5/2 = 2
            //System.out.println(line_each);
            //int last_file_line = line % num;
            
            //if (last_file_line != 0){
            //    num = num + 1;
            //}
            
            System.out.println(line-1);
            int [] arr_numSep = this.numSep(line - 1, num); // arr_line has length num.
            
            int [] arr_line = new int[num];
            for (int j=0;j<arr_numSep.length;j++){
                //System.out.println(arr_numSep[j]);
                arr_line[j] = 0;
                for (int k=0;k<=j;k++){
                    arr_line[j] = arr_line[j] + arr_numSep[k];
                }
            }
            
            
            for (int i=1;i<=num;i++){
                //File fileW = new File(path + "[part" + i + "]" + inputFileName);
                //BufferedWriter writer = null;
                //writer = new BufferedWriter(new FileWriter(fileW));
                //path = path.concat("[part" + i + "]" + inputFileName);
                
                FileOutputStream fos = new FileOutputStream(path.concat("[part" + i + "]" + inputFileName),false);
                osw = new OutputStreamWriter(fos,"UTF-8");
                osw.write("# separated file " + i);
                osw.write("\uFEFF\n");
            
                reader = new BufferedReader(new FileReader(file));
                line = 1;
                while ((tempString = reader.readLine()) != null) {
                    //if ( (line >= line_each*(i-1) + 1) && line <= line_each*i){
                    if ( (line >= arr_line[i-1] - arr_numSep[i-1] + 1) && line <= arr_line[i-1]){
                        osw.write(tempString + "\n");
                    }
                    line++;
                }
                osw.close();
                System.out.println("[part" + i + "] Output Success.");
                fos.close();
            }
            
            //writer.flush();
            //writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            //osw.close();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    
    //@param num the number of separation
    public void txtSep(String path, String inputFileName, int num){
        File file = new File(path + inputFileName);
        
        //for (int i=1;i<=num;i++){
        //    File fileW = new File(path + "[" + i + "]" + inputFileName);            
        //}
        
        BufferedReader reader = null; // FMTF stands for fileMultiword_Tags_and_Forms.
        //BufferedWriter writer = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            //writer = new BufferedWriter(new FileWriter(fileW));
            
            String tempString = null;
            
            // get the line number
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                line++;
            }
            
            reader.close();
            
            //line = line - 1;
            //int line_each = line/num; // 5/2 = 2
            //System.out.println(line_each);
            //int last_file_line = line % num;
            
            //if (last_file_line != 0){
            //    num = num + 1;
            //}
            
            int [] arr_numSep = this.numSep(line - 1, num); // arr_line has length num.
            
            int [] arr_line = new int[num];
            for (int j=0;j<arr_numSep.length;j++){
                //System.out.println(arr_numSep[j]);
                arr_line[j] = 0;
                for (int k=0;k<=j;k++){
                    arr_line[j] = arr_line[j] + arr_numSep[k];
                }
            }
            
            
            for (int i=1;i<=num;i++){
                File fileW = new File(path + "[part" + i + "]" + inputFileName);
                BufferedWriter writer = null;
                writer = new BufferedWriter(new FileWriter(fileW));
                
                reader = new BufferedReader(new FileReader(file));
                line = 1;
                while ((tempString = reader.readLine()) != null) {
                    //if ( (line >= line_each*(i-1) + 1) && line <= line_each*i){
                    if ( (line >= arr_line[i-1] - arr_numSep[i-1] + 1) && line <= arr_line[i-1]){
                        writer.write(tempString);
                        writer.newLine();
                    }
                    line++;
                }
                writer.flush();
                writer.close();
            }
            
            //writer.flush();
            //writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    
    
}