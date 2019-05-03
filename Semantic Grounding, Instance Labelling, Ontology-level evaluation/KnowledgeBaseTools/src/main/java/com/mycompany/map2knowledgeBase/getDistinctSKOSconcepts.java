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
public class getDistinctSKOSconcepts {
    public static void main(String [] args){
        String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction";
        
        File file = new File(path + "\\" + "all_skos_data_dbpedia.txt");
        File fileW = new File(path + "\\" + "distinct_dbpedia_skos_concept.txt");
        BufferedReader readertags = null;
        BufferedWriter writer = null;
        String tempString;
        String tag;
        HashMap <String,Integer> hm = new HashMap<String,Integer>();
        boolean contain;
        try {
            readertags = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(fileW));
            
            int line = 0;
            
            while ((tempString = readertags.readLine()) != null) {
                String [] concepts = tempString.split(" < ");
                if (concepts.length == 2){
                    hm.put(concepts[0],1);
                    hm.put(concepts[1],1);
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                line++;
            }
            
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
            if (readertags != null) {
                try {
                    readertags.close();
                } catch (IOException e1) {
                }
            }
        }    
    }
}
