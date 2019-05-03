
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

/** Query Knowledge Base to label tag pairs.
 * allowing the recognition of equiv and hierarchical relations from an external KB.
 * [changes in 18 Oct 2017] querying both KBs, DBpedia and ACM, to label tag pairs: labelled as true if one of the KBs labels it as true.
 * @author cleme
 */
public class QueryKB2Label {
    public static void main(String [] args){
    //three KBs are commonly used for labelling.
    
    // for dbpedia skos KB.
        //HashMap<String,String> hm_tag_concept_mapping_DBpedia = concept2hm("bibsonomy-tag-skos-concept-ed0.txt");
        HashMap<String,String> hm_tag_concept_mapping_DBpedia = concept2hm("bibsonomy-tag-skos-concept-ed0-new.txt","skos_redir_from_page_redir.txt");
        //HashMap<String,Integer> hm_kb_relations_DBpedia = KBrelation2hm("all_skos_data_dbpedia.txt");
        HashMap<String,Integer> hm_kb_relations_DBpedia = KBrelation2hm("all_skos_data_dbpedia_cleaned.txt");
        String splitchar_DBpedia = " < ";
        
        //System.out.println(hm_concept_tag_mapping.get("Science_Blogs"));
        //System.out.println(isHierarchical("","",hm_concept_tag_mapping,hm_kb_relations));
    
    // for ACM KB.
        HashMap<String,String> hm_tag_concept_mapping_ACM = concept2hm("bibsonomy-tag-acm-concpet-ed0.txt","acm_data_equivalence_new.txt");
        ////System.out.println(hm_tag_concept_mapping.get("wsdl"));
        // using the transitive closured ACM.
        //HashMap<String,Integer> hm_kb_relations_ACM = KBrelation2hm("acm_taxonomy_trans_closure.csv");
        // using the original ACM.
        HashMap<String,Integer> hm_kb_relations_ACM = KBrelation2hm("äcm_preflabel_hierarchy.txt");
        String splitchar_ACM = " <- ";
    
    // for MCG
        HashMap<String,String> hm_tag_concept_mapping_MCG = concept2hm("bibsonomy-tag-mcg-concpet-ed0.txt");
        HashMap<String,Integer> hm_kb_relations_MCG = KBrelation2hm("MCG5_cleaned.txt");
        String splitchar_MCG = " <- ";
        
    // input and output. [the only parameters to change]    
        // for bibsonomy-dbpedia matched concepts
        ////File file = new File("bibsonomy_skos_withredir_pw_candidts0.1.csv");
        ////File fileW = new File("bibsonomy_skos_withredir_pw_candidts0.1_labelled_hier.csv");
        ////File file = new File("bibsonomy_skos_withredir_pw_candidts_all.csv");
        ////File fileW = new File("bibsonomy_skos_withredir_pw_candidts_all_labelled.csv");
        //File file = new File("bibsonomy_skos_withredir_pw_candidts_asso0.001.csv");
        //File fileW = new File("bibsonomy_skos_withredir_pw_candidts_asso0.001_labelled.csv");
        //File file = new File("bibsonomy_skos_withredir_pw_candidts_asso0.002.csv");
        //File fileW = new File("bibsonomy_skos_withredir_pw_candidts_asso0.002_labelled.csv");
        
        // for bibsonomy-acm matched concepts
        ////File file = new File("bibsonomy_acm_pw_candidts0.1.csv");
        ////File fileW = new File("bibsonomy_acm_pw_candidts0.1_labelled_hier.csv");
        ////File file = new File("bibsonomy_acm_pw_candidts_all.csv");
        ////File fileW = new File("bibsonomy_acm_pw_candidts_all_labelled.csv");
        //File file = new File("bibsonomy_acm_pw_candidts_asso0.001.csv");
        //File fileW = new File("bibsonomy_acm_pw_candidts_asso0.001_labelled.csv");
        //File file = new File("bibsonomy_acm_pw_candidts_asso0.002.csv");
        //File fileW = new File("bibsonomy_acm_pw_candidts_asso0.002_labelled.csv");
        
        // for bibsonomy-mcg matched concepts
        ////File file = new File("bibsonomy_mcg5_pw_candidts0.1.csv");
        ////File fileW = new File("bibsonomy_mcg5_pw_candidts0.1_labelled_hier.csv");
        ////File file = new File("bibsonomy_mcg5_pw_candidts_all.csv");
        ////File fileW = new File("bibsonomy_mcg5_pw_candidts_all_labelled.csv");
        //File file = new File("bibsonomy_mcg5_pw_candidts_asso0.001.csv");
        //File fileW = new File("bibsonomy_mcg5_pw_candidts_asso0.001_labelled.csv");
        File file = new File("bibsonomy_mcg5_pw_candidts_asso0.002.csv");
        File fileW = new File("bibsonomy_mcg5_pw_candidts_asso0.002_labelled.csv");
        
        BufferedReader reader = null;
        BufferedWriter writer = null;
        String tempString;
        Boolean isHierarchical_DBpedia;
        
        Boolean isHierarchical_ACM;
        Boolean isHierarchical_MCG;
        Boolean isHierarchical = null;
        Boolean isEquivalent_DBpedia;
        Boolean isEquivalent_ACM;
        Boolean isHierOrEquiv;
        //HashMap<String,String> hm = new HashMap<String,String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(fileW));
            while ((tempString = reader.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length==2){
                    //System.out.println(arr[0]);
                    //System.out.println(arr[1]);
                    isHierarchical_DBpedia = isHierarchical(arr[0],arr[1],hm_tag_concept_mapping_DBpedia,hm_kb_relations_DBpedia, splitchar_DBpedia);
                    isHierarchical_ACM = isHierarchical(arr[0],arr[1],hm_tag_concept_mapping_ACM,hm_kb_relations_ACM, splitchar_ACM);
                    isHierarchical_MCG = isHierarchical(arr[0],arr[1],hm_tag_concept_mapping_MCG,hm_kb_relations_MCG, splitchar_MCG);
                            
                    if (isHierarchical_DBpedia == null){
                        if (isHierarchical_ACM == null){
                            isHierarchical = isHierarchical_MCG;
                        }else{
                            if (isHierarchical_MCG == null){
                                isHierarchical = isHierarchical_ACM;
                            }else{
                                isHierarchical = (isHierarchical_ACM || isHierarchical_MCG);
                            }
                        }
                    }else{
                        if (isHierarchical_ACM == null){
                            if (isHierarchical_MCG == null){
                                isHierarchical = isHierarchical_DBpedia;
                            }else{
                                isHierarchical = (isHierarchical_DBpedia || isHierarchical_MCG);
                            }
                        }else{
                            if (isHierarchical_MCG == null){
                                isHierarchical = (isHierarchical_DBpedia || isHierarchical_ACM);
                            }else{
                                isHierarchical = (isHierarchical_MCG || isHierarchical_DBpedia || isHierarchical_ACM);
                            }
                        }
                    }
//                    if (isHierarchical_ACM == null){
//                        isHierarchical = isHierarchical_DBpedia;
//                    }
//                    if (isHierarchical_DBpedia != null && isHierarchical_ACM != null){
//                        isHierarchical = (isHierarchical_DBpedia || isHierarchical_ACM);
//                    }
                    
                    isEquivalent_ACM = isEquivalent(arr[0],arr[1],hm_tag_concept_mapping_ACM);
                    //isHierOrEquiv = (isHierarchical || isEquivalent_ACM);
                    //System.out.println(isEquivalent_ACM.toString());
                    if (isEquivalent_ACM != null){
                        if (isEquivalent_ACM == true){
                            System.out.println(tempString);
                        }    
                    }
                    
                    isEquivalent_DBpedia = isEquivalent(arr[0],arr[1],hm_tag_concept_mapping_DBpedia);
                    
                    if (isEquivalent_DBpedia != null){
                        if (isEquivalent_DBpedia == true){
                            System.out.println(tempString);
                        }    
                    }
                    
                    if (isHierarchical != null){
                        if (isEquivalent_ACM != null){
                            isHierOrEquiv = (isHierarchical || isEquivalent_ACM);
                        }else{
                            isHierOrEquiv = isHierarchical;
                        }
                        
                        if (isEquivalent_DBpedia != null){
                            isHierOrEquiv = (isHierOrEquiv || isEquivalent_DBpedia);
                        }
                        
                        // show equivalent relation as bi-directional true;
                        //writer.write(tempString + "," + isHierOrEquiv.toString());
                        
                        // show hierarchical relation as true only [finally we stick to the simpler idea].
                        writer.write(tempString + "," + isHierarchical.toString());
                        writer.newLine();
                    }else{
                        System.out.println(tempString);
                    }    
                }else{
//                    if (arr.length == 3){
//                        isHierarchical = isHierarchical(arr[0] + "," + arr[1],arr[1],hm_concept_tag_mapping,hm_kb_relations);
//                        if (isHierarchical != null){
//                            writer.write(tempString + "," + isHierarchical.toString());
//                            writer.newLine();
//                        }else{
//                            System.out.println(tempString);
//                        }    
//                    }else{
                        System.out.println("[wrong format]" + tempString);
//                    }    
                }
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
    
    public static Boolean isHierarchical(String tag_narrower, String tag_broader, HashMap<String,String> hm_tag_concept_mapping, HashMap<String,Integer> hm_kb_relations, String splitchar){
        if (hm_tag_concept_mapping.containsKey(tag_narrower) && hm_tag_concept_mapping.containsKey(tag_broader)){
            String concept_n = hm_tag_concept_mapping.get(tag_narrower);
            String concept_b = hm_tag_concept_mapping.get(tag_broader);
            
            // A <- B, A is a hyponym of B.
            if (hm_kb_relations.containsKey(concept_n + splitchar + concept_b)){
                return true;
            }else{
                return false;                
            }
            
            //// support bi-direction.
            //if (hm_kb_relations.containsKey(concept_n + splitchar + concept_b) || hm_kb_relations.containsKey(concept_b + splitchar + concept_n)){
            //    return true;
            //}else{
            //    return false;
            //}
            
        }else{
            return null; //cannot decide;
        }
    }
    
    public static Boolean isEquivalent(String tag_left, String tag_right, HashMap<String,String> hm_tag_concept_mapping){
        if (!hm_tag_concept_mapping.containsKey(tag_left)){
            //System.out.println(tag_left);
            return null;
        }
        if (!hm_tag_concept_mapping.containsKey(tag_right)){
            //System.out.println(tag_right);
            return null;
        }
        return hm_tag_concept_mapping.get(tag_left).equals(hm_tag_concept_mapping.get(tag_right));
    }
    
    public static HashMap<String,String> concept2hm(String inputMappingFileName){
        File file = new File(inputMappingFileName);
        BufferedReader reader = null;
        String tempString;
        HashMap<String,String> hm = new HashMap<String,String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((tempString = reader.readLine()) != null) {
                //System.out.println(tempString);
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    //System.out.println(arr[0] + "," + arr[1]);
                    hm.put(arr[0],arr[1]);
                }else{
                    //System.out.println(tempString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            return hm; 
        }
    }
    
    // concept2hm: equivalence included
    public static HashMap<String,String> concept2hm(String inputMappingFileName, String inputEquivalenceFileName){
        File file = new File(inputMappingFileName);
        File file_equiv = new File(inputEquivalenceFileName);
        BufferedReader reader = null;
        BufferedReader reader_equiv = null;
        String tempString;
        HashMap<String,String> hm = new HashMap<String,String>();
        HashMap<String,String> hm_equiv = new HashMap<String,String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            reader_equiv = new BufferedReader(new FileReader(file_equiv));
            
//            while ((tempString = reader_equiv.readLine()) != null) {
//                //System.out.println(tempString);
//                String [] arr = tempString.split(" = ");
//                if (arr.length == 2){
//                    if (!hm_equiv.containsKey(arr[0])){
//                        hm_equiv.put(arr[0], arr[0] + " ||| " + arr[1]);
//                    }else{
//                        hm_equiv.put(arr[0], hm_equiv.get(arr[0]) + " ||| " + arr[1]);
//                    }    
//                }else{
//                    System.out.println(tempString);
//                }
//            }
//            reader_equiv.close();
//            
//            while ((tempString = reader.readLine()) != null) {
//                //System.out.println(tempString);
//                String [] arr = tempString.split(",");
//                if (arr.length == 2){
//                    //System.out.println(arr[0] + "," + arr[1]);
//                    //hm.put(arr[0],arr[1]);
//                    String [] arr_n = hm_equiv.get(arr[0]).split(" \\|\\|\\| ");
//                    String [] arr_b = hm_equiv.get(arr[1]).split(" \\|\\|\\| ");
//                    for (String con_n: arr_n){
//                        for (String con_b: arr_b){
//                            hm.put(con_n, con_b);
//                        }
//                    }
//                }else{
//                    System.out.println(tempString);
//                }
//            }

            while ((tempString = reader_equiv.readLine()) != null) {
                //System.out.println(tempString);
                String [] arr = tempString.split(" = ");
                if (arr.length == 2){
                    hm_equiv.put(arr[0].toLowerCase(), arr[1].toLowerCase());
                }else{
                    System.out.println(tempString);
                }
            }
            
            while ((tempString = reader.readLine()) != null) {
                //System.out.println(tempString);
                String [] arr = tempString.split(",");
                if (arr.length == 2){
                    hm.put(arr[0],arr[1]);
                    if (hm_equiv.containsKey(arr[1].toLowerCase())){
                        hm.put(arr[0], hm_equiv.get(arr[1].toLowerCase()));
                    }
                }else{
                    System.out.println(tempString);
                }
            }
            
            
            reader_equiv.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            return hm; 
        }
    }
    
    public static HashMap<String,Integer> KBrelation2hm(String inputKBfileName){
        File file = new File(inputKBfileName);
        BufferedReader reader = null;
        String tempString;
        HashMap<String,Integer> hm = new HashMap<String,Integer>();
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((tempString = reader.readLine()) != null) {
                hm.put(tempString.toLowerCase(), 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            return hm; 
        }
    }
}
