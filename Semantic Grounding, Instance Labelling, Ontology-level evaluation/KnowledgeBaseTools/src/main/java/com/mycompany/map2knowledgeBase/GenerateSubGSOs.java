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

/** Generate sub-GSOs for ontology-level evaluation. 
 * [The program is not necessarily but useful. 
 It is same as setting the boolean parameter createSubKBfiles to true in TaxonomicCSCforPredictionSubKBfromFolder.java.]

 * This program will generate a list of sub-GSOs, 
 * each as a .txt file corresponding to each root concept in the root list.
 * @author cleme
 */
public class GenerateSubGSOs {
    public static void main(String [] args){
        //for ACM
        String KB = "äcm_preflabel_hierarchy.txt";
        String splitcharKB = " <- ";
        String KB_equ = "acm_data_equivalence_new.txt";
        String splitcharKB_equ = " = ";
        String tag_concept_matching = "bibsonomy-tag-acm-concpet-ed0.txt";
        
        String root_list = "roots-to-predict-acm-2.txt";
        String splitcharRoot_list = ",";
        
        //load gold standard ontology
        System.out.println("start loading ontology");
        HashMap<String,String> hm_b_a = getOntology_b_a(KB,splitcharKB);
        System.out.println("ontology loaded");
        
        //load equivalent relations
        HashMap<String,String> hm_equ;
        if (KB_equ.equals("")){
            hm_equ = new HashMap<String,String>();
        }else{
            hm_equ = getOntology_Equal(KB_equ,splitcharKB_equ);
        }
        
        // retrieve tag-concept matching: hm_tag_concept, from a tag to a preferred label in the GSO.
        BufferedReader readertags = null;
        HashMap <String,String> hm_tag_concept = new HashMap<String,String>();
        String tempString;
        String concept;
        try {
            readertags = new BufferedReader(new FileReader(tag_concept_matching));
            while ((tempString = readertags.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    // get the redirected/preferred label of this concept.
                    if (hm_equ.containsKey(arr[1])){
                        concept = hm_equ.get(arr[1]);
                    }else{
                        concept = arr[1];
                    }
                    hm_tag_concept.put(arr[0], concept);
                }else{
                    //System.out.println(tempString);
                }
            }
            readertags.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readertags != null) {
                try {
                    readertags.close();
                } catch (IOException e1) {
                }
            }
        }    
        
        // retrieve and loop over the root list
        BufferedReader readerroots = null;
        String domain_concept = "";
        try {
            readerroots = new BufferedReader(new FileReader(root_list));
            while ((tempString = readerroots.readLine()) != null) {
                String [] arr = tempString.split(splitcharRoot_list);
                if (hm_tag_concept.containsKey(arr[0])){
                    domain_concept = hm_tag_concept.get(arr[0]);
                    File fileWsubKB = new File(domain_concept + " " + KB + ".txt");
                    BufferedWriter writersubKB = new BufferedWriter(new FileWriter(fileWsubKB));
                    outputdomainKB(hm_b_a, domain_concept, writersubKB);
                    writersubKB.flush();
                    writersubKB.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readerroots != null) {
                try {
                    readerroots.close();
                } catch (IOException e1) {
                }
            }
        }        
    }
    
    static void outputdomainKB(HashMap<String,String> hm_b_a, String domain_concept, BufferedWriter writer) throws IOException{
        //domain_concept = domain_concept.toLowerCase();
        //HashMap<String,String> hm = hm_b_a;
        if (hm_b_a.containsKey(domain_concept)){
            String [] hyponyms = hm_b_a.get(domain_concept).split(" \\|\\|\\| ");
            hm_b_a.remove(domain_concept); // to prevent dead loop caused by a cycle structure.
            for (String hyponym: hyponyms){
                //System.out.println(domain_concept + "," + hyponym);
                writer.write(hyponym + " <- " + domain_concept);
                writer.newLine();
                outputdomainKB(hm_b_a,hyponym,writer);
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
