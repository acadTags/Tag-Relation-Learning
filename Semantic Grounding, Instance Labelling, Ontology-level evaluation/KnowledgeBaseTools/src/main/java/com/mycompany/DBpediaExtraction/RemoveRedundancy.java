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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** [important file] Remove the redundant tag pairs after human selection. This is the last step at this stage.
 * modified on Apr 5 2017: check same concepts in a concept pairs and output them a <- a.
 * @author Hang Dong
 */
public class RemoveRedundancy {
    public static void main(String [] args){
        String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\domains matched to tags -subcat only\\";
//        String path = "/Users/hangdong/OneDrive/NetBeansProjects/DBpediaHierarchyExtraction/final matched data human selected/";
//        RemoveRedundancy(path, "social_inf_sub_all_data_matched_to_tags.txt");
//        RemoveRedundancy(path, "internet_o_sub_all_data_matched_to_tags.txt");
//        RemoveRedundancy(path, "natural_la_sub_all_data_matched_to_tags.txt");
//        RemoveRedundancy(path, "data_minin_sub_all_data_matched_to_tags.txt");
//        RemoveRedundancy(path, "machine_learning_mapped_human_selected.txt");
//        RemoveRedundancy(path, "semantic_web_mapped_human_selected.txt");
          //RemoveRedundancy(path, "the_six_categories_mapped_human_selection_reduced_new_regardless_of_root.txt");
//          RemoveRedundancy(path, "library_sc_sub_all_data_matched_to_tags.txt");

        //RemoveRedundancy(path, "social_inf_sub_all_data_matched_to_tags_redirect.txt");
//        RemoveRedundancy(path, "internet_o_sub_all_data_matched_to_tags_redirect.txt");
//        RemoveRedundancy(path, "natural_la_sub_all_data_matched_to_tags_redirect.txt");
//        RemoveRedundancy(path, "data_minin_sub_all_data_matched_to_tags_redirect.txt");
//        RemoveRedundancy(path, "machine_learning_mapped_human_selected_redirect.txt");
//        RemoveRedundancy(path, "semantic_web_mapped_human_selected_redirect.txt");
//
//        RemoveRedundancy(path, "genetics_sub_all_data_matched_to_tags_redirect.txt");
//        //RemoveRedundancy(path, "library_sc_sub_all_data_matched_to_tags_redirect.txt");
//        RemoveRedundancy(path,"social_networks_sub_all_data_matched_to_tags_redir.txt");
//        RemoveRedundancy(path,"social_information_processing_sub_all_data_exclude_social_networks.txt");        
        
        //RemoveRedundancy(path,"mathematics_sub_tree_wiki_cat.txt");
            
//        RemoveRedundancy(path, "software_engineering_subcat_matched.txt");
//        RemoveRedundancy(path, "genetics_subcat_matched.txt");
//        RemoveRedundancy(path, "data_mining_subcat_matched.txt");
//        RemoveRedundancy(path, "databases_subcat_matched.txt");
//        RemoveRedundancy(path, "machine_learning_subcat_matched.txt");
//        RemoveRedundancy(path, "natural_language_processing_subcat_matched.txt");
//        RemoveRedundancy(path, "semantic_web_subcat_matched.txt");
//        RemoveRedundancy(path,"the_six_categories_mapped_redir_regardless_of_root.txt");

        //RemoveRedundancy(path,"in-domain rand-neg.txt");
        
//        RemoveRedundancy(path, "software_engineering_subcat_matched_noredir_ed1.txt");
//        RemoveRedundancy(path, "genetics_subcat_matched_noredir_ed1.txt");
//        RemoveRedundancy(path, "data_mining_subcat_matched_noredir_ed1.txt");
//        RemoveRedundancy(path, "databases_subcat_matched_noredir_ed1.txt");
//        RemoveRedundancy(path, "machine_learning_subcat_matched_noredir_ed1.txt");
//        RemoveRedundancy(path, "natural_language_processing_subcat_matched_noredir_ed1.txt");
//        RemoveRedundancy(path, "semantic_web_subcat_matched_noredir_ed1.txt");
//        RemoveRedundancy(path, "mathematics_subcat_matched_noredir_ed1.txt");
    }
    public static void RemoveRedundancy(String path, String InputFileName){
        System.out.println(InputFileName);
        File file = new File(path + InputFileName);
        File fileW = new File(path + InputFileName.substring(0, InputFileName.length()-4) + "_reduced.txt");
        BufferedReader reader = null;
        BufferedWriter writer = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(fileW));
            
            HashMap<String, Integer> hm = new HashMap<String, Integer>();
            
            String tempString;
            
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                if (!hm.containsKey(tempString)){
                    hm.put(tempString, 1);
                }else{
                    hm.put(tempString, hm.get(tempString) + 1);
                    //System.out.println(line + " " + tempString);
                }
                line++;
            }
            
            Set<Map.Entry<String, Integer>> set = hm.entrySet();
        
            //Iterate through Entry 
            for(Map.Entry<String, Integer> en: set){
                //if (en.getValue() == 1){
                    if (!AreSameConcepts(en.getKey())){
                        writer.write(en.getKey());
                        writer.newLine();
                    }
                    
                    if (en.getValue() > 1){
                        System.out.println(en.getKey());
                    }
                //}else{
                //    System.out.println(en.getKey());
                //}
            }
            
            writer.flush();
            writer.close();
        } catch (Exception e) {
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
    
    public static boolean AreSameConcepts(String tagpair){
        String [] concepts = tagpair.split(" <- ");
        if (concepts.length == 2){
            // check whether the concept contains white space.
            if (concepts[0].indexOf(" ") > 0 || concepts[1].indexOf(" ") > 0){
                System.out.println("[problem]: white space in concept in " + tagpair);
            }
            
            if (concepts[0].equals(concepts[1])){
                return true;
            }else{
                return false;
            }
        }else{
            System.out.println("[problem]: length not 2 with " + tagpair);
            return false;
        }
    }
}
