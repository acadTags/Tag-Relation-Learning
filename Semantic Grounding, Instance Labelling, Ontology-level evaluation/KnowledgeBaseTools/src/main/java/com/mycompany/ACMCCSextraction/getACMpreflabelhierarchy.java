/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ACMCCSextraction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author cleme
 */
public class getACMpreflabelhierarchy {
    public static void main(String [] args){
        File file_conceptids = new File("acm_data_concept_ids.txt");
        File file_hierarchy = new File("acm_data_hierarchy.csv");
        File fileW = new File("äcm_preflabel_hierarchy.txt");
        BufferedReader reader_conceptids = null;
        BufferedReader reader_hierarchy = null;
        BufferedWriter writer = null;
        
        try {
            reader_hierarchy = new BufferedReader(new FileReader(file_hierarchy));
            reader_conceptids = new BufferedReader(new FileReader(file_conceptids));
            writer = new BufferedWriter(new FileWriter(fileW));
            
            HashMap<String, String> hm_id_label = new HashMap<String, String>();
            
            String tempString;
            
            int line = 1;
            while ((tempString = reader_conceptids.readLine()) != null) {
                String [] arr = tempString.split(" : ");
                if (arr.length == 2){
                    if (!hm_id_label.containsKey(arr[0])){
                        hm_id_label.put(arr[0],arr[1]);
                    }else{
                        System.out.println("[repeated id]" + tempString);
                    }    
                }else{
                    System.out.println("[wrong format]" + tempString);
                }    
                line++;
            }
            
            while ((tempString = reader_hierarchy.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    if (hm_id_label.containsKey(arr[0]) && hm_id_label.containsKey(arr[1])){
                        writer.write(hm_id_label.get(arr[0]) + " <- " + hm_id_label.get(arr[1]));
                        writer.newLine();
                    }else{
                        System.out.println("[new id in]" + tempString);     
                    }    
                }else{
                    System.out.println("[wrong format]" + tempString);
                }    
            }
            reader_hierarchy.close();
            
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader_conceptids != null) {
                try {
                    reader_conceptids.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
