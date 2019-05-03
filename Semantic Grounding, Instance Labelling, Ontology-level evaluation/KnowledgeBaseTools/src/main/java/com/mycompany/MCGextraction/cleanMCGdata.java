/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.MCGextraction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author cleme
 */
public class cleanMCGdata {
    public static void main(String [] args){
        File file1 = new File("data-concept-instance-relations.txt");
        File fileW = new File("MCG5_cleaned.txt");
    // for further cleanning the SKOS categories data
        //File file1 = new File("all_skos_data_dbpedia.txt"); 
        //File fileW = new File("all_skos_data_dbpedia_cleaned.txt");
        
        BufferedReader reader1 = null;
        BufferedWriter writer = null;
        int strength;
        try {
            reader1 = new BufferedReader(new FileReader(file1));
            writer = new BufferedWriter(new FileWriter(fileW));
            String tempString = null;

            while ((tempString = reader1.readLine()) != null) {
                String [] arr = tempString.split("\t");
                if (arr.length == 3){
                    strength = Integer.parseInt(arr[2]);
                    if (strength >= 5){
                        writer.write(arr[1] + " <- " + arr[0]);
                        writer.newLine();
                    }
                }    
                //writer.write(tempString.trim().replaceAll(" +", " "));
                //writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader1 != null) {
                try {
                    reader1.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}    