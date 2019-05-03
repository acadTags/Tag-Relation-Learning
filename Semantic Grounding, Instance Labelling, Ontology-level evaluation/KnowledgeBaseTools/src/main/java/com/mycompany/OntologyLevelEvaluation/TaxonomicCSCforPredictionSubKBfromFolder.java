/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.OntologyLevelEvaluation;

import static com.mycompany.OntologyLevelEvaluation.TaxonomicAndNormalPrecRecF1.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/** this is the final program for the ontology-level evaluation: measuring similarity to a domain sub-KB.
 * Read files in the folder one by one, retrieve the LO, get the sub-GSO from a KB, then calculate TP, TR, TO for each LO.
 * Finally average all TP,TR,TO and output the results.
 * GSO: Gold Standard Ontology
 * LO: Learned Ontology
 * @author Hang Dong
 * @version 2019.4.14
 */
public class TaxonomicCSCforPredictionSubKBfromFolder {
    //static HashMap<String,String> hm_hyper_hypo = new HashMap<String,String>();
    
    public static void main (String [] args) throws FileNotFoundException{
        //System.out.println(formatPercent(0,8));
        boolean outputAsFile = false;
        //String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\";
        int level; // the depth to search for the KBs/ontologies.
        int level_subKB;
        
        boolean createSubKBfiles = true; // parameter to set, whether or not to create sub-KB files.
                                         // always set this as true.
        
        //dbpedia relations: parameter settings
//            level_subKB = 3; // level for getting subKB
//            level = 3; // level for getting the characteristic excerpt for evaluation
//            String GSO = "all_skos_data_dbpedia_cleaned.txt"; // skos only
//            String splitcharGSO = " < ";
//
//            String GSO_equ = "skos_redir_from_page_redir_cleaned.txt";
//            String splitcharEqu = " = ";
//            HashMap<String,String> hm_b_a = getOntology_b_a(GSO,splitcharGSO);
//            String LO_folder_path = "D:\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\common root log\\dbpedia-eco-3 probasso s";
//            //String LO_folder_path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\common root log\\dbpedia-test";
//            String sub_KB_folder_path = "D:\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\common root log\\dbpedia-eco-sub";
//            //String sub_KB_folder_path = "D:\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\common root log\\dbpedia-sub-3";
        
        //acm relations: parameter settings
            level_subKB = -1; // level for getting subKB
            level = -1; // level for csub
            String GSO = "äcm_preflabel_hierarchy.txt"; //acm original
            String splitcharGSO = " <- ";

            String GSO_equ = "acm_data_equivalence_new.txt";
            String splitcharEqu = " = ";
            HashMap<String,String> hm_b_a = getOntology_b_a(GSO,splitcharGSO);
            String LO_folder_path = "D:\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\common root log\\acm-2 roots alex8 adaboost";
            String sub_KB_folder_path = "D:\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction\\common root log\\acm-2 sub"; 
//             the folder path above should exist, otherwise the result will be 0%.
//        
        File folder = new File(LO_folder_path);
        File[] listOfFiles = folder.listFiles();
        
        String fileName;
        String root;
        String root_concept;
        int space;
        String LO;
        double [] results;
        double [] results_averaged = new double[4];
        results_averaged[0] = 0;
        results_averaged[1] = 0;
        results_averaged[2] = 0;
        results_averaged[3] = 0;
        double tf_final;
        int num = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                num++;
                // get the root tag of the LO.
                //System.out.println(file.getName());
                fileName = file.getName();
                space = fileName.indexOf(" ");
                root = fileName.substring(0, space);
                System.out.println("Root concept to enrich: " + root);
                
                // get LO
                LO = file.getPath();
                //System.out.println(file.getPath());

                // get sub-GSO: a sub-GSO from the KB, output it as a txt file.
                String [] commonconcepts =  getCommonConcepts(LO,",",GSO,splitcharGSO,GSO_equ,splitcharEqu);
                HashMap<String,Integer> hm_cc = new HashMap<String,Integer>(); // storing common concepts: allowing std and redirect concepts.
                if (commonconcepts == null){
                    System.out.println("\tno common concepts to the whole KB: a blank LO.");
                    continue; // this will result that not all matched tag concepts have a sub-KB in the sub-KB folder. // so keep createSubKBfiles always to true in line 39 in the experiment.
                }
                for (String concept: commonconcepts){
                    hm_cc.put(concept, 1);
                }
                HashMap<String,String> hm_GSO_equ = getOntology_Equal(GSO_equ, splitcharEqu, hm_cc); // storing redir-std key-value pair.
                
                root = root.replace("_", " ");
                root = root.toLowerCase();
                if (hm_GSO_equ.containsKey(root)){
                    root_concept = hm_GSO_equ.get(root);
                }else{
                    root_concept = root;
                }
                File fileW = new File(sub_KB_folder_path + "\\" + root_concept + " " + GSO);
                
                // create sub-GSO files.
                if (createSubKBfiles){
                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter(new FileWriter(fileW));
                        HashMap<String,String> hm_hypernym_hyponyms = new HashMap<String,String>();

                        //iteration to pass
                        Map<String, String> map = hm_b_a;
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            hm_hypernym_hyponyms.put(entry.getKey(), entry.getValue());
                        }

                        //System.out.println(hm_b_a.size() + " " + hm_hyper_hypo.size());
                        if (level_subKB == -1){
                            outputdomainKB(hm_hypernym_hyponyms, root_concept, writer);
                        }else{
                            outputdomainKB(hm_hypernym_hyponyms, root_concept, level_subKB, writer);
                        }
                        //hm_hyper_hypo.clear();
                        writer.flush();
                        writer.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                System.out.println(fileW.getPath());
                
                // get new sub_GSO_equ from the sub-GSO [no need to be implemented]
                
                // get results
                results = getEvaluationResults(level, LO, ",", fileW.getPath(), " <- ", GSO_equ, splitcharEqu, outputAsFile, "evaluation_log_redir.txt");
                results_averaged[0] = results_averaged[0] + results[0];
                results_averaged[1] = results_averaged[1] + results[1];
                results_averaged[2] = results_averaged[2] + results[2];
                results_averaged[3] = results_averaged[3] + results[3];
                System.out.println("current tp_sum = " + results_averaged[0]); //tp
                System.out.println("current tr_sum = " + results_averaged[1]); //tr
                System.out.println("current to_sum = " + results_averaged[2]); //to
                System.out.println("current tf_prime_sum = " + results_averaged[2]); //tf'                 
            }
        }
        System.out.println(num);
        System.out.println(results_averaged[0]); //tp
        System.out.println(results_averaged[1]); //tr
        System.out.println(results_averaged[2]); //to
        System.out.println(results_averaged[3]); //tf'
        results_averaged[0] = results_averaged[0]/(double)num;
        results_averaged[1] = results_averaged[1]/(double)num;
        results_averaged[2] = results_averaged[2]/(double)num;
        results_averaged[3] = results_averaged[3]/(double)num;
        tf_final = 2*results_averaged[0]*results_averaged[1]/(results_averaged[0]+results_averaged[1]);
        
        System.out.println("[csc] Final Taxonomic Precision is " + formatPercent(results_averaged[1],4) + "\n" + 
                "[csc] Final Taxonomic Recall is " + formatPercent(results_averaged[0],4) + "\n" + 
                "[csc] Final Taxonomic F-measure is " + formatPercent(tf_final,4) + "\n" + 
                "[csc] Final Taxonomic Overlap is " + formatPercent(results_averaged[2],4) + "\n" + 
                "[csc] Final Taxonomic F'-measure is " + formatPercent(results_averaged[3],4));
    }
    
    public static double [] getEvaluationResults(int level, String LO, String splitcharLO, String GSO, String splitcharGSO, String GSO_equ, String splitcharEqu, boolean outputAsFile, String outputFileName) throws FileNotFoundException{
        //String CE_definition = "DC"; //SC: semantic cotopy. DC: downward cotopy. UC: upward cotopy.
        //GSO = path + GSO;
        //GSO_equ = path + GSO_equ;
        double [] results = new double[4]; // results[0] = TR, results[1] = TP; results[2] = TO;
        results[0] = 0;
        results[1] = 0;
        results[2] = 0;
        results[3] = 0;
        
        if (outputAsFile){
            PrintStream out = new PrintStream(new FileOutputStream(outputFileName));
            System.setOut(out);
        }
        
        //String splitcharLO = ",";
        //String splitcharLO = " <- ";
        System.out.println("start to get common concepts");
        String [] commonconcepts =  getCommonConcepts(LO,splitcharLO,GSO,splitcharGSO,GSO_equ,splitcharEqu);
        
        HashMap<String,Integer> hm_cc = new HashMap<String,Integer>(); // storing common concepts: allowing std and redirect concepts.
        //HashMap<String,Integer> hm_cc_std = new HashMap<String,Integer>(); // storing common concepts: all std concepts.
        if (commonconcepts == null){
            return results;
        }
        for (String concept: commonconcepts){
            hm_cc.put(concept, 1);
        }
        
        System.out.println("start to load gold standard ontology");
        HashMap<String,String> hm_GSO_equ = getOntology_Equal(GSO_equ, splitcharEqu, hm_cc); // storing redir-std key-value pair.
        boolean std_as_key = true;
        HashMap<String,String> hm_GSO_equ_std = getOntology_Equal(GSO_equ, splitcharEqu, hm_cc, std_as_key); // storing std-redir1_redir2_redir3_... key-value pair.
        
    //* output the overlapped relations only for visualisation
        HashMap<String, String> hm = new HashMap<String, String>();
        File fileLO = new File(LO); // LO: Learned Ontology
        File fileGSO = new File(GSO); // GSO: Gold Standard Ontology
        BufferedReader readerLO = null;
        BufferedReader readerGSO = null;
        
        String nc; // narrower concept
        String bc; // broader concept
        try {
            readerLO = new BufferedReader(new FileReader(fileLO));
            readerGSO = new BufferedReader(new FileReader(fileGSO));
            String tempString = null;

            // store all distinct concepts in LO to hm.
            // deal with single quotes in the inputfile in LO.
            while ((tempString = readerLO.readLine()) != null) {
                tempString = tempString.replace("_", " ");
                tempString = tempString.toLowerCase();
                //tempString = removeSingleQuotesInConceptPair(tempString,splitcharLO);
                //System.out.println(tempString);
                String [] concepts = tempString.split(splitcharLO);
                //System.out.println(concepts[0] + " ||| " + concepts[1]);
                if (concepts.length == 2){
                    if (hm_GSO_equ.containsKey(concepts[1])){
                        nc = hm_GSO_equ.get(concepts[1]);
                    }else{
                        nc = concepts[1];
                    }
                    
                    if (hm_GSO_equ.containsKey(concepts[0])){
                        bc = hm_GSO_equ.get(concepts[0]);
                    }else{
                        bc = concepts[0];
                    }
                    
                    hm.put(nc + splitcharGSO + bc, concepts[1] + splitcharLO + concepts[0]);
                }else{
                    if (concepts.length == 3){
                        if (hm_GSO_equ.containsKey(concepts[2])){
                            nc = hm_GSO_equ.get(concepts[2]);
                        }else{
                            nc = concepts[2];
                        }

                        if (hm_GSO_equ.containsKey(concepts[0])){
                            bc = hm_GSO_equ.get(concepts[0]);
                        }else{
                            bc = concepts[0];
                        }
                    
                        hm.put(nc + splitcharGSO + bc, concepts[2] + splitcharLO + concepts[0]);
                    }else{
                        System.out.println(tempString);
                    }    
                }
            }
            
            // using hm to check whether in the GSO there are any common concepts, and store these common concepts to hm_distinct_csc.
            while ((tempString = readerGSO.readLine()) != null) {
                tempString = tempString.toLowerCase();
                if (hm.containsKey(tempString)){
                    System.out.println(tempString + " [" + hm.get(tempString) + "]");
                }
            }
            readerGSO.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readerLO != null) {
                try {
                    readerLO.close();
                } catch (IOException e1) {
                }
            }
        }
    
    //* get ontology-level taxonomic precision, taxonomic recall and taxonomic f-measure.
        
        //System.out.println("start to get common concepts");
        //String [] commonconcepts =  getCommonConcepts(LO,splitcharLO,GSO,splitcharGSO,GSO_equ,splitcharEqu);
        
        //HashMap<String,Integer> hm_cc = new HashMap<String,Integer>(); // storing common concepts: allowing std and redirect concepts.
        HashMap<String,Integer> hm_cc_std = new HashMap<String,Integer>(); // storing common concepts: all std concepts. common concept but in std form in the GSO.
//        for (String concept: commonconcepts){
//            hm_cc.put(concept, 1);            
//        }
        
        //System.out.println("hm_cc contains " + "molecular sequence data? " + hm_cc.containsKey("molecular sequence data"));
        //System.out.println("hm_cc contains " + "ccc? " + hm_cc.containsKey("ccc"));
        //System.out.println("hm_cc contains " + "half? " + hm_cc.containsKey("half"));
        
        //System.out.println("start to load gold standard ontology");
        //HashMap<String,String> hm_GSO_equ = getOntology_Equal(GSO_equ, splitcharEqu, hm_cc);
        //System.out.println("hm_cc contains " + "molecular sequence data? " + hm_cc.containsKey("molecular sequence data"));
        //System.out.println("hm_GSO_equ contains " + "half? " + hm_GSO_equ.containsKey("half"));
        //System.out.println("hm_cc contains " + "tel? " + hm_cc.containsKey("tel"));
        
        // common concept but in std form in the GSO.
        //String concept_std;
        for (String concept: commonconcepts){
            if (hm_GSO_equ.containsKey(concept)){
                concept = hm_GSO_equ.get(concept);                
            }
            hm_cc_std.put(concept, 1);
        }
        
        //HashMap<String,String> hm_GSO_a_b = getOntology_a_b(GSO, splitcharGSO, hm_cc, hm_GSO_equ);
        HashMap<String,String> hm_GSO_a_b = getOntology_a_b(GSO, splitcharGSO);
        //System.out.println("hm_cc contains " + "molecular sequence data? " + hm_cc.containsKey("molecular sequence data"));
        //System.out.println("hm_GSO_a_b contains " + "ccc? " + hm_GSO_a_b.containsKey("ccc"));
        //System.out.println("hm_GSO_a_b contains " + "half? " + hm_GSO_a_b.containsKey("half"));
        //System.out.println("hm_GSO_a_b contains " + "tel? " + hm_GSO_a_b.containsKey("tel"));
        
        //HashMap<String,String> hm_GSO_b_a = getOntology_b_a(GSO, splitcharGSO, hm_cc, hm_GSO_equ);
        HashMap<String,String> hm_GSO_b_a = getOntology_b_a(GSO, splitcharGSO);
        //System.out.println("hm_GSO_b_a contains " + "ccc? " + hm_GSO_b_a.containsKey("ccc"));
        //System.out.println("hm_GSO_b_a contains " + "half? " + hm_GSO_b_a.containsKey("half"));
        //System.out.println("hm_GSO_b_a contains " + "tel? " + hm_GSO_b_a.containsKey("tel"));
        
        System.out.println("start to load learned ontology");
        //System.out.println("hm_cc contains " + "molecular sequence data? " + hm_cc.containsKey("molecular sequence data"));
        //HashMap<String,String> hm_LO_a_b = getOntology_a_b(LO, splitcharLO, hm_cc);
        HashMap<String,String> hm_LO_b_a = getOntology_b_a(LO, splitcharLO); // hyper-hypo
        HashMap<String,String> hm_LO_a_b = getOntology_a_b(LO, splitcharLO); // hypo-hyper
        //System.out.println("hm_LO_a_b contains " + "molecular sequence data? " + hm_LO_a_b.containsKey("molecular sequence data"));
        //HashMap<String,String> hm_LO_b_a = getOntology_b_a(LO, splitcharLO, hm_cc);
        //System.out.println("hm_LO_b_a contains " + "molecular sequence data? " + hm_LO_b_a.containsKey("molecular sequence data"));
        
        int size = commonconcepts.length;
        //System.out.println(size);
        
        String concept_std;
        // clean up the wrong matched concepts matched to GSO_equ but not the sub_GSO.
        // also update the size here.
        for (String concept: commonconcepts){
            //System.out.println("[" + concept + "]\nGSO:");
            if (hm_GSO_equ.containsKey(concept)){
                concept_std = hm_GSO_equ.get(concept);
                //System.out.println("\t = " + concept_std);
            }else{
                concept_std = concept;
            }
            if (!hm_GSO_b_a.containsKey(concept_std) && !hm_GSO_a_b.containsKey(concept_std)){
                size--;
                hm_cc_std.remove(concept_std);
                hm_cc.remove(concept);
                System.out.println(concept + " [" + concept_std + "] removed.");
            }
        }
        
        int size_concept_LO = getOntologyConceptSize(LO,splitcharLO);
        int size_concept_GSO = getOntologyConceptSize(GSO,splitcharGSO);
                
        double tp_sum = 0; // Taxonomic Precision
        double tr_sum = 0; // Taxonomic Recall
        double to_sum = 0; // Taxonomic Overlap
        double tf; // Taxonomy F-measure
        double lr = (double)size/(double)size_concept_GSO; // Lexical Recall
        double tf_prime; // Taxonomy F'-measure
        
        // start calculating the tp tr tf to.
        for (String concept: commonconcepts){
            System.out.println("[" + concept + "]\nGSO:");
            if (hm_GSO_equ.containsKey(concept)){
                concept_std = hm_GSO_equ.get(concept);
                System.out.println("\t = " + concept_std);
            }else{
                concept_std = concept;
            }
            //String [] GSO_ce = getCaracteristicExcept(concept_std,hm_GSO_a_b,hm_GSO_b_a,CE_definition);
            //String [] GSO_ce = getCaracteristicExcept(concept_std,hm_GSO_a_b,hm_GSO_b_a,hm_cc_std,CE_definition);
            
            //String [] GSO_ce = getCaracteristicExceptCSUB(concept_std, hm_GSO_b_a, hm_cc_std, level);
            String [] GSO_ce = getCaracteristicExceptCSC(concept_std, hm_GSO_a_b, hm_GSO_b_a, hm_cc_std, level);
            if (GSO_ce == null){
                //System.out.println("[contain redir form]" + hm_cc.containsKey(concept));
                //System.out.println("[contain std form]" + hm_cc.containsKey(concept_std));
//                if (!hm_GSO_b_a.containsKey(concept_std) && !hm_GSO_a_b.containsKey(concept_std)){
//                    size--; //this is not a common concept by actual definition, it is matched to GSO_equ but not the sub_GSO.
//                    //so we decrease 1 for the size.
//                    System.out.println("not found, size decrease 1");
//                }else{
//                    System.out.println("none");
//                }    
                continue;
            }
            String [] GSO_ce_redir = redirise(GSO_ce,hm_GSO_equ_std);
            //String [] GSO_ce_redir = redirise(GSO_ce,hm_GSO_a_b, hm_GSO_b_a,hm_GSO_equ_std);
            //displayArr(GSO_ce_redir);
            displayArr(GSO_ce_redir,hm_GSO_equ,hm_GSO_equ_std);
            //int GSO_ce_size = GSO_ce.length; //here still, we calculate using the number of std concepts in CE for GSO.
            int GSO_ce_size = GSO_ce_redir.length;
            System.out.println(GSO_ce_size);
            
            System.out.println("-----\nLO:");
            
            //String [] LO_ce = getCaracteristicExcept(concept,hm_LO_a_b,hm_LO_b_a,CE_definition_LO);
            //String [] LO_ce = getCaracteristicExcept(concept,hm_LO_a_b,hm_LO_b_a,hm_cc,CE_definition_LO);
            //String [] LO_ce = getCaracteristicExceptCSUB(concept, hm_LO_b_a, hm_cc, level);
            String [] LO_ce = getCaracteristicExceptCSC(concept, hm_LO_a_b, hm_LO_b_a, hm_cc, level);
            if (LO_ce == null){
                //System.out.println("[contain redir form]" + hm_cc.containsKey(concept));
                //System.out.println("[contain std form]" + hm_cc.containsKey(concept_std));
                //size--;
                System.out.println("none");
                continue;
            }
            displayArr(LO_ce);
            int LO_ce_size = LO_ce.length;
            System.out.println(LO_ce_size);
            
            //String [] ce_union = getUnionArr(GSO_ce,LO_ce);
            int num_ce_union = getUnionArr(GSO_ce_redir,LO_ce).length;
                    
            int num_ce_common = getNumCommonElements(GSO_ce_redir,LO_ce);
            System.out.println(num_ce_common);
            System.out.println("");
            if (LO_ce_size != 0){
                tp_sum = tp_sum + (double)num_ce_common/(double)LO_ce_size;
            }
            if (GSO_ce_size != 0){
                tr_sum = tr_sum + (double)num_ce_common/(double)GSO_ce_size;
            }    
            if (num_ce_union != 0){
                to_sum = to_sum + (double)num_ce_common/(double)num_ce_union;
            }
            System.out.println(tp_sum);
            System.out.println(tr_sum);
            System.out.println(to_sum);
            System.out.println("");        
        }
        if (size == 0){
            return results;
        }
        tp_sum = tp_sum/size;
        tr_sum = tr_sum/size;
        tf = 2*tp_sum*tr_sum/(tp_sum+tr_sum);
        to_sum = to_sum/size;
        if (tp_sum+tr_sum == 0){
            tf_prime = 0;
        }else{
            tf_prime = 2*lr*tf/(lr+tf);
        }
                
        System.out.println("Ontology:" + LO + "\ntotal concepts:" + size_concept_LO);
        System.out.println("Gold Standard:" + GSO + "\ntotal concepts:" + size_concept_GSO);
        System.out.println("Overlapped concept size:" + size);        
        System.out.println("[csc] Taxonomic Precision is " + formatPercent(tp_sum,4) + "\n" + 
                "[csc] Taxonomic Recall is " + formatPercent(tr_sum,4) + "\n" + 
                "[csc] Taxonomic F-measure is " + formatPercent(tf,4) + "\n" + 
                "[csc] Taxonomic Overlap is " + formatPercent(to_sum,4) + "\n" + 
                "[csc] Taxonomic F'-measure is " + formatPercent(tf_prime,4));
        //csc stands for common semantic cotopy
        
        results[0] = tr_sum;
        results[1] = tp_sum;
        results[2] = to_sum;
        results[3] = tf_prime;
        return results;
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
