/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.OntologyLevelEvaluation;

import static com.mycompany.OntologyLevelEvaluation.TaxonomicAndNormalPrecRecF1.getOntology_Equal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/** this is a key program for the evaluation of the enrichment part:
 * output the enriched relations for further human evaluation.
 * 
 * The input of this program is based on the input and output of "TaxonomicCSUBforPredictionSubKBfromFolder.java":
 * [1] a folder containing all learned ontologies.
 * [2] a folder containing their corresponding sub-KBs from a GSO or KB.
 * @author cleme
 */
public class OutputEnrichedRelations {
    public static void main (String [] args){
        String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\common root log";
        
        //dbpedia
//        String LO_folder_path = path + "\\" + "dbpedia-5 roots 14ft svm";
//        String GSO_folder_path = path + "\\" + "dbpedia-sub-3";
//        String splitcharGSO = " <- ";
//        String GSO_equ = "skos_redir_from_page_redir_cleaned.txt";
//        String splitcharEqu = " = ";
//        String rootOfRootFileName = "roots-to-predict-dbpedia-CS-IS-5.txt";
//        String outputFileName = "enriched-relations-dbpedia-14ft-svm-test";

//        //acm
        String LO_folder_path = path + "\\" + "acm-2 roots 14ft adaboost";
        String GSO_folder_path = path + "\\" + "acm-sub";
        String splitcharGSO = " <- ";
        String GSO_equ = "acm_data_equivalence_new.txt";
        String splitcharEqu = " = ";
        String rootOfRootFileName = "roots-to-predict-acm-2.txt";
        String outputFileName = "enriched-relations-acm-14ft-ada-test.txt";
        
        HashMap<String,String> hm_root_rootOfRoot = getHmRootOfRoot(rootOfRootFileName);
        //store root-rootOfRoot key-value-set
        
        File fileW = new File(path + "\\" + outputFileName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileW));
        }catch (IOException e){
            e.printStackTrace();
        }   
        
        File folder_LO = new File(LO_folder_path);
        File folder_GSO = new File(GSO_folder_path);
        File[] listOfFiles_LO = folder_LO.listFiles();
        File[] listOfFiles_GSO = folder_GSO.listFiles();
        //String GSOFileNamePostfix = "äcm_preflabel_hierarchy"; // this should be speficied manually to make it simple.
        
        //load GSO_equ and save the redirect-redirected/prefferred key-value pairs to hm_GSO_equ.
        HashMap<String,String> hm_GSO_equ = getOntology_Equal(GSO_equ, splitcharEqu);
        
        HashMap<String,File> hm_root_file_GSO = new HashMap<String,File>();
        //stores the root-File key-value pairs.
        
        String fileName;
        String root_GSO;
        //stores the root-File key-value pairs to hm_root_file_GSO.
        for (File file_GSO: listOfFiles_GSO){
            if (file_GSO.isFile()) {
                fileName = file_GSO.getName();
                root_GSO = fileName.substring(0,fileName.lastIndexOf(" "));
                //System.out.println(root_GSO);
                hm_root_file_GSO.put(root_GSO, file_GSO);
            }
        }
        
        int space;
        String root;
        String root_GSO_form; // a possible GSO form 
        for (File file_LO: listOfFiles_LO){
            if (file_LO.isFile()) {
                fileName = file_LO.getName();
                space = fileName.indexOf(" ");
                System.out.println(fileName);
                root = fileName.substring(0, space);
                
                HashMap<String,String> hm_LO_direct_sub_relations = new HashMap<String,String>();
                //store all LO direct subsumption relations from the root as GSO format.
        
                //store all LO direct subsumption relations from the root as GSO format to hm_LO_direct_sub_relations.
                BufferedReader readerLO = null;
                String tempString = "";
                try {
                    readerLO = new BufferedReader(new FileReader(file_LO));
                    while ((tempString = readerLO.readLine()) != null) {
                        String [] arr = tempString.split(",");
                        if (arr.length == 2){
                            //find all direct subsumption relations and transform it to a GSO format.
                            if (arr[1].equals(root)){
                                
                                arr[0] = arr[0].toLowerCase().replace("_", " ");
                                if (hm_GSO_equ.containsKey(arr[0])){
                                    arr[0] = hm_GSO_equ.get(arr[0]);
                                }
                                
                                arr[1] = arr[1].toLowerCase().replace("_", " ");
                                if (hm_GSO_equ.containsKey(arr[1])){
                                    arr[1] = hm_GSO_equ.get(arr[1]);
                                }
                                hm_LO_direct_sub_relations.put(arr[0] + splitcharGSO + arr[1],tempString);
                            }
                        }else{
                            //System.out.println("[wrong format] " + tempString);
                        }
                    }
                    readerLO.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                root_GSO_form = root.toLowerCase().replace("_", " ");
                //System.out.println(root_GSO_form);
                // transform it to the redirected/prefferred/standard label.
                if (hm_GSO_equ.containsKey(root_GSO_form)){
                    root_GSO_form = hm_GSO_equ.get(root_GSO_form);
                }
                //System.out.println(root_GSO_form);
                if (hm_root_file_GSO.containsKey(root_GSO_form)){
                    File file_GSO = hm_root_file_GSO.get(root_GSO_form);
                    BufferedReader readerGSO = null;
                    try {
                        readerGSO = new BufferedReader(new FileReader(file_GSO));
                        while ((tempString = readerGSO.readLine()) != null) {
                            String [] arr = tempString.split(splitcharGSO);
                            if (arr.length == 2){
                                if (arr[1].equals(root_GSO_form)){
                                   if (hm_LO_direct_sub_relations.containsKey(tempString)){
                                        System.out.println(tempString); //matched relations
                                        hm_LO_direct_sub_relations.remove(tempString);
                                    }else{
                                        //System.out.println(tempString); //only contained in GSO
                                    }
                                }   
                            }else{
                                
                            }   
                        }
                        readerGSO.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    //System.out.println("[not found] " + root_GSO_form); // possibly the original LO is blank.
                }
                
                // output the enriched relations (only direct subsumption from root)
                try{
                    // iterate to output enriched relations.
                    Map<String, String> map = hm_LO_direct_sub_relations;
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        //System.out.println(entry.getValue() + " [" + entry.getKey() + "]"); //enriched/unmatched relations
                        
                        //output both tag concept form and KB concept form.
                        //writer.write(entry.getValue() + " [" + entry.getKey() + "]");
                        
                        //output tag concept form only.
                        writer.write(addRootOfRoot(entry.getValue(), hm_root_rootOfRoot));
                        writer.newLine();
                    }
                    writer.flush();
                }catch (IOException e){
                    e.printStackTrace();
                }    
            }
        }
        try{
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }      
    }
    
    static HashMap<String,String> getHmRootOfRoot(String rootOfRootFileName){
        HashMap<String,String> hm = new HashMap<String,String>();
        
        File fileO = new File(rootOfRootFileName); // Ontology
        BufferedReader readerO = null;
        try {
            readerO = new BufferedReader(new FileReader(fileO));
            String tempString = null;

            while ((tempString = readerO.readLine()) != null) {
                tempString = tempString.toLowerCase();
                String [] concepts = tempString.split(",");
                
                if (concepts.length == 2){
                    hm.put(concepts[0], concepts[1]);
                }else{
                    System.out.println("[wrong format in getHmRootOfRoot: ]" + tempString);
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
        
        return hm;
    }
    
    static String addRootOfRoot(String relation, HashMap<String,String> hm_root_rootOfRoot){
        String [] arr = relation.split(",");
        if (arr.length == 2){
            if (hm_root_rootOfRoot.containsKey(arr[1])){
                return arr[0] + "," + arr[1] + "," + hm_root_rootOfRoot.get(arr[1]);
            }else{
                System.out.println("[root not found in addRootOfRoot: ]" + relation);
                return arr[0] + "," + arr[1] + "," + arr[1];
            }    
        }else{
            System.out.println("[wrong format in addRootOfRoot: ]" + relation);
            return relation;
        }
        //return "";
    }
}
