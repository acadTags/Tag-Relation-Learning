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

/** [this program is not used]
 *
 * @author cleme
 */
public class GeneratePositiveDataOnly {
    public static void main(String [] args){
        String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\";
        
        //String path = "/Users/hangdong/OneDrive/NetBeansProjects/DBpediaHierarchyExtraction/";
        
        // the difference between _labelled and _labelled_hier is that the former takes equivalence as bi-directional hierarchies, but the latter not.
        // we use the latter idea.
        //File file_DBpedia = new File(path + "bibsonomy_skos_withredir_pw_candidts0.1_labelled.csv");
        //File file_ACM = new File(path + "bibsonomy_acm_pw_candidts0.1_labelled.csv");
        //File file_MCG = new File(path + "bibsonomy_mcg5_pw_candidts0.1_labelled.csv");
        
//        File file_DBpedia = new File(path + "bibsonomy_skos_withredir_pw_candidts0.1_labelled_hier.csv");
//        File file_ACM = new File(path + "bibsonomy_acm_pw_candidts0.1_labelled_hier.csv");
//        File file_MCG = new File(path + "bibsonomy_mcg5_pw_candidts0.1_labelled_hier.csv");
//        File fileWpositive = new File(path + "positive_instances_hier.txt");
//        File fileWrev_negative = new File(path + "reversed-neg_instances_hier.txt");
//        File fileWrnd_negative = new File(path + "random-neg_instances-whole_hier.txt");
        
        File file_DBpedia = new File(path + "bibsonomy_skos_withredir_pw_candidts_all_labelled.csv");
        File file_ACM = new File(path + "bibsonomy_acm_pw_candidts_all_labelled.csv");
        File file_MCG = new File(path + "bibsonomy_mcg5_pw_candidts_all_labelled.csv");
        File fileWpositive = new File(path + "positive_instances_all.txt");
        //File fileWrev_negative = new File(path + "reversed-neg_instances_all.txt");
        //File fileWrnd_negative = new File(path + "random-neg_instances-whole_all.txt");

        BufferedReader reader_DBpedia = null;
        BufferedReader reader_ACM = null;
        BufferedReader reader_MCG = null;
        BufferedWriter writerWpositive = null;
        //BufferedWriter writerWrev_negative = null;
        //BufferedWriter writerWrnd_negative = null;
        String tempString;
        //String tag;
        HashMap <String,Integer> hm_pos = new HashMap<String,Integer>();
        //HashMap <String,Integer> hm_rev_neg = new HashMap<String,Integer>();
        //HashMap <String,Integer> hm_rnd_neg = new HashMap<String,Integer>();
        //boolean contain;
        String pair;
        try {
            reader_DBpedia = new BufferedReader(new FileReader(file_DBpedia));
            reader_ACM = new BufferedReader(new FileReader(file_ACM));
            reader_MCG = new BufferedReader(new FileReader(file_MCG));
            writerWpositive = new BufferedWriter(new FileWriter(fileWpositive));
            //writerWrev_negative = new BufferedWriter(new FileWriter(fileWrev_negative));
            //writerWrnd_negative = new BufferedWriter(new FileWriter(fileWrnd_negative));
            
            int line = 0;
            System.out.println("start to read DBpedia labelled pairs");
            // read DBpedia labelled pairs.
            while ((tempString = reader_DBpedia.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 3){
                    if (arr[2].equals("true")){
                        hm_pos.put(arr[0] + "," + arr[1],1);
                    }else if(arr[2].equals("false")){
                    }else{
                        System.out.println("[wrong format]" + tempString);
                    }
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                line++;
                if (line % 10000 == 0){
                    System.out.println(line);
                }    
            }
            
            System.out.println("start to read ACM labelled pairs");
            // read ACM labelled pairs.
            line = 0;
            while ((tempString = reader_ACM.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 3){
                    if (arr[2].equals("true")){
                        hm_pos.put(arr[0] + "," + arr[1],1);
                    }else if(arr[2].equals("false")){
                    }else{
                        System.out.println("[wrong format]" + tempString);
                    }
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                //line++;
                line++;
                if (line % 10000 == 0){
                    System.out.println(line);
                }
            }
            reader_ACM.close();
            
            System.out.println("start to read MCG labelled pairs");
            // read MCG labelled pairs.
            line = 0;
            while ((tempString = reader_MCG.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 3){
                    if (arr[2].equals("true")){
                        hm_pos.put(arr[0] + "," + arr[1],1);
                    }else if(arr[2].equals("false")){
                    }else{
                        System.out.println("[wrong format]" + tempString);
                    }
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                //line++;
                line++;
                if (line % 100000 == 0){
                    System.out.println(line);
                }
            }
            reader_MCG.close();
            
            System.out.println("start to output");
            Set<Map.Entry<String, Integer>> set = hm_pos.entrySet();
            for(Map.Entry<String, Integer> en: set){
                pair = en.getKey();
                // output positive instances
                writerWpositive.write(pair);
                writerWpositive.newLine();
                
            }
            
            writerWpositive.flush();
            writerWpositive.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader_DBpedia != null) {
                try {
                    reader_DBpedia.close();
                } catch (IOException e1) {
                }
            }
        }    
    }
}
