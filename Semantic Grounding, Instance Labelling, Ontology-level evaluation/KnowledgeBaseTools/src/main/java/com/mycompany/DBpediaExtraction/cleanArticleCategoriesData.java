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

/** clean the article categories data from the 4 outputs in QueryAllArticleCategoriesData.java
 * cleaning:
 * [1] replace all "_" to " "
 * [2] remove all "Category:"
 * [3] remove all parentheses "(xxx)" and things inside parentheses.
 * 
 * This is also used for SKOS categories data.
 * @author cleme
 */
public class cleanArticleCategoriesData {
    public static void main(String [] args){
        File file1 = new File("all_article_categories_data_part1.txt");
        File file2 = new File("all_article_categories_data_part2.txt");
        File file3 = new File("all_article_categories_data_part3.txt");
        File file4 = new File("all_article_categories_data_part4.txt");
        File file5skos = new File("all_skos_data_dbpedia.txt"); 
        //File fileW = new File("all_article_cat_data_cleaned.txt");
        File fileW = new File("all_skos_and_article_cat_data_cleaned.txt");
    // for further cleanning the SKOS categories data
        //File file1 = new File("all_skos_data_dbpedia.txt"); 
        //File fileW = new File("all_skos_data_dbpedia_cleaned.txt");
        
        BufferedReader reader1 = null;
        BufferedReader reader2 = null;
        BufferedReader reader3 = null;
        BufferedReader reader4 = null;
        BufferedReader reader5skos = null;
        BufferedWriter writer = null;
        try {
            reader1 = new BufferedReader(new FileReader(file1));
            reader2 = new BufferedReader(new FileReader(file2));
            reader3 = new BufferedReader(new FileReader(file3));
            reader4 = new BufferedReader(new FileReader(file4));
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
            
            while ((tempString = reader1.readLine()) != null) {
                tempString = tempString.replace("_", " ").replace("Category:", "");
                tempString = removeFirstParenthesis(removeFirstParenthesis(tempString));
                writer.write(tempString.trim().replaceAll(" +", " "));
                writer.newLine();
            }
            
            while ((tempString = reader2.readLine()) != null) {
                tempString = tempString.replace("_", " ").replace("Category:", "");
                tempString = removeFirstParenthesis(removeFirstParenthesis(tempString));
                writer.write(tempString.trim().replaceAll(" +", " "));
                writer.newLine();
            }
            reader2.close();
                    
            while ((tempString = reader3.readLine()) != null) {
                tempString = tempString.replace("_", " ").replace("Category:", "");
                tempString = removeFirstParenthesis(removeFirstParenthesis(tempString));
                writer.write(tempString.trim().replaceAll(" +", " "));
                writer.newLine();
            }
            reader3.close();
            
            while ((tempString = reader4.readLine()) != null) {
                tempString = tempString.replace("_", " ").replace("Category:", "");
                tempString = removeFirstParenthesis(removeFirstParenthesis(tempString));
                writer.write(tempString.trim().replaceAll(" +", " "));
                writer.newLine();
            }
            reader4.close();
            
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
