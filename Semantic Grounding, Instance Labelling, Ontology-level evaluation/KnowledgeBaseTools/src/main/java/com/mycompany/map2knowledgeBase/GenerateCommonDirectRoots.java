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

/** Generate common direct root from the data.
 * This program can result different number of positive instances vs. reversed instances due to the multiple roots for a pair [no way to determine with one is most direct].
 * @author hangdong
 */
public class GenerateCommonDirectRoots {
    public static void main(String [] args){
        String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\";
        
        //String path = "/Users/hangdong/OneDrive/NetBeansProjects/DBpediaHierarchyExtraction/";
        File file_positive = new File(path + "positive_instances_final.txt");
        File file_rev_neg = new File(path + "reversed-neg_instances_final.txt");
        File file_rnd_neg = new File(path + "random-neg_instances-whole_final.txt");
//        File file_positive = new File(path + "positive_instances_hier.txt");
//        File file_rev_neg = new File(path + "reversed-neg_instances_hier.txt");
//        File file_rnd_neg = new File(path + "random-neg_instances-whole_hier.txt");
        File fileWpositiveR = new File(path + "positive_instances_with_root_final.txt");
        File fileWrev_negativeR = new File(path + "reversed-neg_instances_with_root_final.txt");
        File fileWrnd_negativeR = new File(path + "random-neg_instances-whole_with_root_final.txt");
        
        BufferedReader reader_positive = null;
        BufferedReader reader_rev_neg = null;
        BufferedReader reader_rnd_neg = null;
        BufferedWriter writerWpositiveR = null;
        BufferedWriter writerWrev_negativeR = null;
        BufferedWriter writerWrnd_negativeR = null;
        String tempString;
        //String tag;
        HashMap <String,String> hm = new HashMap<String,String>();
        //HashMap <String,Integer> hm_rev_neg = new HashMap<String,Integer>();
        //HashMap <String,Integer> hm_rnd_neg = new HashMap<String,Integer>();
        //boolean contain;
        String root;
        String pair_with_root;
        try {
            reader_positive = new BufferedReader(new FileReader(file_positive));
            reader_rev_neg = new BufferedReader(new FileReader(file_rev_neg));
            reader_rnd_neg = new BufferedReader(new FileReader(file_rnd_neg));
            writerWpositiveR = new BufferedWriter(new FileWriter(fileWpositiveR));
            writerWrev_negativeR = new BufferedWriter(new FileWriter(fileWrev_negativeR));
            writerWrnd_negativeR = new BufferedWriter(new FileWriter(fileWrnd_negativeR));
            //int line = 0;
            
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
                //line++;
            }
            reader_positive.close();
            reader_positive = new BufferedReader(new FileReader(file_positive));
            while ((tempString = reader_positive.readLine()) != null) {
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
                                    writerWpositiveR.write(tempString + "," + root);
                                    writerWpositiveR.newLine();
                                }
                            }
                        }
                        if (root.equals("")){
                            root = arr[1];
                            writerWpositiveR.write(tempString + "," + root);
                            writerWpositiveR.newLine();
                        }
                    }else{
                        root = arr[1];
                        writerWpositiveR.write(tempString + "," + root);
                        writerWpositiveR.newLine();
                    }
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                //writerWpositiveR.write(tempString + "," + root);
                //writerWpositiveR.newLine();
                //line++;
            }
            
            while ((tempString = reader_rev_neg.readLine()) != null) {
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
                                    //System.out.println(tempString + "," + root);
                                    writerWrev_negativeR.write(tempString + "," + root);
                                    writerWrev_negativeR.newLine();
                                }
                            }
                        }
                        if (root.equals("")){
                            root = arr[1];
                            writerWrev_negativeR.write(tempString + "," + root);
                            writerWrev_negativeR.newLine();
                        }
                    }else{
                        root = arr[1];
                        writerWrev_negativeR.write(tempString + "," + root);
                        writerWrev_negativeR.newLine();
                    }
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                //writerWpositiveR.write(tempString + "," + root);
                //writerWpositiveR.newLine();
                //line++;
            }
            
            while ((tempString = reader_rnd_neg.readLine()) != null) {
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
                                    //System.out.println(tempString + "," + root);
                                    writerWrnd_negativeR.write(tempString + "," + root);
                                    writerWrnd_negativeR.newLine();
                                }
                            }
                        }
                        if (root.equals("")){
                            root = arr[1];
                            writerWrnd_negativeR.write(tempString + "," + root);
                            writerWrnd_negativeR.newLine();
                        }
                    }else{
                        root = arr[1];
                        writerWrnd_negativeR.write(tempString + "," + root);
                        writerWrnd_negativeR.newLine();
                    }
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                //writerWpositiveR.write(tempString + "," + root);
                //writerWpositiveR.newLine();
                //line++;
            }
            
            writerWpositiveR.flush();
            writerWpositiveR.close();
            writerWrev_negativeR.flush();
            writerWrev_negativeR.close();
            writerWrnd_negativeR.flush();
            writerWrnd_negativeR.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader_positive != null) {
                try {
                    reader_positive.close();
                } catch (IOException e1) {
                }
            }
        }    
    }
}
