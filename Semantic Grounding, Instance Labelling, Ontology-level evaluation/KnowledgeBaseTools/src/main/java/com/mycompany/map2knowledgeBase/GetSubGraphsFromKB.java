/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.map2knowledgeBase;

import static com.mycompany.map2knowledgeBase.SelectRootsForEvaluation.getOntology_Equal;
import static com.mycompany.map2knowledgeBase.SelectRootsForEvaluation.getOntology_b_a;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/** get sub KB from a KB through input as a concept, 
 * output a taxonomic KB (each child has only one parent) in the domain of this concept. 
 * This program is before SelectRootsForEvaluation.
 * The program is only used for DBpedia and MCG5 (but in ontology-level evaluation we did not select MCG due to its low transitivity).
 * @author cleme
 */
public class GetSubGraphsFromKB {
    public static void main(String [] args){
        //String domain_concept = "areas of computer science"; // DBpedia
        //String domain_concept = "information science"; // DBpedia
        //String domain_concept = "education"; // DBpedia
        String domain_concept = "economics"; // DBpedia
        //String domain_concept = "information"; // MCG
//        //for ACM
//        //String KB = "äcm_preflabel_hierarchy.txt";
//        //String splitcharKB = " <- ";
//        //String KB_equ = "acm_data_equivalence_new.txt";
//        //String splitcharKB_equ = " = ";
        
        //for DBpedia
        String KB = "all_skos_data_dbpedia.txt";
        String splitcharKB = " < ";
        String KB_equ = "skos_redir_from_page_redir.txt";
        String splitcharKB_equ = " = ";
        int level = 10;
        
        //for MCG5
//        String KB = "MCG5_cleaned.txt";
//        String splitcharKB = " <- ";
//        String KB_equ = "";
//        String splitcharKB_equ = " = ";
//        int level = 3;
        
        System.out.println("start loading ontology");
        HashMap<String,String> hm_b_a = getOntology_b_a(KB,splitcharKB);
//        HashMap<String,String> hm_equ;
//        if (KB_equ.equals("")){
//            hm_equ = new HashMap<String,String>();
//        }else{
//            hm_equ = getOntology_Equal(KB_equ,splitcharKB_equ);
//        }
        
        System.out.println("ontology loaded");
        File fileWsubKB = new File(domain_concept + "_subKB_" + level + ".txt");
        BufferedWriter writersubKB = null;
        try {
            writersubKB = new BufferedWriter(new FileWriter(fileWsubKB));
            outputdomainKB(hm_b_a,domain_concept,level,writersubKB);
            writersubKB.flush();
            writersubKB.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    // depth-first search
    static void outputdomainKB(HashMap<String,String> hm_b_a, String domain_concept){
        //domain_concept = domain_concept.toLowerCase();
        if (hm_b_a.containsKey(domain_concept)){
            String [] hyponyms = hm_b_a.get(domain_concept).split(" \\|\\|\\| ");
            hm_b_a.remove(domain_concept); // to prevent dead loop caused by a cycle structure.
            for (String hyponym: hyponyms){
                System.out.println(domain_concept + "," + hyponym);
                outputdomainKB(hm_b_a,hyponym);
            }
        }else{
            
        }
    }
    
    static void outputdomainKB(HashMap<String,String> hm_b_a, String domain_concept, BufferedWriter writer) throws IOException{
        //domain_concept = domain_concept.toLowerCase();
        if (hm_b_a.containsKey(domain_concept)){
            String [] hyponyms = hm_b_a.get(domain_concept).split(" \\|\\|\\| ");
            hm_b_a.remove(domain_concept); // to prevent dead loop caused by a cycle structure.
            for (String hyponym: hyponyms){
                //System.out.println(domain_concept + "," + hyponym);
                writer.write(domain_concept + "," + hyponym);
                outputdomainKB(hm_b_a,hyponym,writer);
            }
        }else{
            
        }
    }
    
    // depth-first search with the level set.
    static void outputdomainKB(HashMap<String,String> hm_b_a, String domain_concept, int level){
        //domain_concept = domain_concept.toLowerCase();
        if (level<=0){
            return;
        }
        if (hm_b_a.containsKey(domain_concept)){
            String [] hyponyms = hm_b_a.get(domain_concept).split(" \\|\\|\\| ");
            hm_b_a.remove(domain_concept); // to prevent dead loop caused by a cycle structure.
            for (String hyponym: hyponyms){
                System.out.println(domain_concept + "," + hyponym + ","+level);
                outputdomainKB(hm_b_a,hyponym,level-1);
            }
        }else{
            
        }
    }
    
    static void outputdomainKB(HashMap<String,String> hm_b_a, String domain_concept, int level, BufferedWriter writer) throws IOException{
        //domain_concept = domain_concept.toLowerCase();
        if (level<=0){
            return;
        }
        if (hm_b_a.containsKey(domain_concept)){
            String [] hyponyms = hm_b_a.get(domain_concept).split(" \\|\\|\\| ");
            hm_b_a.remove(domain_concept); // to prevent dead loop caused by a cycle structure.
            for (String hyponym: hyponyms){
                //System.out.println(domain_concept + "," + hyponym + ","+level);
                //writer.write(domain_concept + "," + hyponym + ","+level);
                //writer.write(domain_concept + "," + hyponym);
                writer.write(hyponym + " <- " + domain_concept);
                writer.newLine();
                outputdomainKB(hm_b_a,hyponym,level-1,writer);
            }
        }else{
            
        }
    }
}    