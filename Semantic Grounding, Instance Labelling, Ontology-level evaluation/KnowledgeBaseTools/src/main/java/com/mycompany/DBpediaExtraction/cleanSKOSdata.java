/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DBpediaExtraction;

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
public class cleanSKOSdata {
    public static void main(String [] args){
        
        File file5skos = new File("all_skos_data_dbpedia.txt"); 
        //File fileW = new File("all_article_cat_data_cleaned.txt");
        File fileW = new File("all_skos_data_cleaned.txt");
    // for further cleanning the SKOS categories data
        //File file1 = new File("all_skos_data_dbpedia.txt"); 
        //File fileW = new File("all_skos_data_dbpedia_cleaned.txt");
        
        BufferedReader reader5skos = null;
        BufferedWriter writer = null;
        try {
            reader5skos = new BufferedReader(new FileReader(file5skos));
            writer = new BufferedWriter(new FileWriter(fileW));
            String tempString = null;

            // store all distinct concepts in LO to hm.
            // deal with single quotes in the inputfile in LO.
            while ((tempString = reader5skos.readLine()) != null) {
                tempString = tempString.replace(" < ", " <- ");
                tempString = tempString.replace("_", " ").replace("Category:", "");
                tempString = removeFirstParenthesis(removeFirstParenthesis(tempString));
                writer.write(tempString.trim().replaceAll(" +", " "));
                writer.newLine();
            }
            reader5skos.close();
                       
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader5skos != null) {
                try {
                    reader5skos.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    
    public static String removeFirstParenthesis(String target){
        int lp = target.indexOf("(");
        int rp = target.indexOf(")");
        String changed;
        if (lp>0 && rp>0 && rp>lp){
            changed= target.substring(0,lp) + target.substring(rp+1);
//            if (changed.charAt(changed.length()-1) == '_'){
//                return changed.substring(0,changed.length()-1);
//            }else{
//                return changed;
//            }
            return changed;
        }else{
            return target;
        }
    }
}
