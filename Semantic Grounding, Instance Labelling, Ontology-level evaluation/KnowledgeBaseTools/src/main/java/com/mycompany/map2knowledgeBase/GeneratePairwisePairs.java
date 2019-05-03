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

/** generate pairwise tag pairs from the matched tags.
 *
 * @author cleme
 */
public class GeneratePairwisePairs {
    public static void main(String [] args){
        // for dbpedia skos
        //File file = new File("bibsonomy-tag-skos-concept-ed0-new.txt");
        //File fileW = new File("bibsonomy_skos_withredir_pw_candidts_all.csv");
        
        // for acm
        //File file = new File("bibsonomy-tag-acm-concpet-ed0.txt");
        //File fileW = new File("bibsonomy_acm_pw_candidts_all.csv");
        
        // for mcg5
        File file = new File("bibsonomy-tag-mcg-concpet-ed0.txt");
        File fileW = new File("bibsonomy_mcg5_pw_candidts_all.csv");
        
        BufferedReader reader = null;
        BufferedWriter writer = null;
        String tempString;
        String [] concepts;
        //HashMap<String,String> hm = new HashMap<String,String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(fileW));
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
                line++;
            }
            //System.out.println(line);
            concepts = new String [line];
            reader.close();
            
            reader = new BufferedReader(new FileReader(file));
            line = 0;
            while ((tempString = reader.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    concepts[line] = arr[0];
                }else{
                    System.out.println(tempString);
                }
                line++;
            }
            reader.close();
            
            for (int i=0;i<concepts.length;i++){
                System.out.println(i);
                for (int j=i+1;j<concepts.length;j++){
                    writer.write(concepts[i] + "," + concepts[j]);
                    writer.newLine();
                    writer.write(concepts[j] + "," + concepts[i]);
                    writer.newLine();
                }
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