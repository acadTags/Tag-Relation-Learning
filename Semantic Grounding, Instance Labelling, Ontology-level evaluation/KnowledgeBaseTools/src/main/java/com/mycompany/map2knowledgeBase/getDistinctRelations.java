/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.map2knowledgeBase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** combine and get CS-IS-subKB
 *
 * @author cleme
 */
public class getDistinctRelations {
    public static void main(String [] args){
        String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction";
        
        File file_CS = new File(path + "\\" + "areas of computer science_subKB_10.txt");
        File file_IS = new File(path + "\\" + "information science_subKB_10.txt");
        File fileW = new File(path + "\\" + "CS_IS_subKB_10.txt");
        BufferedReader reader_CS = null;
        BufferedReader reader_IS = null;
        BufferedWriter writer = null;
        String tempString;
        String tag;
        HashMap <String,Integer> hm = new HashMap<String,Integer>();
        boolean contain;
        try {
            reader_CS = new BufferedReader(new FileReader(file_CS));
            reader_IS = new BufferedReader(new FileReader(file_IS));
            writer = new BufferedWriter(new FileWriter(fileW));
            
            while ((tempString = reader_CS.readLine()) != null) {
                hm.put(tempString, 1);
            }
            
            while ((tempString = reader_IS.readLine()) != null) {
                hm.put(tempString, 1);
            }
            reader_IS.close();
                    
            Set<Map.Entry<String, Integer>> set = hm.entrySet();
            for(Map.Entry<String, Integer> en: set){
                writer.write(en.getKey());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader_CS != null) {
                try {
                    reader_CS.close();
                } catch (IOException e1) {
                }
            }
        }    
    }
}
