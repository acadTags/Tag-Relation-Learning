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

/** This is to create a redirect file for SKOS concepts from page-redirects for DBpedia pages.
 *
 * @author cleme
 */
public class mapPageRedirects2SKOSconcepts {
    public static void main(String [] args){
        String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction";
        
        File file = new File(path + "\\" + "all_skos_data_dbpedia.txt");
        File file_redir = new File(path + "\\" + "all_page_redirect_data_dbpedia_url_name.txt");
        File fileW = new File(path + "\\" + "skos_redir_from_page_redir.txt");
        BufferedReader reader = null;
        BufferedReader reader_redir = null;
        BufferedWriter writer = null;
        String tempString;

        HashMap <String,Integer> hm = new HashMap<String,Integer>();
        HashMap <String,Integer> hm_redir_relations = new HashMap<String,Integer>();
        try {
            reader = new BufferedReader(new FileReader(file));
            reader_redir = new BufferedReader(new FileReader(file_redir));
            writer = new BufferedWriter(new FileWriter(fileW));
            
            int line = 0;
            
            while ((tempString = reader.readLine()) != null) {
                String [] concepts = tempString.split(" < ");
                if (concepts.length == 2){
                    hm.put(concepts[0],1);
                    hm.put(concepts[1],1);
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
                line++;
            }
            
            while ((tempString = reader_redir.readLine()) != null) {
                String [] concepts = tempString.split(" = ");
                if (concepts.length == 2){
                    if (hm.containsKey(concepts[0])){
                        if (hm.containsKey(concepts[1])){
                            // this means that both the redirected page and redirect page have a same name as a SKOS category.
                            // we will not deal with this issue, as it is complex here and it will not affect much final results.
                            
                            // we keep using the original SKOS label same as the tag, i.e. not using the redirect relation.
                            //System.out.println(tempString);
                        }else{
                            hm_redir_relations.put(concepts[1] + " = " + concepts[0], 1);
                            //writer.write(concepts[1] + " = " + concepts[0]);
                            //writer.newLine();
                        }
                    }else{
                        if (hm.containsKey(concepts[1])){
                            hm_redir_relations.put(tempString, 1);
                            //writer.write(tempString);
                            //writer.newLine();
                        }
                    }
                }else{
                    System.out.println("[wrong format]" + tempString);
                }
            }
            Set<Map.Entry<String, Integer>> set = hm_redir_relations.entrySet();
            for(Map.Entry<String, Integer> en: set){
                writer.write(en.getKey());
                writer.newLine();
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

