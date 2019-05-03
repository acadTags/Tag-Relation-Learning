/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.OntologyLevelEvaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cleme
 */
public class TaxonomicAndNormalPrecRecF1 {
    
    public static void main(String [] args){
        String CE_definition = "DC"; //SC: semantic cotopy. DC: downward cotopy. UC: upward cotopy.
        
        String path = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\TagRelationVisualisation\\";//1000 topics for whole data - new\\";
        //String path = "/Users/hangdong/OneDrive/NetBeansProjects/TagRelationVisualisation/"; //for mac
        
        //LO pairwise PTM
        //String LO = path + "pairwise_ranking_fuzzy.csv.csv"; // fuzzy-set-PTM-0.5
        //String LO = path + "pairwise_ranking_centrality.csv"; // centrality-PTM
        //String LO = path + "pairwise_ranking_prob_asso_nf0.csv"; // prob-asso-PTM-0
        //String LO = path + "pairwise_ranking_info_theo0.05.csv"; // info-theo-PTM-0.05
        //String LO = path + "pairwise_ranking_info_theo0.csv"; // info-theo-PTM-0
        String LO = path + "pairwise_ranking_set0.7.csv"; // set-PTM-0.7
        
        //LO pairwise res-based
        //String LO = path + "pairwise_ranking_prob_asso_nf0_res_based.csv"; // prob-asso-res-based-0
        //String LO = path + "pairwise_ranking_info_theo_res_based.csv"; // info-theo-res-based-0
        //String LO = path + "pairwise_ranking_info_theo0.05_res_based.csv"; // info-theo-res-based-0.05
        //String LO = path + "pairwise_ranking_fuzzy_res_based.csv.csv"; // fuzzy-set-res-based-0.5
        //String LO = path + "pairwise_ranking_centrality_res_based.csv"; // centrality-res-based
        //String LO = path + "pairwise_ranking_set_res_based0.7.csv.csv"; // set-res-based-0.7
        
        //LO heymann structure
        //String LO = path + "bibsonomy_pw_ranking_0.2.csv"; //pw-ranking-0.2 (taxThreshold)
        //String LO = path + "bibsonomy_topic_heymann_graph_res1_0.1_0.2.csv"; //centrality-heymann-PTM-0.1-0.2
        //String LO = path + "heymann_graph_0.1_0.2.csv"; // central-heymann-res-based-0.1-0.2
        
        //LO random PTM
        //String LO = path + "pairwise_ranking_random_topic_rep.csv.csv"; //random-PTM
        //String LO = path + "pairwise_ranking_random1_topic_rep.csv.csv"; //random-PTM
        //String LO = path + "pairwise_ranking_random2_topic_rep.csv.csv"; //random-PTM
        //String LO = path + "pairwise_ranking_random3_topic_rep.csv.csv"; //random-PTM
        //String LO = path + "pairwise_ranking_random4_topic_rep.csv.csv"; //random-PTM
        
        //LO random tag2vec
        //String LO = path + "pairwise_ranking_random_tag2vec.csv.csv"; //random-tag2vec
        
        //LO random res-based
        //String LO = path + "pairwise_ranking_random_res_based.csv.csv"; //random-res-based
        //String LO = path + "pairwise_ranking_random1_res_based.csv.csv"; //random-res-based
        //String LO = path + "pairwise_ranking_random2_res_based.csv.csv"; //random-res-based
        //String LO = path + "pairwise_ranking_random3_res_based.csv.csv"; //random-res-based
        //String LO = path + "pairwise_ranking_random4_res_based.csv.csv"; //random-res-based
        
        String splitcharLO = ",";
        //String splitcharLO = " <- ";
        
        //dbpedia relations
        String GSO = "all_skos_and_article_cat_data_cleaned.txt";
        String splitcharGSO = " <- ";
        
        String GSO_equ = "all_page_redirect_data_cleaned.txt";
        String splitcharEqu = " = ";
        
        //acm relations [transitive-closured]
        //String GSO = "äcm_preflabel_hierarchy.txt"; //acm original
        //String GSO = "acm_taxonomy_trans_closure.txt"; // acm transitive closured
        //String splitcharGSO = " <- ";
        
        //String GSO_equ = "acm_data_equivalence_new.txt";
        //String splitcharEqu = " = ";
        
        //MCG relations
        //String GSO = "MCG_all_cleaned.txt";
        //String splitcharGSO = " <- ";
        
        //String GSO = "databases_sub_page_data_matched_noredir_ed1_new_relations.txt";
        //String GSO = "semantic_web_sub_pages_matched.txt";
        
        //String GSO = "genetics_sub_all_data_matched_to_tags_redirect_reduced.txt";
        //String GSO = "databases_sub_all_data_matched_to_tags_redirect_new_relations.txt";
        //String GSO = "algorithms_sub_all_data_matched_to_tags_redirect_new_relations.txt";
        //String GSO = "web_development_sub_all_data_matched_to_tags_new_relations.txt";
                
        //String GSO = "social_networks_sub_all_data_matched_to_tags_redir_reduced.txt";
        //String GSO = "machine_learning_mapped_human_selected_redirect_reduced.txt";
        //String GSO = "library_sc_sub_all_data_matched_to_tags_reduced_new.txt";
        //String GSO = "semantic_web_mapped_human_selected_reduced_new.txt";
        //String splitcharGSO = " <- ";
        
    // * get relation-level precision, recall, f-measure [so far it does not take the equivalent list into consideration.
        HashMap<String, String> hm = new HashMap<String, String>();
        int n_common = 0;
        int size_LO = 0;
        int size_GSO = 0;
        
        double rec=0;
        double prec=0;
        double f_measure=0;
        
        System.out.println("start to get common concepts");
        String [] commonconcepts =  getCommonConcepts(LO,splitcharLO,GSO,splitcharGSO,GSO_equ,splitcharEqu);
        
        HashMap<String,Integer> hm_cc = new HashMap<String,Integer>(); // storing common concepts: allowing std and redirect concepts.
        //HashMap<String,Integer> hm_cc_std = new HashMap<String,Integer>(); // storing common concepts: all std concepts.
        for (String concept: commonconcepts){
            hm_cc.put(concept, 1);
        }
        System.out.println("start to load gold standard ontology");
        HashMap<String,String> hm_GSO_equ = getOntology_Equal(GSO_equ, splitcharEqu, hm_cc); // storing redir-std key-value pair.
        boolean std_as_key = true;
        HashMap<String,String> hm_GSO_equ_std = getOntology_Equal(GSO_equ, splitcharEqu, hm_cc, std_as_key); // storing std-redir1_redir2_redir3_... key-value pair.
        
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
                size_LO++;
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
                size_GSO++;
                tempString = tempString.toLowerCase();
                if (hm.containsKey(tempString)){
                    System.out.println(tempString + " [" + hm.get(tempString) + "]");
                    n_common++;
                }
            }
            readerGSO.close();
            
            prec = (double)n_common/(double)size_LO;
            rec = (double)n_common/(double)size_GSO;
            f_measure = 2*prec*rec/(prec+rec);
//            System.out.println("Precision is " + prec + "\n" + 
//                "Recall is " + rec + "\n" + 
//                "F-measure is " + f_measure);
            
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
        
        System.out.println("Ontology:" + LO);
        System.out.println("Gold Standard:" + GSO);
        System.out.println("Precision is " + formatPercent(prec,4) + "\n" + 
                "Recall is " + formatPercent(rec,7) + "\n" + 
                "F-measure is " + formatPercent(f_measure,4));
        
        //System.exit(0);
        
    //* get ontology-level taxonomic precision, taxonomic recall and taxonomic f-measure.
        String CE_definition_LO = "SC"; // default value
        if (CE_definition.equals("SC")){
            CE_definition_LO = CE_definition;
        }else if (CE_definition.equals("DC")){
            CE_definition_LO = "UC";
        }else if (CE_definition.equals("UC")){
            CE_definition_LO = "DC";
        }
        
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
        
        System.out.println("start to load gold standard ontology");
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
        
        HashMap<String,String> hm_GSO_a_b = getOntology_a_b(GSO, splitcharGSO, hm_cc, hm_GSO_equ);
        //System.out.println("hm_cc contains " + "molecular sequence data? " + hm_cc.containsKey("molecular sequence data"));
        //System.out.println("hm_GSO_a_b contains " + "ccc? " + hm_GSO_a_b.containsKey("ccc"));
        //System.out.println("hm_GSO_a_b contains " + "half? " + hm_GSO_a_b.containsKey("half"));
        //System.out.println("hm_GSO_a_b contains " + "tel? " + hm_GSO_a_b.containsKey("tel"));
        HashMap<String,String> hm_GSO_b_a = getOntology_b_a(GSO, splitcharGSO, hm_cc, hm_GSO_equ);
        //System.out.println("hm_GSO_b_a contains " + "ccc? " + hm_GSO_b_a.containsKey("ccc"));
        //System.out.println("hm_GSO_b_a contains " + "half? " + hm_GSO_b_a.containsKey("half"));
        //System.out.println("hm_GSO_b_a contains " + "tel? " + hm_GSO_b_a.containsKey("tel"));
        
        System.out.println("start to load learned ontology");
        //System.out.println("hm_cc contains " + "molecular sequence data? " + hm_cc.containsKey("molecular sequence data"));
        HashMap<String,String> hm_LO_a_b = getOntology_a_b(LO, splitcharLO, hm_cc);
        //System.out.println("hm_LO_a_b contains " + "molecular sequence data? " + hm_LO_a_b.containsKey("molecular sequence data"));
        HashMap<String,String> hm_LO_b_a = getOntology_b_a(LO, splitcharLO, hm_cc);
        //System.out.println("hm_LO_b_a contains " + "molecular sequence data? " + hm_LO_b_a.containsKey("molecular sequence data"));
        
        int size = commonconcepts.length;
        //System.out.println(size);
        
        double tp_sum = 0; // Taxonomic Precision
        double tr_sum = 0; // Taxonomic Recall
        double to_sum = 0; // Taxonomic Overlap
        double tf; // Taxonomy F-measure
        String concept_std;
        for (String concept: commonconcepts){
            System.out.println("[" + concept + "]\nGSO:");
            if (hm_GSO_equ.containsKey(concept)){
                concept_std = hm_GSO_equ.get(concept);
                System.out.println("\t = " + concept_std);
            }else{
                concept_std = concept;
            }
            String [] GSO_ce = getCaracteristicExcept(concept_std,hm_GSO_a_b,hm_GSO_b_a,CE_definition);
            //String [] GSO_ce = getCaracteristicExcept(concept_std,hm_GSO_a_b,hm_GSO_b_a,hm_cc_std,CE_definition);
            if (GSO_ce == null){
                //System.out.println("[contain redir form]" + hm_cc.containsKey(concept));
                //System.out.println("[contain std form]" + hm_cc.containsKey(concept_std));
                //size--;
                continue;
            }
            String [] GSO_ce_redir = redirise(GSO_ce,hm_GSO_equ_std);
            //displayArr(GSO_ce_redir);
            displayArr(GSO_ce_redir,hm_GSO_equ,hm_GSO_equ_std);
            //int GSO_ce_size = GSO_ce.length; //here still, we calculate using the number of std concepts in CE for GSO.
            int GSO_ce_size = GSO_ce_redir.length;
            System.out.println(GSO_ce_size);

            System.out.println("-----\nLO:");
            
            String [] LO_ce = getCaracteristicExcept(concept,hm_LO_a_b,hm_LO_b_a,CE_definition_LO);
            //String [] LO_ce = getCaracteristicExcept(concept,hm_LO_a_b,hm_LO_b_a,hm_cc,CE_definition);
            if (LO_ce == null){
                //System.out.println("[contain redir form]" + hm_cc.containsKey(concept));
                //System.out.println("[contain std form]" + hm_cc.containsKey(concept_std));
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
        tp_sum = tp_sum/size;
        tr_sum = tr_sum/size;
        tf = 2*tp_sum*tr_sum/(tp_sum+tr_sum);
        to_sum = to_sum/size;
        
        System.out.println("Ontology:" + LO);
        System.out.println("Gold Standard:" + GSO);
        System.out.println("Overlapped concept size:" + size);        
        System.out.println("Precision is " + formatPercent(prec,4) + "\n" + 
                "Recall is " + formatPercent(rec,7) + "\n" + 
                "F-measure is " + formatPercent(f_measure,4));
        System.out.println(" Taxonomic Precision is " + formatPercent(tp_sum,4) + "\n" + 
                "Taxonomic Recall is " + formatPercent(tr_sum,4) + "\n" + 
                "Taxonomic F-measure is " + formatPercent(tf,4) + "\n" + 
                "Taxonomic Overlap is " + formatPercent(to_sum,4));
        //System.out.println(("& " + formatPercent(prec,2) + " & " + formatPercent(rec,2) + " & " + formatPercent(f_measure,2) +
        //        " & " + formatPercent(tp_sum,2) + " & " + formatPercent(tr_sum,2) + " & " + 
        //        formatPercent(tf,2) + " & " + formatPercent(to_sum,2)).replace("%", "\\%"));
        //System.out.printf ("Value with 4 digits after decimal point %.4f", tr_sum);

//        String [] commonconcepts = getCommonSemanticCotopy("239_(239+986)_results_SW.csv",",","semantic_web_mapped_human_selected_reduced_new.txt"," <- ");
//        for (String concept: commonconcepts){
//            System.out.println(concept);
//        }

//        HashMap<String,String> hm = getOntology_b_a("239_(239+986)_results_SW.csv", ",");
//        Map<String, String> map = hm;
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + " ||| " + entry.getValue());
//        }

//          String [] ce = getCaracteristicExtract("semantic_web", getOntology_a_b(GSO, " <- "), getOntology_b_a(GSO, " <- "),"UC");
//          for (String concept: ce){
//              System.out.println(concept);
//          }
    }
    
    static int getNumCommonElements(String [] arr1, String [] arr2){
        int n = 0;
        HashMap<String, Integer> hm_distinct_arr1 = new HashMap<String, Integer>();
        HashMap<String, Integer> hm_distinct_arr2 = new HashMap<String, Integer>();
        for (String str: arr1){
            hm_distinct_arr1.put(str, 1);
        }
        for (String str: arr2){
            hm_distinct_arr2.put(str,1);
        }
        
        // display common concepts
        Map<String, Integer> map = hm_distinct_arr2;
        for (String str : map.keySet()) {
            if (hm_distinct_arr1.containsKey(str)){
                System.out.println("\tcommon concept: " + str);
                n++;
            }
        }
        return n;
    }
    
    static void displayArr(String [] array){
        for (String str: array){
              System.out.println(str);
          }
    }
    
    // also display the std concepts if the concept in CE is a redir concept.
    static void displayArr(String [] array, HashMap<String,String> hm_GSO_equ, HashMap<String,String> hm_GSO_equ_std){
        for (String str: array){
            if (hm_GSO_equ.containsKey(str) && !hm_GSO_equ_std.containsKey(str)){
                System.out.println(str + "[=" + hm_GSO_equ.get(str) + "]");
            }else{
                System.out.println(str);
            }
        }
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
                tempString = tempString.replace("_", " ");
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
    
    static HashMap<String,String> getOntology_a_b(String inputOntologyFileName, String splitchar, HashMap<String,Integer> hm_commonconcepts){
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
                tempString = tempString.replace("_", " ");
                tempString = tempString.toLowerCase();
                String [] concepts = tempString.split(splitchar);
                
                if (concepts.length == 2){
                    if (hm_commonconcepts.containsKey(concepts[0])){
                        if (hm_a_b.containsKey(concepts[0])){
                            hm_a_b.put(concepts[0], hm_a_b.get(concepts[0]) + " ||| " + concepts[1]);
                        }else{
                            hm_a_b.put(concepts[0], concepts[1]);
                        }
                    }    
                }else{
                    // case of "," at the tail of a tag.
                    if (concepts.length == 3){
                        if (hm_commonconcepts.containsKey(concepts[0])){
                            if (hm_a_b.containsKey(concepts[0])){
                                hm_a_b.put(concepts[0], hm_a_b.get(concepts[0]) + " ||| " + concepts[2]);
                            }else{
                                hm_a_b.put(concepts[0], concepts[2]);
                            }
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
    
    static HashMap<String,String> getOntology_a_b(String inputOntologyFileName, String splitchar, HashMap<String,Integer> hm_commonconcepts, HashMap<String, String> hm_equ){
        // change redirected concepts to std concepts.
        String [] commonconcepts = getKeyArrayFromHashMap(hm_commonconcepts);
        HashMap<String,Integer> hm_commonconcepts_std = new HashMap<String,Integer>();
        //hm_commonconcepts.clear();
        for (String concept: commonconcepts){
            if (hm_equ.containsKey(concept)){
                concept = hm_equ.get(concept);
            }
            hm_commonconcepts_std.put(concept, 1);
        }
        
        return getOntology_a_b(inputOntologyFileName,splitchar,hm_commonconcepts_std);
    }
    
//    //csub. uncompleted. not applicable?
//    static HashMap<String,String> getOntology_a_b_csub(String inputOntologyFileName, String splitchar, HashMap<String,Integer> hm_commonconcepts){
//        HashMap<String, String> hm_a_b = new HashMap<String, String>(); // storing a <- b_c_d, where _ means a white space
//        
//        File fileO = new File(inputOntologyFileName); // Ontology
//        BufferedReader readerO = null;
//        try {
//            readerO = new BufferedReader(new FileReader(fileO));
//            String tempString = null;
//
//            // storing a <- b_c_d, where _ means a white space, to the hashmap hm_a_b
//            // storing b -> a_c_d, where _ means a white space, to the hashmap hm_b_a
//            while ((tempString = readerO.readLine()) != null) {
//                //tempString = removeSingleQuotesInConceptPair(tempString, splitchar);
//                tempString = tempString.replace("_", " ");
//                tempString = tempString.toLowerCase();
//                String [] concepts = tempString.split(splitchar);
//                
//                if (concepts.length == 2){
//                    if (hm_commonconcepts.containsKey(concepts[0])){
//                        if (hm_commonconcepts.containsKey(concepts[1])){
//                            if (hm_a_b.containsKey(concepts[0])){
//                                hm_a_b.put(concepts[0], hm_a_b.get(concepts[0]) + " ||| " + concepts[1]);
//                            }else{
//                                hm_a_b.put(concepts[0], concepts[1]);
//                            }
//                        }else{
//                            
//                        }
//                    }    
//                }else{
//                    // case of "," at the tail of a tag.
//                    if (concepts.length == 3){
//                        if (hm_commonconcepts.containsKey(concepts[0])){
//                            if (hm_a_b.containsKey(concepts[0])){
//                                hm_a_b.put(concepts[0], hm_a_b.get(concepts[0]) + " ||| " + concepts[2]);
//                            }else{
//                                hm_a_b.put(concepts[0], concepts[2]);
//                            }
//                        }    
//                    }else{
//                        System.out.println(tempString);
//                    }    
//                }
//            }
//            
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (readerO != null) {
//                try {
//                    readerO.close();
//                } catch (IOException e1) {
//                }
//            }
//        }
//        
//        return hm_a_b;
//    }
    
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
                tempString = tempString.replace("_", " ");
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
    
    static HashMap<String,String> getOntology_b_a(String inputOntologyFileName, String splitchar, HashMap<String,Integer> hm_commonconcepts){
        HashMap<String, String> hm_b_a = new HashMap<String, String>(); // storing b -> a_c_d, where _ means a white space
        
        File fileO = new File(inputOntologyFileName); // Ontology
        BufferedReader readerO = null;
        try {
            readerO = new BufferedReader(new FileReader(fileO));
            String tempString = null;
            
            // storing a <- b_c_d, where _ means a white space, to the hashmap hm_a_b
            // storing b -> a_c_d, where _ means a white space, to the hashmap hm_b_a
            while ((tempString = readerO.readLine()) != null) {
                tempString = tempString.replace("_", " ");
                tempString = tempString.toLowerCase();
                //tempString = removeSingleQuotesInConceptPair(tempString, splitchar);
                String [] concepts = tempString.split(splitchar);
                
                if (concepts.length == 2){
                    if (hm_commonconcepts.containsKey(concepts[1])){
                        if (hm_b_a.containsKey(concepts[1])){
                            hm_b_a.put(concepts[1], hm_b_a.get(concepts[1]) + " ||| " + concepts[0]);
                        }else{
                            hm_b_a.put(concepts[1], concepts[0]);
                        }
                    }    
                }else{
                    // case of "," at the tail of a tag.
                    if (concepts.length == 3){
                        if (hm_commonconcepts.containsKey(concepts[2])){
                            if (hm_b_a.containsKey(concepts[2])){
                                hm_b_a.put(concepts[2], hm_b_a.get(concepts[2]) + " ||| " + concepts[0]);
                            }else{
                                hm_b_a.put(concepts[2], concepts[0]);
                            }
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
    
    static HashMap<String,String> getOntology_b_a(String inputOntologyFileName, String splitchar, HashMap<String,Integer> hm_commonconcepts, HashMap<String, String> hm_equ){
        // change redirected concepts to std concepts.
        String [] commonconcepts = getKeyArrayFromHashMap(hm_commonconcepts);
        HashMap<String,Integer> hm_commonconcepts_std = new HashMap<String,Integer>();
        //hm_commonconcepts.clear();
        for (String concept: commonconcepts){
            if (hm_equ.containsKey(concept)){
                concept = hm_equ.get(concept);
            }
            hm_commonconcepts_std.put(concept, 1);
        }
        return getOntology_b_a(inputOntologyFileName,splitchar,hm_commonconcepts_std);
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
                tempString = tempString.replace("_", " ");
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
    
    static HashMap<String,String> getOntology_Equal(String inputEqualList, String splitchar, HashMap<String,Integer> hm_commonconcepts){
        HashMap<String, String> hm_equ = new HashMap<String, String>();
        
        File fileO = new File(inputEqualList); // Ontology
        BufferedReader readerO = null;
        try {
            readerO = new BufferedReader(new FileReader(fileO));
            String tempString = null;
            
            // storing a <- b_c_d, where _ means a white space, to the hashmap hm_a_b
            // storing b -> a_c_d, where _ means a white space, to the hashmap hm_b_a
            while ((tempString = readerO.readLine()) != null) {
                tempString = tempString.replace("_", " ");
                tempString = tempString.toLowerCase();
                //tempString = removeSingleQuotesInConceptPair(tempString, splitchar);
                String [] concepts = tempString.split(splitchar);
                
                if (concepts.length == 2){
                    if (hm_commonconcepts.containsKey(concepts[0])){
                        hm_equ.put(concepts[0], concepts[1]);
                    }    
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
    
    //std as key: storing std-redir1_redir2_redir3_... key-value pairs, "_" is " ||| ".
    static HashMap<String,String> getOntology_Equal(String inputEqualList, String splitchar, HashMap<String,Integer> hm_commonconcepts, boolean std_as_key){
        if (!std_as_key){
            return getOntology_Equal(inputEqualList, splitchar, hm_commonconcepts);
        }
        
        HashMap<String, String> hm_equ = new HashMap<String, String>();
        
        File fileO = new File(inputEqualList); // Ontology
        BufferedReader readerO = null;
        try {
            readerO = new BufferedReader(new FileReader(fileO));
            String tempString = null;
            
            // storing a <- b_c_d, where _ means a white space, to the hashmap hm_a_b
            // storing b -> a_c_d, where _ means a white space, to the hashmap hm_b_a
            while ((tempString = readerO.readLine()) != null) {
                tempString = tempString.replace("_", " ");
                tempString = tempString.toLowerCase();
                //tempString = removeSingleQuotesInConceptPair(tempString, splitchar);
                String [] concepts = tempString.split(splitchar);
                
                if (concepts.length == 2){
                    if (hm_commonconcepts.containsKey(concepts[0])){
                        if (hm_equ.containsKey(concepts[1])){
                            hm_equ.put(concepts[1], hm_equ.get(concepts[1]) + " ||| " + concepts[0]);
                        }else{
                            hm_equ.put(concepts[1], concepts[0]);
                        }    
                    }    
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
    
    // both inputs should have concept pairs separated using comma ",". 
    static String [] getCommonConcepts(String inputLearnedOntologyFileName, String splitcharLO, String inputGoldStdOntologyFileName, String splitcharGSO){
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        HashMap<String, Integer> hm_distinct_csc = new HashMap<String, Integer>();
        File fileLO = new File(inputLearnedOntologyFileName); // LO: Learned Ontology
        File fileGSO = new File(inputGoldStdOntologyFileName); // GSO: Gold Standard Ontology
        BufferedReader readerLO = null;
        BufferedReader readerGSO = null;
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
                    hm.put(concepts[0], 1);
                    hm.put(concepts[1], 1);
//                    if (hasSingleQuotes(concepts[0])){
//                        hm.put(removeSingleQuotes(concepts[0]), 1);
//                    }else{
//                        hm.put(concepts[0], 1);
//                    }
//                    if (hasSingleQuotes(concepts[1])){
//                        hm.put(removeSingleQuotes(concepts[1]), 1);
//                    }else{
//                        hm.put(concepts[1], 1);
//                    }
                }else{
                    if (concepts.length == 3){
                        hm.put(concepts[0], 1);
                        hm.put(concepts[2], 1);
                    }else{
                        System.out.println(tempString);
                    }
                }    
            }
            
            // using hm to check whether in the GSO there are any common concepts, and store these common concepts to hm_distinct_csc.
            while ((tempString = readerGSO.readLine()) != null) {
                tempString = tempString.toLowerCase();
                String [] concepts = tempString.split(splitcharGSO);
                //System.out.println(concepts[0] + " ||| " + concepts[1]);
                if (concepts.length == 2){
                    if (hm.containsKey(concepts[0])){
                        hm_distinct_csc.put(concepts[0], 1);
                    }
                    if (hm.containsKey(concepts[1])){
                        hm_distinct_csc.put(concepts[1], 1);
                    }
                    //hm.put(concepts[0], 1);
                    //hm.put(concepts[1], 1);
                }else{
                    System.out.println(tempString);
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
        
//        String [] commonconcepts = new String[hm_distinct_csc.size()];
//        Map<String, Integer> map = hm_distinct_csc;
//        int n = 0;
//        for (String key : map.keySet()) {
//            commonconcepts[n] = key;
//            n++;
//        }
        return getKeyArrayFromHashMap(hm_distinct_csc);
    }
    
    // with a list of equivalence concepts such as page redirect.
    static String [] getCommonConcepts(String inputLearnedOntologyFileName, String splitcharLO, String inputGoldStdOntologyFileName, String splitcharGSO, String inputEqualList, String splitcharEqual){
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        HashMap<String, Integer> hm_distinct_csc = new HashMap<String, Integer>();
        File fileLO = new File(inputLearnedOntologyFileName); // LO: Learned Ontology
        File fileGSO = new File(inputGoldStdOntologyFileName); // GSO: Gold Standard Ontology
        File fileEqu = new File(inputEqualList); // Equ: equivalent concept list
        BufferedReader readerLO = null;
        BufferedReader readerGSO = null;
        BufferedReader readerEqu = null;
        
        try {
            readerLO = new BufferedReader(new FileReader(fileLO));
            readerGSO = new BufferedReader(new FileReader(fileGSO));
            readerEqu = new BufferedReader(new FileReader(fileEqu));
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
                    hm.put(concepts[0], 1);
                    hm.put(concepts[1], 1);
//                    if (hasSingleQuotes(concepts[0])){
//                        hm.put(removeSingleQuotes(concepts[0]), 1);
//                    }else{
//                        hm.put(concepts[0], 1);
//                    }
//                    if (hasSingleQuotes(concepts[1])){
//                        hm.put(removeSingleQuotes(concepts[1]), 1);
//                    }else{
//                        hm.put(concepts[1], 1);
//                    }
                }else{
                    if (concepts.length == 3){
                        hm.put(concepts[0], 1);
                        hm.put(concepts[2], 1);
                    }else{
                        System.out.println(tempString);
                    }
                }    
            }
            
            // using hm to check whether in the GSO there are any common concepts, and store these common concepts to hm_distinct_csc.
            while ((tempString = readerGSO.readLine()) != null) {
                tempString = tempString.toLowerCase();
                String [] concepts = tempString.split(splitcharGSO);
                //System.out.println(concepts[0] + " ||| " + concepts[1]);
                if (concepts.length == 2){
                    if (hm.containsKey(concepts[0])){
                        hm_distinct_csc.put(concepts[0], 1);
                    }
                    if (hm.containsKey(concepts[1])){
                        hm_distinct_csc.put(concepts[1], 1);
                    }
                    //hm.put(concepts[0], 1);
                    //hm.put(concepts[1], 1);
                }else{
                    System.out.println(tempString);
                }
            }
            
            while ((tempString = readerEqu.readLine()) != null) {
                tempString = tempString.replace("_", " ");
                tempString = tempString.toLowerCase();
                String [] concepts = tempString.split(splitcharEqual);
                if (concepts.length == 2){
                    if (hm.containsKey(concepts[0])){
                        //System.out.println("[redir concept]" + concepts[0]);
                        hm_distinct_csc.put(concepts[0], 1);
                    }              
                }else{
                    //System.out.println(tempString);
                }
            }
            
            readerGSO.close();
            readerEqu.close();
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
        
//        String [] commonconcepts = new String[hm_distinct_csc.size()];
//        Map<String, Integer> map = hm_distinct_csc;
//        int n = 0;
//        for (String key : map.keySet()) {
//            commonconcepts[n] = key;
//            n++;
//        }
        return getKeyArrayFromHashMap(hm_distinct_csc);
    }
    
    static int getOntologyConceptSize(String inputOntologyFileName, String splitchar){
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        
        File fileO = new File(inputOntologyFileName); // Ontology
        BufferedReader readerO = null;
        try {
            readerO = new BufferedReader(new FileReader(fileO));
            String tempString = null;

            // storing a <- b_c_d, where _ means a white space, to the hashmap hm_a_b
            // storing b -> a_c_d, where _ means a white space, to the hashmap hm_b_a
            while ((tempString = readerO.readLine()) != null) {
                //tempString = removeSingleQuotesInConceptPair(tempString, splitchar);
                tempString = tempString.replace("_", " ");
                tempString = tempString.toLowerCase();
                String [] concepts = tempString.split(splitchar);
                
                if (concepts.length == 2){
                    hm.put(concepts[0], 1);
                    hm.put(concepts[1], 1);
                }else{
                    // case of "," at the tail of a tag.
                    if (concepts.length == 3){
                        hm.put(concepts[0],1);
                        hm.put(concepts[2], 1);
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
        
        return hm.size();
    }
    
    // get the Caracteristic Extract (ce) of a concept from an Ontology
    // this is done first by storing the ontology to two hashmaps, then by using another hashmap to get the distinct concepts as CE.
    static String [] getCaracteristicExcept(String concept, HashMap<String, String> hm_a_b, HashMap<String, String> hm_b_a){
        HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        //hm_distinct_ce.put(concept, 1); // put the concept itself.
        
        String [] upward_cotopy;
        String [] downward_cotopy;
        
        if (hm_a_b.containsKey(concept)){
            upward_cotopy = hm_a_b.get(concept).split(" \\|\\|\\| ");
            
            for (String concept_ce: upward_cotopy){
                hm_distinct_ce.put(concept_ce, 1);
            }
        }
        
        if (hm_b_a.containsKey(concept)){
            downward_cotopy = hm_b_a.get(concept).split(" \\|\\|\\| ");
            
            for (String concept_ce: downward_cotopy){
                hm_distinct_ce.put(concept_ce, 1);
            }
        }
        
        //String [] upward_cotopy = hm_a_b.get(concept).split(" ");
        //String [] downward_cotopy = hm_b_a.get(concept).split(" ");
        
        return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    static String [] getCaracteristicExcept(String concept, HashMap<String, String> hm_a_b, HashMap<String, String> hm_b_a, String CE_definition){
        if (CE_definition.equals("SC")){ // semantic cotopy as default
            return getCaracteristicExcept(concept, hm_a_b, hm_b_a);
        }
        
        HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        //hm_distinct_ce.put(concept, 1); // put the concept itself.
        
        String [] upward_cotopy;
        String [] downward_cotopy;
        
        if (CE_definition.equals("UC")){ // upward cotopy
            if (hm_a_b.containsKey(concept)){
                upward_cotopy = hm_a_b.get(concept).split(" \\|\\|\\| ");
            
                for (String concept_ce: upward_cotopy){
                    hm_distinct_ce.put(concept_ce, 1);
                }
            }
            //return getKeyArrayFromHashMap(hm_distinct_ce);
        } else if (CE_definition.equals("DC")){ // downward cotopy
            if (hm_b_a.containsKey(concept)){
                downward_cotopy = hm_b_a.get(concept).split(" \\|\\|\\| ");
                
                for (String concept_ce: downward_cotopy){
                    hm_distinct_ce.put(concept_ce, 1);
                }
            }
            //return getKeyArrayFromHashMap(hm_distinct_ce);
        }
        return getKeyArrayFromHashMap(hm_distinct_ce);
    }    
    
    // add a criterion: the characterstic except should only contain common concepts. [not used]
    static String [] getCaracteristicExcept(String concept, HashMap<String, String> hm_a_b, HashMap<String, String> hm_b_a, HashMap<String, Integer> hm_cc, String CE_definition){
        if (CE_definition.equals("SC")){ // semantic cotopy as default
            return getCaracteristicExcept(concept, hm_a_b, hm_b_a);
        }
        
        HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        //hm_distinct_ce.put(concept, 1); // put the concept itself.
        
        String [] upward_cotopy;
        String [] downward_cotopy;
        
        if (CE_definition.equals("UC")){ // upward cotopy
            if (hm_a_b.containsKey(concept)){
                upward_cotopy = hm_a_b.get(concept).split(" \\|\\|\\| ");
            
                for (String concept_ce: upward_cotopy){
                    if (hm_cc.containsKey(concept_ce)){
                        hm_distinct_ce.put(concept_ce, 1);
                    }    
                }
            }
            //return getKeyArrayFromHashMap(hm_distinct_ce);
        } else if (CE_definition.equals("DC")){ // downward cotopy
            if (hm_b_a.containsKey(concept)){
                downward_cotopy = hm_b_a.get(concept).split(" \\|\\|\\| ");
                
                for (String concept_ce: downward_cotopy){
                    if (hm_cc.containsKey(concept_ce)){
                        hm_distinct_ce.put(concept_ce, 1);
                    }    
                }
            }
            //return getKeyArrayFromHashMap(hm_distinct_ce);
        }
        return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    // the CSUB one, to be revised.
    static HashMap<String, Integer> getCaracteristicExceptCSUB(String concept, HashMap<String, String> hm_b_a, HashMap<String, Integer> hm_cc, HashMap<String, Integer> hm_distinct_ce){
        //HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        //System.out.println("searching for " + concept);
        if (hm_b_a.containsKey(concept)){
            String [] hyponyms = hm_b_a.get(concept).split(" \\|\\|\\| ");
            for (String hyponym: hyponyms){
                //System.out.println("having hyponym " + hyponym);
                if (hm_cc.containsKey(hyponym)){
                    hm_distinct_ce.put(hyponym, 1);
                    System.out.println(concept + " -> " + hyponym); //to test
                }else{
                    hm_distinct_ce = getCaracteristicExceptCSUB(hyponym, hm_b_a, hm_cc, hm_distinct_ce);
                }
            }
        }else{
            //System.out.println("not found " + concept);
        }
        return hm_distinct_ce;
        //return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    // specifying a level that prevent java.lang.StackOverflowError
    static HashMap<String, Integer> getCaracteristicExceptCSUB(String concept, HashMap<String, String> hm_b_a, HashMap<String, Integer> hm_cc, HashMap<String, Integer> hm_distinct_ce, int level){
        //HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        if (level == 0){
            return hm_distinct_ce;
        }
        //System.out.println("searching for " + concept);
        if (hm_b_a.containsKey(concept)){
            String [] hyponyms = hm_b_a.get(concept).split(" \\|\\|\\| ");
            hm_b_a.remove(concept); //prevent a cycle which causes dead loop
            for (String hyponym: hyponyms){
                //System.out.println("having hyponym " + hyponym);
                if (hm_cc.containsKey(hyponym)){
                    hm_distinct_ce.put(hyponym, 1);
                    System.out.println(concept + " -> " + hyponym); //to test
                }else{
                    hm_distinct_ce = getCaracteristicExceptCSUB(hyponym, hm_b_a, hm_cc, hm_distinct_ce,level-1);
                }
            }
        }else{
            //System.out.println("not found " + concept);
        }
        return hm_distinct_ce;
        //return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    static String [] getCaracteristicExceptCSUB(String concept, HashMap<String, String> hm_b_a, HashMap<String, Integer> hm_cc){
        HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        hm_distinct_ce =  getCaracteristicExceptCSUB(concept, hm_b_a, hm_cc, hm_distinct_ce);
        return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    // with level
    static String [] getCaracteristicExceptCSUB(String concept, HashMap<String, String> hm_b_a, HashMap<String, Integer> hm_cc, int level){
        if (level < 0){ //when level is a negative number, use limitless level, but may suffer from java.stackoverflowerror.
            return getCaracteristicExceptCSUB(concept, hm_b_a, hm_cc);
        }
        HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        hm_distinct_ce =  getCaracteristicExceptCSUB(concept, hm_b_a, hm_cc, hm_distinct_ce, level);
        return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    static HashMap<String, Integer> getCaracteristicExceptCUPWARD(String concept, HashMap<String, String> hm_a_b, HashMap<String, Integer> hm_cc, HashMap<String, Integer> hm_distinct_ce){
        //HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        //System.out.println("searching for " + concept);
        if (hm_a_b.containsKey(concept)){
            String [] hyponyms = hm_a_b.get(concept).split(" \\|\\|\\| ");
            for (String hyponym: hyponyms){
                //System.out.println("having hyponym " + hyponym);
                if (hm_cc.containsKey(hyponym)){
                    hm_distinct_ce.put(hyponym, 1);
                    System.out.println(concept + " <- " + hyponym); //to test
                }else{
                    hm_distinct_ce = getCaracteristicExceptCUPWARD(hyponym, hm_a_b, hm_cc, hm_distinct_ce);
                }
            }
        }else{
            //System.out.println("not found " + concept);
        }
        return hm_distinct_ce;
        //return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    // specifying a level that prevent java.lang.StackOverflowError
    static HashMap<String, Integer> getCaracteristicExceptCUPWARD(String concept, HashMap<String, String> hm_a_b, HashMap<String, Integer> hm_cc, HashMap<String, Integer> hm_distinct_ce, int level){
        //HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        if (level == 0){
            return hm_distinct_ce;
        }
        //System.out.println("searching for " + concept);
        if (hm_a_b.containsKey(concept)){
            String [] hyponyms = hm_a_b.get(concept).split(" \\|\\|\\| ");
            hm_a_b.remove(concept); //prevent a cycle which causes dead loop
            for (String hyponym: hyponyms){
                //System.out.println("having hyponym " + hyponym);
                if (hm_cc.containsKey(hyponym)){
                    hm_distinct_ce.put(hyponym, 1);
                    System.out.println(concept + " <- " + hyponym); //to test
                }else{
                    hm_distinct_ce = getCaracteristicExceptCUPWARD(hyponym, hm_a_b, hm_cc, hm_distinct_ce,level-1);
                }
            }
        }else{
            //System.out.println("not found " + concept);
        }
        return hm_distinct_ce;
        //return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    static String [] getCaracteristicExceptCUPWARD(String concept, HashMap<String, String> hm_a_b, HashMap<String, Integer> hm_cc){
        HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        hm_distinct_ce =  getCaracteristicExceptCUPWARD(concept, hm_a_b, hm_cc, hm_distinct_ce);
        return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    // with level
    static String [] getCaracteristicExceptCUPWARD(String concept, HashMap<String, String> hm_a_b, HashMap<String, Integer> hm_cc, int level){
        if (level < 0){ //when level is a negative number, use limitless level, but may suffer from java.stackoverflowerror.
            return getCaracteristicExceptCSUB(concept, hm_a_b, hm_cc);
        }
        HashMap<String, Integer> hm_distinct_ce = new HashMap<String, Integer>();
        hm_distinct_ce =  getCaracteristicExceptCUPWARD(concept, hm_a_b, hm_cc, hm_distinct_ce, level);
        return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    static String [] getCaracteristicExceptCSC(String concept, HashMap<String, String> hm_a_b, HashMap<String, String> hm_b_a, HashMap<String, Integer> hm_cc, int level){
        if (level < 0){ //when level is a negative number, use limitless level, but may suffer from java.stackoverflowerror.
            String [] csub = getCaracteristicExceptCSUB(concept, hm_b_a, hm_cc);
            String [] cupward = getCaracteristicExceptCUPWARD(concept, hm_a_b, hm_cc);
            return unionOf2Array(csub,cupward);
        }
        
        HashMap<String, Integer> hm_distinct_csub = new HashMap<String, Integer>();
        hm_distinct_csub =  getCaracteristicExceptCSUB(concept, hm_b_a, hm_cc, hm_distinct_csub, level);
        
        HashMap<String, Integer> hm_distinct_cupward = new HashMap<String, Integer>();
        hm_distinct_cupward =  getCaracteristicExceptCUPWARD(concept, hm_a_b, hm_cc, hm_distinct_cupward, level);
        
        return unionOf2Array(getKeyArrayFromHashMap(hm_distinct_csub),getKeyArrayFromHashMap(hm_distinct_cupward));
    }
    
    private static String [] unionOf2Array(String [] arr1, String [] arr2){
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        if (arr1 == null){
            if (arr2 == null){
                return null;
            }else{
                return arr2;
            }
        }else{
            if (arr2 == null){
                return arr1;
            }else{
                for (String ele: arr1){
                    hm.put(ele, 1);
                }
                for (String ele: arr2){
                    hm.put(ele, 1);
                }
                return getKeyArrayFromHashMap(hm);
            }
        }
        
    }
//    
//    static String traversal2findCSUB(String concept, HashMap<String, String> hm_b_a, HashMap<String, Integer> hm_cc){
//        if (hm_b_a.containsKey(concept)){
//            String [] hyponyms = hm_b_a.get(concept).split(" \\|\\|\\| ");
//            for (String hyponym: hyponyms){
//                if (hm_cc.containsKey(hyponym)){
//                    return hyponym;
//                }else{
//                    
//                }
//            }
//        }
//        return "";
//    }
    
    
    // from the redirected to redirect, from the standard in KB to the one matche to the tag concept
    static String [] redirise(String [] GSO_ce, HashMap<String,String> hm_GSO_a_b, HashMap<String,String> hm_GSO_b_a, HashMap<String,String> hm_GSO_equ_std){
        HashMap<String,Integer> hm_distinct_ce = new HashMap<String,Integer>();
        for (String concept: GSO_ce){
            if (hm_GSO_equ_std.containsKey(concept)){
                String [] redirs = hm_GSO_equ_std.get(concept).split(" \\|\\|\\| ");
                for (String redir: redirs){
                    hm_distinct_ce.put(redir, 1);
                }
            }else if (hm_GSO_a_b.containsKey(concept) || hm_GSO_b_a.containsKey(concept)){
                hm_distinct_ce.put(concept, 1);
            }
        }
        return getKeyArrayFromHashMap(hm_distinct_ce);
    }
    
    // from the redirected to redirect, from the standard in KB to the one matche to the tag concept
    static String [] redirise(String [] GSO_ce, HashMap<String,String> hm_GSO_equ_std){
        HashMap<String,Integer> hm_distinct_ce = new HashMap<String,Integer>();
        for (String concept: GSO_ce){
            if (hm_GSO_equ_std.containsKey(concept)){
                String [] redirs = hm_GSO_equ_std.get(concept).split(" \\|\\|\\| ");
                for (String redir: redirs){
                    hm_distinct_ce.put(redir, 1);
                }
            }else{
                hm_distinct_ce.put(concept, 1);
            }
        }
        return getKeyArrayFromHashMap(hm_distinct_ce);
    }
            
    static String [] getUnionArr(String [] arr1, String [] arr2){
        HashMap<String, Integer> hm_union = new HashMap<String, Integer>();
        for (String concept: arr1){
            hm_union.put(concept, 1);
        }
        
        for (String concept: arr2){
            hm_union.put(concept, 1);
        }
        
        return getKeyArrayFromHashMap(hm_union);
    }
            
    static int getTaxonomicPrecision(){
        return 0;
    }
    
    static int getTaxonomicRecall(){
        return 0;
    }
    
    static String [] getKeyArrayFromHashMap(HashMap hm){
        if (hm.size() == 0){
            return null;
        }
        String [] arr = new String[hm.size()];
        Map<String, Object> map = hm;
        int n = 0;
        for (String key : map.keySet()) {
            arr[n] = key;
            n++;
        }
        return arr;
    }
    
    static String removeSingleQuotes(String concept){
        return concept.substring(1, concept.length()-1);
    }
    
    public static String removeSingleQuotesInConceptPair(String conceptpair, String splitchar){
        String [] concepts = conceptpair.split(splitchar);
        if (concepts.length == 2){
            if (hasSingleQuotes(concepts[0])){
                concepts[0] = concepts[0].substring(1,concepts[0].length()-1);
            }
            if (hasSingleQuotes(concepts[1])){
                concepts[1] = concepts[1].substring(1,concepts[1].length()-1);
            }
            //concepts[0] = 1;
            conceptpair = concepts[0] + splitchar + concepts[1];
        }else if (concepts.length == 3){
            if (hasSingleQuotes(concepts[0])){
                concepts[0] = concepts[0].substring(1,concepts[0].length()-1);
            }
            if (hasSingleQuotes(concepts[1])){
                concepts[1] = concepts[1].substring(1,concepts[1].length()-1);
            }
            if (hasSingleQuotes(concepts[2])){
                concepts[2] = concepts[2].substring(1,concepts[2].length()-1);
            }
            //concepts[0] = 1;
            conceptpair = concepts[0] + splitchar + concepts[1] + splitchar + concepts[2];
        }
        return conceptpair;
    } 
    
    public static boolean hasSingleQuotes(String concept){
        if ((int)concept.charAt(0) == 39 && (int)concept.charAt(concept.length()-1) == 39){
            return true;
        }else{
            //System.out.println((int)concept.charAt(0));
            //System.out.println((int)concept.charAt(concept.length()-1));
            return false;
        }    
    }
    
    public static String formatPercent(double done, int digits) {
        DecimalFormat percentFormat = new DecimalFormat("0.0%");
        percentFormat.setDecimalSeparatorAlwaysShown(false);
        percentFormat.setMinimumFractionDigits(digits);
        percentFormat.setMaximumFractionDigits(digits);
        return percentFormat.format(done);
    }
}
