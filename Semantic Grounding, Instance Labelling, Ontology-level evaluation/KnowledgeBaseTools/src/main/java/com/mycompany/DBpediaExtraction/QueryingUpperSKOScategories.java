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

/**
 * [important file] This extracts all skos data starting from a concept.
 * Extracting all subordinate/subsumed concepts, also one level ancestors of the starting concept and all subsumed concepts.
 * Input: "all_skos_data_dbpedia_url_name.txt" obtained using QueryAllSkosData.java
 * Input: an exact concept query, like "Category:Machine_learning"
 * Output: concept pairs in "machine_learning_tree_wiki_cat.txt"
 * @author hong.dong
 */
public class QueryingUpperSKOScategories {
    public static void main(String[] args){
        System.out.println(new Date( ));
        File files = new File("all_skos_data_dbpedia_url_name.txt");
        //File filec1 = new File("all_article_categories_data_part1.txt");
        //File filec2 = new File("all_article_categories_data_part2.txt");
        //File filec3 = new File("all_article_categories_data_part3.txt");
        //File filec4 = new File("all_article_categories_data_part4.txt");
        //File fileW = new File("C:\\Users\\hang.dong\\Documents\\tag processing\\(a)(f)standardised_tags_2.txt");
        BufferedReader readers = null;
        //BufferedReader readerc1 = null;
        //BufferedReader readerc2 = null;
        //BufferedReader readerc3 = null;
        //BufferedReader readerc4 = null;
        //BufferedWriter writer = null;
        try {
            readers = new BufferedReader(new FileReader(files));
            //readerc1 = new BufferedReader(new FileReader(filec1));
            //readerc2 = new BufferedReader(new FileReader(filec2));
            //readerc3 = new BufferedReader(new FileReader(filec3));
            //readerc4 = new BufferedReader(new FileReader(filec4));
            //writer = new BufferedWriter(new FileWriter(fileW));
            
            String tempString = null;
            //Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
            HashMap<String, String> hm_n_b = new HashMap<String, String>(); //narrower_broader
            HashMap<String, String> hm_b_n = new HashMap<String, String>(); //broader_narrower
            // store data to hashmap
            //int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = readers.readLine()) != null) {
                String [] concepts = tempString.split(" <- ");
                //System.out.println(concepts[0] + " ||| " + concepts[1]);
                if (concepts.length == 2){                   
                    //concepts[0] = removeCategory(concepts[0]);
                    //concepts[1] = removeCategory(concepts[1]);
                    if (hm_n_b.containsKey(concepts[0])){
                        hm_n_b.put(concepts[0], hm_n_b.get(concepts[0]) + " ||| " + concepts[1]);
                    }else{
                        hm_n_b.put(concepts[0],concepts[1]);
                    }
                    if (hm_b_n.containsKey(concepts[1])){
                        hm_b_n.put(concepts[1], hm_b_n.get(concepts[1]) + " ||| " + concepts[0]);
                    }else{
                        hm_b_n.put(concepts[1],concepts[0]);
                    }
                    //hm_n_b.put(concepts[0], concepts[1]);
                    //hm_b_n.put(concepts[1], concepts[0]);
                }else{
                    System.out.println(tempString);
                }
                //line++;
            }
            System.out.println("skos data loaded to hashmap at " + new Date( ));
            //start searching
            //String query = "Category:Semantic_Web";
            //searchAllSubs("Category:Machine_learning",hm_b_n);
            //searchAllSubs("Category:Semantic_Web",hm_b_n);
            
            //searchAllGraph("Category:Machine_learning",hm_n_b,hm_b_n,"machine_learning_tree_wiki_cat.txt");
            //searchAllGraph("Category:Semantic_Web",hm_n_b,hm_b_n,"semantic_web_tree_wiki_cat.txt");
            
            //searchAllSubs("Category:Machine_learning",hm_b_n,"machine_learning_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Semantic_Web",hm_b_n,"semantic_web_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Internet_of_Things",hm_b_n,"internet_of_things_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Natural_language_processing",hm_b_n,"natural_language_processing_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Social_information_processing",hm_b_n,"social_information_processing_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Data_mining",hm_b_n,"data_mining_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Information_science",hm_b_n,"information_science_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Library_science",hm_b_n,"library_science_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Artificial_neural_networks",hm_b_n,"neural_network_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Big_data",hm_b_n,"big_data_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Information_retrieval",hm_b_n,"information_retrieval_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Human–computer_interaction",hm_b_n,"human_computer_interaction_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Chemistry",hm_b_n,"chemistry_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Genetics",hm_b_n,"genetics_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Computational_chemistry",hm_b_n,"computational_chemistry_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Knowledge_management",hm_b_n,"knowledge_management_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Social_networks",hm_b_n,"social_networks_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:User_interfaces",hm_b_n,"user_interfaces_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Algorithms",hm_b_n,"algorithms_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Databases",hm_b_n,"databases_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:E-Learning",hm_b_n,"e_learning_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Web_development",hm_b_n,"web_development_sub_tree_wiki_cat.txt");
            //searchAllSubs("Category:Software_engineering",hm_b_n,"software_engineering_sub_tree_wiki_cat.txt");
            searchAllSubs("Category:Mathematics",hm_b_n,"mathematics_sub_tree_wiki_cat.txt");
//            System.out.println(hm_n_b.get(query));
//            if (hm_b_n.containsKey(query)){
//                String result_1 = hm_b_n.get(query);
//                System.out.println("level 2: " +  result_1);
//                String [] queries_2 = result_1.split(" \\|\\|\\| ");
//                for (String query_2 : queries_2){
//                    System.out.println("\tchecking: " + query_2);
//                    if (hm_b_n.containsKey(query_2)){
//                        String result_2 = hm_b_n.get(query_2);
//                        System.out.println("\tlevel 3 for " + query_2 + ": " + result_2);
//                        String [] queries_3 = result_2.split(" \\|\\|\\| ");
//                        for (String query_3 : queries_3){
//                            if (hm_b_n.containsKey(query_3)){
//                                String result_3 = hm_b_n.get(query_3);
//                                System.out.println("\t\tlevel 4 for " + query_3 + ": " + result_3);
//                                String [] queries_4 = result_3.split(" \\|\\|\\| ");
//                                if (queries_4.length != 0){
//                                    for (String query_4: queries_4){
//                                        if (hm_b_n.containsKey(query_4)){
//                                            String result_4 = hm_b_n.get(query_4);
//                                            System.out.println("\t\t\tlevel 5 for " + query_4 + ": " + result_4);
//                                            String [] queries_5 = result_4.split(" \\|\\|\\| ");
//                                            if (queries_5.length != 0){
//                                                for (String query_5: queries_5){
//                                                    if (hm_b_n.containsKey(query_5)){
//                                                        String result_5 = hm_b_n.get(query_5);
//                                                        System.out.println("\t\t\tlevel 6 for " + query_5 + ": " + result_5);
//                                                        String [] queries_6 = result_5.split(" \\|\\|\\| ");
//                                                        if (queries_6.length != 0){
//                                                            System.out.println("need next level");
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readers != null) {
                try {
                    readers.close();
                } catch (IOException e1) {
                }
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
    
    public static void searchAllSubs(String query, HashMap<String,String> hm_b_n, String OutputFileName){
        File fileW = new File(OutputFileName);
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(fileW));
            if (hm_b_n.containsKey(query)){
                String result_1 = hm_b_n.get(query);
                System.out.println("\t\tlevel 2: " +  result_1);
                String [] queries_2 = result_1.split(" \\|\\|\\| ");
                for (String query_2 : queries_2){
                    System.out.println(query_2 + " <- " + query);
                    writer.write(query_2 + " <- " + query);
                    writer.newLine();
                    
                    System.out.println("\tchecking: " + query_2);
                    if (hm_b_n.containsKey(query_2)){
                        String result_2 = hm_b_n.get(query_2);
                        System.out.println("\t\t\tlevel 3 for " + query_2 + ": " + result_2);
                        String [] queries_3 = result_2.split(" \\|\\|\\| ");
                        for (String query_3 : queries_3){
                            System.out.println(query_3 + " <- " + query_2);
                            writer.write(query_3 + " <- " + query_2);
                            writer.newLine();
                            if (hm_b_n.containsKey(query_3)){
                                String result_3 = hm_b_n.get(query_3);
                                System.out.println("\t\t\t\tlevel 4 for " + query_3 + ": " + result_3);
                                String [] queries_4 = result_3.split(" \\|\\|\\| ");
                                if (queries_4.length != 0){
                                    // get level 4
                                    for (String query_4: queries_4){
                                        System.out.println(query_4 + " <- " + query_3);
                                        writer.write(query_4 + " <- " + query_3);
                                        writer.newLine();
                                        if (hm_b_n.containsKey(query_4)){
                                            String result_4 = hm_b_n.get(query_4);
                                            System.out.println("\t\t\t\t\tlevel 5 for " + query_4 + ": " + result_4);
                                            String [] queries_5 = result_4.split(" \\|\\|\\| ");
                                            if (queries_5.length != 0){
                                                //get level 5
                                                for (String query_5: queries_5){
                                                    System.out.println(query_5 + " <- " + query_4);
                                                    writer.write(query_5 + " <- " + query_4);
                                                    writer.newLine();
                                                    if (hm_b_n.containsKey(query_5)){
                                                        String result_5 = hm_b_n.get(query_5);
                                                        System.out.println("\t\t\t\t\t\tlevel 6 for " + query_5 + ": " + result_5);
                                                        String [] queries_6 = result_5.split(" \\|\\|\\| ");
                                                        if (queries_6.length != 0){
                                                            System.out.println("need next level");
                                                            
//                                                            for (String query_6: queries_6){
//                                                                System.out.println(query_6 + " <- " + query_5);
//                                                                writer.write(query_6 + " <- " + query_5);
//                                                                writer.newLine();
//                                                                if (hm_b_n.containsKey(query_6)){
//                                                                    String result_6 = hm_b_n.get(query_6);
//                                                                    System.out.println("\t\t\t\t\t\t\tlevel 7 for " + query_6 + ": " + result_6);
//                                                                    String [] queries_7 = result_6.split(" \\|\\|\\| ");
//                                                                    if (queries_7.length != 0){
//                                                                        for (String query_7: queries_7){
//                                                                            System.out.println(query_7 + " <- " + query_6);
//                                                                            writer.write(query_7 + " <- " + query_6);
//                                                                            writer.newLine();
//                                                                            if (hm_b_n.containsKey(query_7)){
//                                                                                String result_7 = hm_b_n.get(query_7);
//                                                                                System.out.println("\t\t\t\t\t\t\t\tlevel 8 for " + query_7 + ": " + result_7);
//                                                                                String [] queries_8 = result_7.split(" \\|\\|\\| ");
//                                                                                if (queries_8.length != 0){
//                                                                                    for (String query_8: queries_8){
//                                                                                        System.out.println(query_8 + " <- " + query_7);
//                                                                                        writer.write(query_8 + " <- " + query_7);
//                                                                                        writer.newLine();
//                                                                                        if (hm_b_n.containsKey(query_8)){
//                                                                                            String result_8 = hm_b_n.get(query_8);
//                                                                                            System.out.println("\t\t\t\t\t\t\t\t\tlevel 9 for " + query_8 + ": " + result_8);
//                                                                                            String [] queries_9 = result_8.split(" \\|\\|\\| ");
//                                                                                            if (queries_9.length != 0){
//                                                                                                for (String query_9: queries_9){
//                                                                                                    System.out.println(query_9 + " <- " + query_8);
//                                                                                                    writer.write(query_9 + " <- " + query_8);
//                                                                                                    writer.newLine();
//                                                                                                    if (hm_b_n.containsKey(query_9)){
//                                                                                                        String result_9 = hm_b_n.get(query_9);
//                                                                                                        System.out.println("\t\t\t\t\t\t\t\t\t\tlevel 10 for " + query_9 + ": " + result_9);
//                                                                                                        String [] queries_10 = result_9.split(" \\|\\|\\| ");
//                                                                                                        if (queries_10.length != 0){
//                                                                                                            for (String query_10: queries_10){
//                                                                                                                System.out.println(query_10 + " <- " + query_9);
//                                                                                                                writer.write(query_10 + " <- " + query_9);
//                                                                                                                writer.newLine();
//                                                                                                                if (hm_b_n.containsKey(query_10)){
//                                                                                                                    String result_10 = hm_b_n.get(query_10);
//                                                                                                                    System.out.println("\t\t\t\t\t\t\t\t\t\t\tlevel 11 for " + query_10 + ": " + result_10);
//                                                                                                                    String [] queries_11 = result_10.split(" \\|\\|\\| ");
//                                                                                                                    if (queries_11.length != 0){
//                                                                                                                        for (String query_11: queries_11){
//                                                                                                                            System.out.println(query_11 + " <- " + query_10);
//                                                                                                                            writer.write(query_11 + " <- " + query_10);
//                                                                                                                            writer.newLine();
//                                                                                                                            if (hm_b_n.containsKey(query_11)){
//                                                                                                                                String result_11 = hm_b_n.get(query_11);
//                                                                                                                                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tlevel 12 for " + query_11 + ": " + result_11);
//                                                                                                                                String [] queries_12 = result_11.split(" \\|\\|\\| ");
//                                                                                                                                if (queries_12.length != 0){
//                                                                                                                                    for (String query_12: queries_12){
//                                                                                                                                        System.out.println(query_12 + " <- " + query_11);
//                                                                                                                                        writer.write(query_12 + " <- " + query_11);
//                                                                                                                                        writer.newLine();
//                                                                                                                                        if (hm_b_n.containsKey(query_12)){
//                                                                                                                                            String result_12 = hm_b_n.get(query_12);
//                                                                                                                                            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tlevel 13 for " + query_12 + ": " + result_12);
//                                                                                                                                            String [] queries_13 = result_12.split(" \\|\\|\\| ");
//                                                                                                                                            if (queries_13.length != 0){
//                                                                                                                                                for (String query_13: queries_13){
//                                                                                                                                                    System.out.println(query_13 + " <- " + query_12);
//                                                                                                                                                    writer.write(query_13 + " <- " + query_12);
//                                                                                                                                                    writer.newLine();
//                                                                                                                                                    if (hm_b_n.containsKey(query_13)){
//                                                                                                                                                        String result_13 = hm_b_n.get(query_13);
//                                                                                                                                                        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\tlevel 14 for " + query_13 + ": " + result_13);
//                                                                                                                                                        String [] queries_14 = result_13.split(" \\|\\|\\| ");
//                                                                                                                                                        if (queries_14.length != 0){
//                                                                                                                                                            System.out.println("need next level");
//                                                                                                                                                        }
//                                                                                                                                                    }
//                                                                                                                                                }
//                                                                                                                                            }
//                                                                                                                                        }
//                                                                                                                                    }
//                                                                                                                                }
//                                                                                                                            }
//                                                                                                                        }
//                                                                                                                    }
//                                                                                                                }
//                                                                                                            }
//                                                                                                        }
//                                                                                                    }
//                                                                                                }
//                                                                                            }
//                                                                                        }
//                                                                                    }
//                                                                                }
//                                                                            }
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            writer.flush();
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }    
    }
    
    // search all subsumptions from a starting concept, only concerns subsumptions.
    // only considering to 6 levels.
    public static void searchAllSubs(String query, HashMap<String,String> hm_b_n){
        if (hm_b_n.containsKey(query)){
            String result_1 = hm_b_n.get(query);
            System.out.println("level 2: " +  result_1);
            String [] queries_2 = result_1.split(" \\|\\|\\| ");
            for (String query_2 : queries_2){
                System.out.println("\tchecking: " + query_2);
                if (hm_b_n.containsKey(query_2)){
                    String result_2 = hm_b_n.get(query_2);
                    System.out.println("\tlevel 3 for " + query_2 + ": " + result_2);
                    String [] queries_3 = result_2.split(" \\|\\|\\| ");
                    for (String query_3 : queries_3){
                        if (hm_b_n.containsKey(query_3)){
                            String result_3 = hm_b_n.get(query_3);
                            System.out.println("\t\tlevel 4 for " + query_3 + ": " + result_3);
                            String [] queries_4 = result_3.split(" \\|\\|\\| ");
                            if (queries_4.length != 0){
                                for (String query_4: queries_4){
                                    if (hm_b_n.containsKey(query_4)){
                                        String result_4 = hm_b_n.get(query_4);
                                        System.out.println("\t\t\tlevel 5 for " + query_4 + ": " + result_4);
                                        String [] queries_5 = result_4.split(" \\|\\|\\| ");
                                        if (queries_5.length != 0){
                                            for (String query_5: queries_5){
                                                if (hm_b_n.containsKey(query_5)){
                                                    String result_5 = hm_b_n.get(query_5);
                                                    System.out.println("\t\t\tlevel 6 for " + query_5 + ": " + result_5);
                                                    String [] queries_6 = result_5.split(" \\|\\|\\| ");
                                                    if (queries_6.length != 0){
                                                        System.out.println("need next level");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    // search both subsumption and superordinates starting from a concept.
    // only considering one level of superordinates.
    public static void searchAllGraph(String query, HashMap<String,String> hm_n_b, HashMap<String,String> hm_b_n, String OutputFileName){
        File fileW = new File(OutputFileName);
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(fileW));
            if (hm_n_b.containsKey(query)){
                String result_0 = hm_n_b.get(query);
                System.out.println("level 0 for " + query + ": " + result_0);
                
                String [] outputset = result_0.split(" \\|\\|\\| ");
                for (String outputconcept: outputset){
                    System.out.println(query + " <- " + outputconcept);
                    writer.write(query + " <- " + outputconcept);
                    writer.newLine();
                }
            }
            if (hm_b_n.containsKey(query)){
                String result_1 = hm_b_n.get(query);
                System.out.println("\t\tlevel 2: " +  result_1);
                String [] queries_2 = result_1.split(" \\|\\|\\| ");
                for (String query_2 : queries_2){
                    System.out.println(query_2 + " <- " + query);
                    writer.write(query_2 + " <- " + query);
                    writer.newLine();
                    
                    System.out.println("\tchecking: " + query_2);
                    if (hm_n_b.containsKey(query_2)){
                        String result_1_0 = removeQueryFromDirectAncestor(hm_n_b.get(query_2),query); // the alternative direct ancester
                        if (!result_1_0.equals("")){
                            System.out.println("\tlevel 1_alt for " + query_2 + ": " + result_1_0);

                            String [] outputset = result_1_0.split(" \\|\\|\\| ");
                            for (String outputconcept: outputset){
                                System.out.println(query_2 + " <- " + outputconcept);
                                writer.write(query_2 + " <- " + outputconcept);
                                writer.newLine();
                            }
                        }
                    }
                    if (hm_b_n.containsKey(query_2)){
                        String result_2 = hm_b_n.get(query_2);
                        System.out.println("\t\t\tlevel 3 for " + query_2 + ": " + result_2);
                        String [] queries_3 = result_2.split(" \\|\\|\\| ");
                        for (String query_3 : queries_3){
                            System.out.println(query_3 + " <- " + query_2);
                            writer.write(query_3 + " <- " + query_2);
                            writer.newLine();
                            
                            if (hm_n_b.containsKey(query_3)){
                                String result_2_0 = removeQueryFromDirectAncestor(hm_n_b.get(query_3),query_2); // the alternative direct ancester
                                if (!result_2_0.equals("")){
                                    System.out.println("\t\tlevel 2_altfor " + query_3 + ": " + result_2_0);

                                    String [] outputset = result_2_0.split(" \\|\\|\\| ");
                                    for (String outputconcept: outputset){
                                        System.out.println(query_3 + " <- " + outputconcept);
                                        writer.write(query_3 + " <- " + outputconcept);
                                        writer.newLine();
                                    }
                                }
                            }
                            if (hm_b_n.containsKey(query_3)){
                                String result_3 = hm_b_n.get(query_3);
                                System.out.println("\t\t\t\tlevel 4 for " + query_3 + ": " + result_3);
                                String [] queries_4 = result_3.split(" \\|\\|\\| ");
                                if (queries_4.length != 0){
                                    for (String query_4: queries_4){
                                        System.out.println(query_4 + " <- " + query_3);
                                        writer.write(query_4 + " <- " + query_3);
                                        writer.newLine();
                                        
                                        if (hm_n_b.containsKey(query_4)){
                                            String result_3_0 = removeQueryFromDirectAncestor(hm_n_b.get(query_4),query_3); // the alternative direct ancester
                                            if (!result_3_0.equals("")){
                                                System.out.println("\t\t\tlevel 3_alt for " + query_4 + ": " + result_3_0);

                                                String [] outputset = result_3_0.split(" \\|\\|\\| ");
                                                for (String outputconcept: outputset){
                                                    System.out.println(query_4 + " <- " + outputconcept);
                                                    writer.write(query_4 + " <- " + outputconcept);
                                                    writer.newLine();
                                                }
                                            }
                                        }
                                        if (hm_b_n.containsKey(query_4)){
                                            String result_4 = hm_b_n.get(query_4);
                                            System.out.println("\t\t\t\t\tlevel 5 for " + query_4 + ": " + result_4);
                                            String [] queries_5 = result_4.split(" \\|\\|\\| ");
                                            if (queries_5.length != 0){
                                                for (String query_5: queries_5){
                                                    System.out.println(query_5 + " <- " + query_4);
                                                    writer.write(query_5 + " <- " + query_4);
                                                    writer.newLine();
                                                    
                                                    if (hm_n_b.containsKey(query_5)){
                                                        String result_4_0 = removeQueryFromDirectAncestor(hm_n_b.get(query_5),query_4); // the alternative direct ancester
                                                        if (!result_4_0.equals("")){
                                                            System.out.println("\t\t\t\tlevel 4_alt for " + query_5 + ": " + result_4_0);

                                                            String [] outputset = result_4_0.split(" \\|\\|\\| ");
                                                            for (String outputconcept: outputset){
                                                                System.out.println(query_5 + " <- " + outputconcept);
                                                                writer.write(query_5 + " <- " + outputconcept);
                                                                writer.newLine();
                                                            }
                                                        }
                                                    }
                                                    if (hm_b_n.containsKey(query_5)){
                                                        String result_5 = hm_b_n.get(query_5);
                                                        System.out.println("\t\t\t\t\t\tlevel 6 for " + query_5 + ": " + result_5);
                                                        String [] queries_6 = result_5.split(" \\|\\|\\| ");
                                                        if (queries_6.length != 0){
                                                            System.out.println("need next level");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            writer.flush();
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }    
    }
    
    public static void searchAllGraph(String query, HashMap<String,String> hm_n_b, HashMap<String,String> hm_b_n){
        if (hm_n_b.containsKey(query)){
            String result_0 = hm_n_b.get(query);
            System.out.println("level 0 for " + query + ": " + result_0);
        }
        if (hm_b_n.containsKey(query)){
            String result_1 = hm_b_n.get(query);
            System.out.println("\t\tlevel 2: " +  result_1);
            String [] queries_2 = result_1.split(" \\|\\|\\| ");
            for (String query_2 : queries_2){
                System.out.println("\tchecking: " + query_2);
                if (hm_n_b.containsKey(query_2)){
                    String result_1_0 = hm_n_b.get(query_2); // the alternative direct ancester
                    System.out.println("\tlevel 1_alt for " + query_2 + ": " + result_1_0);
                }
                if (hm_b_n.containsKey(query_2)){
                    String result_2 = hm_b_n.get(query_2);
                    System.out.println("\t\t\tlevel 3 for " + query_2 + ": " + result_2);
                    String [] queries_3 = result_2.split(" \\|\\|\\| ");
                    for (String query_3 : queries_3){
                        if (hm_n_b.containsKey(query_3)){
                            String result_2_0 = hm_n_b.get(query_3); // the alternative direct ancester
                            System.out.println("\t\tlevel 2_altfor " + query_3 + ": " + result_2_0);
                        }
                        if (hm_b_n.containsKey(query_3)){
                            String result_3 = hm_b_n.get(query_3);
                            System.out.println("\t\t\t\tlevel 4 for " + query_3 + ": " + result_3);
                            String [] queries_4 = result_3.split(" \\|\\|\\| ");
                            if (queries_4.length != 0){
                                for (String query_4: queries_4){
                                    if (hm_n_b.containsKey(query_4)){
                                        String result_3_0 = hm_n_b.get(query_4); // the alternative direct ancester
                                        System.out.println("\t\t\tlevel 3_alt for " + query_4 + ": " + result_3_0);
                                    }
                                    if (hm_b_n.containsKey(query_4)){
                                        String result_4 = hm_b_n.get(query_4);
                                        System.out.println("\t\t\t\t\tlevel 5 for " + query_4 + ": " + result_4);
                                        String [] queries_5 = result_4.split(" \\|\\|\\| ");
                                        if (queries_5.length != 0){
                                            for (String query_5: queries_5){
                                                if (hm_n_b.containsKey(query_5)){
                                                    String result_4_0 = hm_n_b.get(query_5); // the alternative direct ancester
                                                    System.out.println("\t\t\t\tlevel 4_alt for " + query_5 + ": " + result_4_0);
                                                }
                                                if (hm_b_n.containsKey(query_5)){
                                                    String result_5 = hm_b_n.get(query_5);
                                                    System.out.println("\t\t\t\t\t\tlevel 6 for " + query_5 + ": " + result_5);
                                                    String [] queries_6 = result_5.split(" \\|\\|\\| ");
                                                    if (queries_6.length != 0){
                                                        System.out.println("need next level");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static String removeQueryFromDirectAncestor(String result, String query){
        String [] results = result.split(" \\|\\|\\| ");
        String result_clean = "";
        for (String concept: results){
            if (!concept.equals(query)){
                if (!result_clean.equals("")){
                    result_clean = result_clean + " ||| " + concept;
                }else{
                    result_clean = concept;
                }
            }
        }
        return result_clean;
    }
}