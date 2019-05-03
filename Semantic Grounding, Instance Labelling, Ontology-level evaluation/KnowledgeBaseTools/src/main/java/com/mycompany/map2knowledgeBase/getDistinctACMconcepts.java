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

/**
 *
 * @author cleme
 */
public class getDistinctACMconcepts {
    public static void main(String [] args){
        String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction";
        
        File file = new File(path + "\\" + "äcm_preflabel_hierarchy.txt");
        File file_equiv = new File(path + "\\" + "acm_data_equivalence_new.txt");
        File fileW = new File(path + "\\" + "distinct_acm_concept.txt");
        BufferedReader reader = null;
        BufferedReader reader_equiv = null;
        BufferedWriter writer = null;
        String tempString;
        String tag;
        HashMap <String,Integer> hm = new HashMap<String,Integer>();
        boolean contain;
        try {
            reader = new BufferedReader(new FileReader(file));
            reader_equiv = new BufferedReader(new FileReader(file_equiv));
            writer = new BufferedWriter(new FileWriter(fileW));
            
            int line = 0;
            
            while ((tempString = reader.readLine()) != null) {
                String [] concepts = tempString.split(" <- ");
                if (concepts.length == 2){
                    hm.put(concepts[0],1);
                    hm.put(concepts[1],1);
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                line++;
            }
            
            while ((tempString = reader_equiv.readLine()) != null) {
                String [] concepts = tempString.split(" = ");
                if (concepts.length == 2){
                    hm.put(concepts[0],1);
                    hm.put(concepts[1],1);
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                line++;
            }
            reader_equiv.close();
                    
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
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }    
    }
}
