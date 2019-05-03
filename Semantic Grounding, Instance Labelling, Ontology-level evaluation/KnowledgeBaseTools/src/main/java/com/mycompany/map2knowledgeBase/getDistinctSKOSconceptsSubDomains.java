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

/** get distinct SKOS concepts in CS and IS.
 *
 * @author cleme
 */
public class getDistinctSKOSconceptsSubDomains {
    public static void main(String [] args){
        String path = "D:\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction";
        
        //File file_CS = new File(path + "\\" + "areas of computer science_subKB_10.txt");
        //File file_CS = new File(path + "\\" + "edu_new_subKB_10.txt");
        File file_CS = new File(path + "\\" + "eco_disjoint_all_subKB_10.txt");
        //File file_IS = new File(path + "\\" + "information science_subKB_10.txt");
        //File fileW = new File(path + "\\" + "distinct_dbpedia_skos_concept_CS_IS.txt");
        //File fileW = new File(path + "\\" + "distinct_dbpedia_skos_concept_edu_new.txt");
        File fileW = new File(path + "\\" + "distinct_dbpedia_skos_concept_eco_disjoint_all.txt");
        BufferedReader reader_CS = null;
        BufferedReader reader_IS = null;
        BufferedWriter writer = null;
        String tempString;
        String tag;
        HashMap <String,Integer> hm = new HashMap<String,Integer>();
        boolean contain;
        try {
            reader_CS = new BufferedReader(new FileReader(file_CS));
            //reader_IS = new BufferedReader(new FileReader(file_IS));
            writer = new BufferedWriter(new FileWriter(fileW));
            
            int line = 0;
            
            while ((tempString = reader_CS.readLine()) != null) {
                String [] concepts = tempString.split(" <- ");
                if (concepts.length == 2){
                    hm.put(concepts[0],1);
                    hm.put(concepts[1],1);
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                line++;
            }
            
//            while ((tempString = reader_IS.readLine()) != null) {
//                String [] concepts = tempString.split(" <- ");
//                if (concepts.length == 2){
//                    hm.put(concepts[0],1);
//                    hm.put(concepts[1],1);
//                }else{
//                    System.out.println("[wrong format]" + tempString);
//                }
//                line++;
//            }
//            reader_IS.close();
                    
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
