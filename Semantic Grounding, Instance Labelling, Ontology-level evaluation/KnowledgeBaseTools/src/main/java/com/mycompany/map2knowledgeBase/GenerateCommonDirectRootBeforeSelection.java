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

/** [this program is not used, aborted and not entirely correct]
 *
 * @author cleme
 */
public class GenerateCommonDirectRootBeforeSelection {
    public static void main(String [] args){
        String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\";
        
        File file_positive = new File(path + "positive_instances_all.txt");
        File file_DBpedia = new File(path + "bibsonomy_skos_withredir_pw_candidts_all_labelled.csv");
        File file_ACM = new File(path + "bibsonomy_acm_pw_candidts_all_labelled.csv");
        File file_MCG = new File(path + "bibsonomy_mcg5_pw_candidts_all_labelled.csv");
        File fileWDBpediaWithRoot = new File(path + "bibsonomy_skos_withredir_pw_candidts_all_labelled_root.txt");
        File fileWACMwithRoot = new File(path + "bibsonomy_acm_pw_candidts_all_labelled_root.txt");
        File fileWMCGwithRoot = new File(path + "bibsonomy_mcg5_pw_candidts_all_labelled-whole_all_root.txt");
        
        BufferedReader reader_positive = null;
        BufferedReader reader_DBpedia = null;
        BufferedReader reader_ACM = null;
        BufferedReader reader_MCG = null;
        BufferedWriter writerDBpediaWithRoot = null;
        BufferedWriter writerACMwithRoot = null;
        BufferedWriter writerMCGwithRoot = null;
        String tempString;
        //String tag;
        HashMap <String,String> hm = new HashMap<String,String>();
        // storing
        
        //boolean contain;
        String root;
        try {
            reader_positive = new BufferedReader(new FileReader(file_positive));
            reader_DBpedia = new BufferedReader(new FileReader(file_DBpedia));
            reader_ACM = new BufferedReader(new FileReader(file_ACM));
            reader_MCG = new BufferedReader(new FileReader(file_MCG));
            writerDBpediaWithRoot = new BufferedWriter(new FileWriter(fileWDBpediaWithRoot));
            writerACMwithRoot = new BufferedWriter(new FileWriter(fileWACMwithRoot));
            writerMCGwithRoot = new BufferedWriter(new FileWriter(fileWMCGwithRoot));
            
            // store hypohym-hypernym1_hypernym2_hypernym3 key_value pairs in hm.
            while ((tempString = reader_positive.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    if (!hm.containsKey(arr[0])){
                        hm.put(arr[0], arr[1]);
                    }else{
                        hm.put(arr[0], hm.get(arr[0]) + "," + arr[1]);
                    }    
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
            }
            
            while ((tempString = reader_DBpedia.readLine()) != null) {
                root = "";
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    if (hm.containsKey(arr[0]) && hm.containsKey(arr[1])){
                        String [] arr1 = hm.get(arr[0]).split(",");
                        String [] arr2 = hm.get(arr[1]).split(",");
                        for (String hypo_arr1: arr1){
                            for (String hypo_arr2: arr2){
                                if (hypo_arr1.equals(hypo_arr2)){
                                    root = hypo_arr1;
                                    //display enriched roots which are different from the hypernym.
                                    System.out.println(tempString + "," + root);
                                    writerDBpediaWithRoot.write(tempString + "," + root);
                                    writerDBpediaWithRoot.newLine();
                                }
                            }
                        }
                        if (root.equals("")){ // no common hypernym
                            root = arr[1];
                            writerDBpediaWithRoot.write(tempString + "," + root);
                            writerDBpediaWithRoot.newLine();
                        }
                    }else{ // at least one of the concept have no hypernym
                        root = arr[1];
                        writerDBpediaWithRoot.write(tempString + "," + root);
                        writerDBpediaWithRoot.newLine();
                    }
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
            }
            reader_DBpedia.close();
            
            while ((tempString = reader_ACM.readLine()) != null) {
                root = "";
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    if (hm.containsKey(arr[0]) && hm.containsKey(arr[1])){
                        String [] arr1 = hm.get(arr[0]).split(",");
                        String [] arr2 = hm.get(arr[1]).split(",");
                        for (String hypo_arr1: arr1){
                            for (String hypo_arr2: arr2){
                                if (hypo_arr1.equals(hypo_arr2)){
                                    root = hypo_arr1;
                                    //display enriched roots which are different from the hypernym.
                                    System.out.println(tempString + "," + root);
                                    writerACMwithRoot.write(tempString + "," + root);
                                    writerACMwithRoot.newLine();
                                }
                            }
                        }
                        if (root.equals("")){ // no common hypernym
                            root = arr[1];
                            writerACMwithRoot.write(tempString + "," + root);
                            writerACMwithRoot.newLine();
                        }
                    }else{ // at least one of the concept have no hypernym
                        root = arr[1];
                        writerACMwithRoot.write(tempString + "," + root);
                        writerACMwithRoot.newLine();
                    }
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
            }
            reader_ACM.close();
            
            while ((tempString = reader_MCG.readLine()) != null) {
                root = "";
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    if (hm.containsKey(arr[0]) && hm.containsKey(arr[1])){
                        String [] arr1 = hm.get(arr[0]).split(",");
                        String [] arr2 = hm.get(arr[1]).split(",");
                        for (String hypo_arr1: arr1){
                            for (String hypo_arr2: arr2){
                                if (hypo_arr1.equals(hypo_arr2)){
                                    root = hypo_arr1;
                                    //display enriched roots which are different from the hypernym.
                                    System.out.println(tempString + "," + root);
                                    writerMCGwithRoot.write(tempString + "," + root);
                                    writerMCGwithRoot.newLine();
                                }
                            }
                        }
                        if (root.equals("")){ // no common hypernym
                            root = arr[1];
                            writerMCGwithRoot.write(tempString + "," + root);
                            writerMCGwithRoot.newLine();
                        }
                    }else{ // at least one of the concept have no hypernym
                        root = arr[1];
                        writerMCGwithRoot.write(tempString + "," + root);
                        writerMCGwithRoot.newLine();
                    }
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
            }
            reader_MCG.close();
            
            writerDBpediaWithRoot.flush();
            writerDBpediaWithRoot.close();
            
            writerACMwithRoot.flush();
            writerACMwithRoot.close();
            
            writerMCGwithRoot.flush();
            writerMCGwithRoot.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader_DBpedia != null) {
                try {
                    reader_positive.close();
                } catch (IOException e1) {
                }
            }
        }    
    }
}

