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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/** select the root common concepts which are in the k highest level of a KB tree.
 * output: a file containing, "tag,preferred/redirected-form-in-KB".
 * for most cases of DBpedia matched-form-in-KB is same as preferred/redirected-form-in-KB.
 * for most cases of ACM matched-form-in-KB is not preferred/redirected-form-in-KB.
 * for all cases of MCG matched-form-in-KB is preferred/redirected-form-in-KB.
 * @author cleme
 */
public class SelectRootsForEvaluation {
    public static void main(String [] args){
        //for ACM
        String KB = "äcm_preflabel_hierarchy.txt";
        String splitcharKB = " <- ";
        String KB_equ = "acm_data_equivalence_new.txt";
        String splitcharKB_equ = " = ";
        String tag_concept_matching = "bibsonomy-tag-acm-concpet-ed0.txt";
        int level = 5;
        boolean remove_leaf = true;
        String outputFileName = "roots-to-predict-acm-" + level + "-no-leaf.txt";
        
//        //for DBpedia
////        String KB = "all_skos_data_dbpedia.txt";
////        String splitcharKB = " < ";
////        String KB_equ = "skos_redir_from_page_redir.txt";
////        String splitcharKB_equ = " = ";
////        String tag_concept_matching = "bibsonomy-tag-skos-concept-ed0-new.txt";
////        int level = 5;
        
        //for DBpedia-CS_IS
//        String KB = "CS_IS_subKB_10.txt";
//        String splitcharKB = " <- ";
//        String KB_equ = "skos_redir_from_page_redir.txt";
//        String splitcharKB_equ = " = ";
//        String tag_concept_matching = "bibsonomy-tag-skos-concept-ed0-CS_IS.txt";
//        int level = 5;
//        boolean remove_leaf = false; //we did not remove the leaf concepts in the chose roots for CS-IS-5 in our experiment.
//        String outputFileName = "roots-to-predict-dbpedia-CS-IS-5-again.txt";
        
        //for DBpedia-edu
//        String KB = "edu_new_subKB_10.txt";
//        String splitcharKB = " <- ";
//        String KB_equ = "skos_redir_from_page_redir.txt";
//        String splitcharKB_equ = " = ";
//        String tag_concept_matching = "bibsonomy-tag-skos-concept-ed0-edu-disjoint.txt";
//        int level = 3;
//        boolean remove_leaf = false;
//        String outputFileName = "roots-to-predict-dbpedia-edu-" + level + ".txt";

        //for DBpedia-eco
//        String KB = "eco_new_subKB_10.txt";
//        String splitcharKB = " <- ";
//        String KB_equ = "skos_redir_from_page_redir.txt";
//        String splitcharKB_equ = " = ";
//        String tag_concept_matching = "bibsonomy-tag-skos-concept-ed0-eco-disjoint-all.txt";
//        int level = 3;
//        boolean remove_leaf = false;
//        String outputFileName = "roots-to-predict-dbpedia-eco-3.txt";
        
        //for MCG5 [not used]
//        String KB = "MCG5_cleaned.txt";
//        String splitcharKB = " <- ";
//        String KB_equ = "";
//        String splitcharKB_equ = " = ";
//        String tag_concept_matching = "bibsonomy-tag-mcg-concpet-ed0.txt";
//        int level = 2;
//        String outputFileName = "roots-to-predict-MCG-" + level + ".txt";
        
        File fileW = new File(outputFileName);
        
        HashMap<String,String> hm_b_a = getOntology_b_a(KB,splitcharKB);
        HashMap<String,String> hm_a_b = getOntology_a_b(KB,splitcharKB);
        HashMap<String,String> hm_equ;
        if (KB_equ.equals("")){
            hm_equ = new HashMap<String,String>();
        }else{
            hm_equ = getOntology_Equal(KB_equ,splitcharKB_equ);
        }    
        //String path1 = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\mallet-2.0.8";
        String path2 = "D:\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction";
        
        File filetag = new File(path2 + "\\" + tag_concept_matching);
        //File fileequ = new File(path2 + "\\" + "acm_data_equivalence_new.txt");
        //File fileKB = new File(path2 + "\\" + "äcm_preflabel_hierarchy.txt");
        //File fileWroots = new File(path2 + "\\" + "selected-roots-acm.txt");
        //File fileWleaves = new File(path2 + "\\" + "selected-leaves-acm.txt");
        
        BufferedReader readertags = null;
        BufferedWriter writerroots = null;
        //BufferedReader readerequ = null;
        //BufferedReader readerKB = null;
        //BufferedWriter writerroots = null;
        //BufferedWriter writerleaves = null;
        String tempString;
        String concept;
        String root_of_root;
        //String matched;
        //String [] concepts;
        //String [] concepts_raw;
        //int dist;
        //HashMap <String,String> hm = new HashMap<String,String>();
        HashMap <String,String> hm_concept_tag = new HashMap<String,String>();
        //HashMap <String,String> hm = new HashMap<String,String>();
        //boolean contain;
        //int max_len=0;
        try {
            readertags = new BufferedReader(new FileReader(filetag));
            writerroots = new BufferedWriter(new FileWriter(fileW));
            //readerequ = new BufferedReader(new FileReader(fileequ));
            //readerKB = new BufferedReader(new FileReader(fileKB));
            //writerroots = new BufferedWriter(new FileWriter(fileWroots));
            //writerleaves = new BufferedWriter(new FileWriter(fileWleaves));
            
            // store concept-tag key-value pair to hm_concept_tag.
            while ((tempString = readertags.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    // get the redirected/preferred label of this concept.
                    if (hm_equ.containsKey(arr[1])){
                        concept = hm_equ.get(arr[1]);
                    }else{
                        concept = arr[1];
                    }
                    hm_concept_tag.put(concept, arr[0]);
                }else{
                    //System.out.println(tempString);
                }
            }
            readertags.close();
            
            readertags = new BufferedReader(new FileReader(filetag));
            while ((tempString = readertags.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    // get the redirected/preferred label of this concept.
                    if (hm_equ.containsKey(arr[1])){
                        concept = hm_equ.get(arr[1]);
                    }else{
                        concept = arr[1];
                    }
                    // arr[0] is the tag concept, 
                    // arr[1] is either the preferred or an equivalent concept in the GSO, 
                    // concept is the preferred label of this concept in the GSO.
                    
                    if (isLeaf(hm_b_a, concept) && remove_leaf){
                        System.out.println(tempString + "," + concept);
                    }else{
                        if (isRoot(hm_a_b, concept,level)){ //if the concept is within a certain level of the gold standard ontology.
                            //System.out.println("\t" + tempString + "," + concept);
                            root_of_root = labelRootofRoot(hm_a_b, concept, hm_concept_tag); 
                            // get the hypernym of the root concept if it is a matched concept to tags, otherwise use the same concept as the root concept.
                            
                            if (root_of_root == null){
                                //System.out.println(concept);
                            }else if (root_of_root.equals("")){    
                                writerroots.write(arr[0] + "," + arr[0]); 
                                writerroots.newLine();
                            }else{
                                writerroots.write(arr[0] + "," + root_of_root);
                                writerroots.newLine();
                            }
                        }
                    }
                }else{
                    //System.out.println(tempString);
                }    
            }
            writerroots.flush();
            writerroots.close();
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
    }
    
    // get the direct common root of this concept (the selected "root") in the KB, if there is one.
    // if there is not one, then output null.
    static String labelRootofRoot(HashMap<String,String> hm_a_b, String root, HashMap<String,String> hm_concept_tag){
        if (hm_a_b.containsKey(root)){
            String [] hypernyms = hm_a_b.get(root).split(" \\|\\|\\|");
            for (String hypernym: hypernyms){
                if (hm_concept_tag.containsKey(hypernym)){
                    return hm_concept_tag.get(hypernym); // the root of root is available.
                    //only use the first root of root if there are more than 1.
                }
            }
            return ""; // there is no root of root in the KB that is common to the matched tags.
        }else{
            return null; // the input root is a top root in the KB, or is not found in the KB.
        }    
    }
    
    // [not used] the input file should be in the project folder.
    static HashMap<String,Integer> getHmKey(String tag_concept_matching){
        File filetag = new File(tag_concept_matching);
        BufferedReader readertags = null;
        String tempString;
        HashMap <String,Integer> hm = new HashMap<String,Integer>();
        try {
            readertags = new BufferedReader(new FileReader(filetag));
            while ((tempString = readertags.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    hm.put(arr[0], 1);
                }
            }
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
        return null;
    }
    
    // [not used] the input file should be in the project folder.
    static HashMap<String,String> getHm(String tag_concept_matching){
        File filetag = new File(tag_concept_matching);
        BufferedReader readertags = null;
        String tempString;
        HashMap <String,String> hm = new HashMap<String,String>();
        try {
            readertags = new BufferedReader(new FileReader(filetag));
            while ((tempString = readertags.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    hm.put(arr[0], arr[1]);
                }
            }
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
        return null;
    }
    
    // [not used] the input file should be in the project folder.
    static HashMap<String,String> getHmValue(String tag_concept_matching){
        File filetag = new File(tag_concept_matching);
        BufferedReader readertags = null;
        String tempString;
        HashMap <String,String> hm = new HashMap<String,String>();
        try {
            readertags = new BufferedReader(new FileReader(filetag));
            while ((tempString = readertags.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    hm.put(arr[1], arr[0]);
                }
            }
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
        return null;
    }
    
    // level means the concept is within k-th levels of KB.
    static boolean isRoot(HashMap<String,String> hm_a_b, String concept, int level){
        //System.out.println(concept + "," + level);
        if (level<=0){
            return false;
        }
        boolean isRoot = false;
        if (!hm_a_b.containsKey(concept)){
            // does not have a hypernym, the highest root.
            if (level>0){
                isRoot = true;
            }else{
                isRoot = false;
            }
        }else{
            // has a hypernym.
            String [] hypernyms = hm_a_b.get(concept).split(" \\|\\|\\| ");
            if (hypernyms.length == 1){
                isRoot = isRoot(hm_a_b,hypernyms[0],level-1);
            }else{
                //System.out.println(hm_a_b.get(concept));
                for (String hypernym: hypernyms){
                    isRoot = isRoot || isRoot(hm_a_b,hypernym,level-1);
                }
            }
        }
        return isRoot;
    }
    
    //whether the concept is a leaf node in the ontology.
    static boolean isLeaf(HashMap<String,String> hm_b_a, String concept){
        if (!hm_b_a.containsKey(concept)){
            // does not have a hyponym, then is a leaf.
            return true;
        }else{
            return false;
        }
    }
    
    static HashMap<String,String> getOntology_b_a(String inputOntologyFileName, String splitchar){
        HashMap<String, String> hm_b_a = new HashMap<String, String>(); // storing b -> a_c_d, where _ means a white space
        
        File fileO = new File(inputOntologyFileName); // Ontology
        BufferedReader readerO = null;
        try {
            readerO = new BufferedReader(new FileReader(fileO));
            String tempString = null;
            
            // storing a <- b_c_d, where _ means a white space, to the hashmap hm_a_b
            // storing b -> a_c_d, where _ means a white space, to the hashmap hm_b_a
            while ((tempString = readerO.readLine()) != null) {
                //tempString = tempString.replace("_", " ");
                tempString = tempString.toLowerCase();
                //tempString = removeSingleQuotesInConceptPair(tempString, splitchar);
                String [] concepts = tempString.split(splitchar);
                
                if (concepts.length == 2){
                    if (hm_b_a.containsKey(concepts[1])){
                        hm_b_a.put(concepts[1], hm_b_a.get(concepts[1]) + " ||| " + concepts[0]);
                    }else{
                        hm_b_a.put(concepts[1], concepts[0]);
                    }
                }else{
                    // case of "," at the tail of a tag.
                    if (concepts.length == 3){
                        if (hm_b_a.containsKey(concepts[2])){
                            hm_b_a.put(concepts[2], hm_b_a.get(concepts[2]) + " ||| " + concepts[0]);
                        }else{
                            hm_b_a.put(concepts[2], concepts[0]);
                        }
                    }else{
                        System.out.println(tempString);
                    }    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readerO != null) {
                try {
                    readerO.close();
                } catch (IOException e1) {
                }
            }
        }
        
        return hm_b_a;
    }
    
    static HashMap<String,String> getOntology_Equal(String inputEqualList, String splitchar){
        HashMap<String, String> hm_equ = new HashMap<String, String>();
        
        File fileO = new File(inputEqualList); // Ontology
        BufferedReader readerO = null;
        try {
            readerO = new BufferedReader(new FileReader(fileO));
            String tempString = null;
            
            // storing a <- b_c_d, where _ means a white space, to the hashmap hm_a_b
            // storing b -> a_c_d, where _ means a white space, to the hashmap hm_b_a
            while ((tempString = readerO.readLine()) != null) {
                //tempString = tempString.replace("_", " ");
                tempString = tempString.toLowerCase();
                //tempString = removeSingleQuotesInConceptPair(tempString, splitchar);
                String [] concepts = tempString.split(splitchar);
                
                if (concepts.length == 2){
                    //if (hm_commonconcepts.containsKey(concepts[0])){
                        hm_equ.put(concepts[0], concepts[1]);
                    //}    
                }else{
                    //System.out.println(tempString);                        
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readerO != null) {
                try {
                    readerO.close();
                } catch (IOException e1) {
                }
            }
        }
        
        return hm_equ;
        
    }
    
    static HashMap<String,String> getOntology_a_b(String inputOntologyFileName, String splitchar){
        HashMap<String, String> hm_a_b = new HashMap<String, String>(); // storing a <- b_c_d, where _ means a white space
        
        File fileO = new File(inputOntologyFileName); // Ontology
        BufferedReader readerO = null;
        try {
            readerO = new BufferedReader(new FileReader(fileO));
            String tempString = null;

            // storing a <- b_c_d, where _ means a white space, to the hashmap hm_a_b
            // storing b -> a_c_d, where _ means a white space, to the hashmap hm_b_a
            while ((tempString = readerO.readLine()) != null) {
                //tempString = removeSingleQuotesInConceptPair(tempString, splitchar);
                //tempString = tempString.replace("_", " ");
                tempString = tempString.toLowerCase();
                String [] concepts = tempString.split(splitchar);
                
                if (concepts.length == 2){
                    if (hm_a_b.containsKey(concepts[0])){
                        hm_a_b.put(concepts[0], hm_a_b.get(concepts[0]) + " ||| " + concepts[1]);
                    }else{
                        hm_a_b.put(concepts[0], concepts[1]);
                    }
                }else{
                    // case of "," at the tail of a tag.
                    if (concepts.length == 3){
                        if (hm_a_b.containsKey(concepts[0])){
                            hm_a_b.put(concepts[0], hm_a_b.get(concepts[0]) + " ||| " + concepts[2]);
                        }else{
                            hm_a_b.put(concepts[0], concepts[2]);
                        }
                    }else{
                        System.out.println(tempString);
                    }    
                }
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readerO != null) {
                try {
                    readerO.close();
                } catch (IOException e1) {
                }
            }
        }
        
        return hm_a_b;
    }
}    
