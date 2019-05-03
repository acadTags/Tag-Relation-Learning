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
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/** [important file] Querying all lower direct DCT pages using article category information.
 *
 * @author hong.dong
 */
public class QueryingLowerDCTpages {
    public static void main(String[] args){
        System.out.println(new Date( ));
        for (int i=1;i<5;i++){
            String InputSourceFileName = "all_article_categories_data_part" + i + ".txt";
            //String InputUpperConceptPairsFileName1 = "machine_learning_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName2 = "semantic_web_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName3 = "social_information_processing_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName4 = "natural_language_processing_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName5 = "data_mining_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName6 = "internet_of_things_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName7 = "library_science_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName8 = "neural_network_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName9 = "big_data_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName10 = "information_retrieval_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName11 = "human_computer_interaction_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName12 = "genetics_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName13 = "computational_chemistry_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName14 = "chemistry_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName15 = "user_interfaces_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName16 = "knowledge_management_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName17 = "social_networks_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName18 = "web_development_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName19 = "databases_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName20 = "algorithms_sub_tree_wiki_cat.txt";
            //String InputUpperConceptPairsFileName21 = "mathematics_sub_tree_wiki_cat.txt";
            String InputUpperConceptPairsFileName22 = "software_engineering_sub_tree_wiki_cat.txt";
            
            HashMap<String,String> hm_b_n = getConceptPairs(InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName1,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName2,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName3,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName4,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName5,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName6,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName7,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName8,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName9,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName10,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName11,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName12,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName13,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName14,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName15,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName16,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName17,InputSourceFileName);
//            search(hm_b_n,InputUpperConceptPairsFileName18,InputSourceFileName);
//            search(hm_b_n,InputUpperConceptPairsFileName19,InputSourceFileName);
//            search(hm_b_n,InputUpperConceptPairsFileName20,InputSourceFileName);
            //search(hm_b_n,InputUpperConceptPairsFileName21,InputSourceFileName);
            search(hm_b_n,InputUpperConceptPairsFileName22,InputSourceFileName);
        }
//        hm_b_n.clear();
//        InputSourceFileName = "all_article_categories_data_part4.txt";
//        InputUpperConceptPairsFileName1 = "machine_learning_tree_wiki_cat.txt";
//        InputUpperConceptPairsFileName2 = "semantic_web_tree_wiki_cat.txt";
//        hm_b_n = getConceptPairs(InputSourceFileName);
//        search(hm_b_n,InputUpperConceptPairsFileName1,InputSourceFileName);
//        search(hm_b_n,InputUpperConceptPairsFileName2,InputSourceFileName);
    }
    
    public static void search(HashMap<String,String> hm_b_n, String InputUpperConceptPairsFileName, String InputSourceFileName){
        //HashMap hm_b_n = getConceptPairs(InputSourceFileName);
        HashMap<String, Integer> ht = getDistinctUpperConcepts(InputUpperConceptPairsFileName);
        getAllLowerPages(ht,hm_b_n, InputUpperConceptPairsFileName.substring(0,10) + "_sub_" + InputSourceFileName);
    }
    
    public static HashMap<String,String> getConceptPairs(String InputSourceFileName){
        File file = new File(InputSourceFileName);
        BufferedReader reader = null;
        HashMap<String, String> hm_b_n = new HashMap<String, String>(); //broader_narrower
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine()) != null) {
                String [] concepts = tempString.split(" <- ");
                //System.out.println(concepts[0] + " ||| " + concepts[1]);
                if (concepts.length == 2){
                    //concepts[0] = removeCategory(concepts[0]);
                    //concepts[1] = removeCategory(concepts[1]);
//                    if (hm_n_b.containsKey(concepts[0])){
//                        hm_n_b.put(concepts[0], hm_n_b.get(concepts[0]) + " ||| " + concepts[1]);
//                    }else{
//                        hm_n_b.put(concepts[0],concepts[1]);
//                    }
                    if (hm_b_n.containsKey(concepts[1])){
                        hm_b_n.put(concepts[1], hm_b_n.get(concepts[1]) + " ||| " + concepts[0]);
                    }else{
                        hm_b_n.put(concepts[1],concepts[0]);
                    }
                }else{
                    System.out.println(tempString);
                }
                //line++;
            }
            System.out.println("article category data loaded to hashmap at " + new Date( ));
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
        return hm_b_n;
    }
    
    public static HashMap<String,Integer> getDistinctUpperConcepts(String InputUpperConceptPairsFileName){
        File fileupper = new File(InputUpperConceptPairsFileName);
        BufferedReader readerupper = null;
        HashMap<String, Integer> ht = new HashMap<String, Integer>();
        try {
            readerupper = new BufferedReader(new FileReader(fileupper));
            String tempString = null;
            while ((tempString = readerupper.readLine()) != null) {
                String [] concepts = tempString.split(" <- ");
                for (String concept: concepts){
                    ht.put(concept, 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readerupper != null) {
                try {
                    readerupper.close();
                } catch (IOException e1) {
                }
            }
        }
        return ht;
    }
    
    public static void getAllLowerPages(HashMap<String,Integer> ht, HashMap<String,String> hm_b_n, String OutputFileName){
        File fileW = new File(OutputFileName);
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(fileW));
            Set<Entry<String, Integer>> set = ht.entrySet();
            for(Entry<String, Integer> en: set){
                System.out.println("getting pages for " + en.getKey());
                if (hm_b_n.containsKey(en.getKey())){
                    String result = hm_b_n.get(en.getKey());
                    String [] concepts = result.split(" \\|\\|\\| ");
                    for (String concept: concepts){
                        //System.out.println(concept + " <- " + query);
                        writer.write(concept + " <- " + en.getKey());
                        writer.newLine();
                    }
                }
            }    
            writer.flush();
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }    
    }
    
    public static void getAllLowerPages(String query, HashMap<String,String> hm_b_n){
        if (hm_b_n.containsKey(query)){
            String result = hm_b_n.get(query);
            String [] concepts = result.split(" \\|\\|\\| ");
            for (String concept: concepts){
                System.out.println(concept + " <- " + query);
            }
        }
    }
    
    public static String removeCategory(String concept){
        if (concept.indexOf("Category:") == 0){
            return concept.substring(9);
        }else{
            return concept;
        }
    }
}
