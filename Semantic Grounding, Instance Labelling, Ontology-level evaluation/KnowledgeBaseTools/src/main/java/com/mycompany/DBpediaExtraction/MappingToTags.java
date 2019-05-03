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
import static java.lang.Math.min;
import static java.lang.Math.max;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.apache.jena.riot.system.StreamRDFLib.writer;

/** [important file] Mapping the taxonomy to tags.
 * Input: tag list and a DBpedia taxonomy created from QueryingUpperSKOScategories.java and QueryingLowerDCTpages.java.
 * Output: a mapped tag hierarchy.
 * @author Hang Dong
 */
public class MappingToTags {
    public static void main(String [] args){
//        System.out.println(new Date( ));
//        MappingToTags("semantic_web_sub_all_data.txt",MapDBpediaConceptToTag("semantic_web_sub_all_data.txt","tag list.txt",2));
//        System.out.println(new Date( ));
//        MappingToTags("machine_learning_sub_all_data.txt",MapDBpediaConceptToTag("machine_learning_sub_all_data.txt","tag list.txt",2));
//        System.out.println(new Date( ));
//        MappingToTags("data_minin_sub_all_data.txt",MapDBpediaConceptToTag("data_minin_sub_all_data.txt","tag list.txt",2));
//        System.out.println(new Date( ));
//        MappingToTags("natural_la_sub_all_data.txt",MapDBpediaConceptToTag("natural_la_sub_all_data.txt","tag list.txt",2));
//        System.out.println(new Date( ));
//        MappingToTags("social_inf_sub_all_data.txt",MapDBpediaConceptToTag("social_inf_sub_all_data.txt","tag list.txt",2));
//        System.out.println(new Date( ));
//        MappingToTags("internet_o_sub_all_data.txt",MapDBpediaConceptToTag("internet_o_sub_all_data.txt","tag list.txt",2));
//        MappingToTags("library_sc_sub_all_data.txt",MapDBpediaConceptToTag("library_sc_sub_all_data.txt","tag list.txt",2));
        
        // Get all tag_concept-DBpedia_concepts mappings in a csv. file. [not useful].
        //GetAllConceptMapping("the_six_categories_sub_all_data.txt","tag list.txt",2);
        
        //HashMap<String,String> dbo_redir = LoadRedirectsData("all_page_redirect_data_dbpedia_url_name.txt");
        //HashMap<String,String> dbo_redir = null;
        
//        MappingToTags("semantic_web_sub_all_data.txt",MapDBpediaConceptToTag("semantic_web_sub_all_data.txt",dbo_redir,"tag list.txt",2));
//        MappingToTags("machine_learning_sub_all_data.txt",MapDBpediaConceptToTag("machine_learning_sub_all_data.txt",dbo_redir,"tag list.txt",2));
//        MappingToTags("data_minin_sub_all_data.txt",MapDBpediaConceptToTag("data_minin_sub_all_data.txt",dbo_redir,"tag list.txt",2));
//        MappingToTags("natural_la_sub_all_data.txt",MapDBpediaConceptToTag("natural_la_sub_all_data.txt",dbo_redir,"tag list.txt",2));
//        MappingToTags("social_inf_sub_all_data.txt",MapDBpediaConceptToTag("social_inf_sub_all_data.txt",dbo_redir,"tag list.txt",2));
//        MappingToTags("internet_o_sub_all_data.txt",MapDBpediaConceptToTag("internet_o_sub_all_data.txt",dbo_redir,"tag list.txt",2));
//        MappingToTags("library_sc_sub_all_data.txt",MapDBpediaConceptToTag("library_sc_sub_all_data.txt",dbo_redir,"tag list.txt",2));

        //MappingToTags("human_computer_interaction_sub_all_data.txt",MapDBpediaConceptToTag("human_computer_interaction_sub_all_data.txt",dbo_redir,"tag list.txt",2));
        //MappingToTags("information_retrieval_sub_all_data.txt",MapDBpediaConceptToTag("information_retrieval_sub_all_data.txt",dbo_redir,"tag list.txt",1));
        //MappingToTags("genetics_sub_all_data.txt",MapDBpediaConceptToTag("genetics_sub_all_data.txt",dbo_redir,"tag list.txt",1));
        //MappingToTags("computational_chemistry_sub_all_data.txt",MapDBpediaConceptToTag("computational_chemistry_sub_all_data.txt",dbo_redir,"tag list.txt",2));
        //MappingToTags("chemistry_sub_all_data.txt",MapDBpediaConceptToTag("chemistry_sub_all_data.txt",dbo_redir,"tag list.txt",2));
        //MappingToTags("neural_network_sub_all_data.txt",MapDBpediaConceptToTag("neural_network_sub_all_data.txt",dbo_redir,"tag list.txt",1));
        //MappingToTags("big_data_sub_all_data.txt",MapDBpediaConceptToTag("big_data_sub_all_data.txt",dbo_redir,"tag list.txt",1));
        //MappingToTags("user_interaces_sub_all_data.txt",MapDBpediaConceptToTag("user_interaces_sub_all_data.txt",dbo_redir,"taglist_tf3.csv",2));
        //MappingToTags("social_networks_sub_all_data.txt",MapDBpediaConceptToTag("social_networks_sub_all_data.txt",dbo_redir,"taglist_tf3.csv",2));
        //MappingToTags("knowledge_management_sub_all_data.txt",MapDBpediaConceptToTag("knowledge_management_sub_all_data.txt",dbo_redir,"taglist_tf3.csv",2));
        //MappingToTags("databases_sub_all_data.txt",MapDBpediaConceptToTag("databases_sub_all_data.txt",dbo_redir,"taglist_tf3.csv",2));
        //MappingToTags("web_development_sub_all_data.txt",MapDBpediaConceptToTag("web_development_sub_all_data.txt",dbo_redir,"taglist_tf3.csv",2));
        //MappingToTags("algorithms_sub_all_data.txt",MapDBpediaConceptToTag("algorithms_sub_all_data.txt",dbo_redir,"taglist_tf3.csv",2));
        //System.out.println(minimumEditedDistance("k_l","k+l"));
        
// only map the categories
        //MappingToTags("semantic_web_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("semantic_web_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        //MappingToTags("machine_learning_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("machine_learning_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        //MappingToTags("data_mining_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("data_mining_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        //MappingToTags("natural_language_processing_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("natural_language_processing_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        ////MappingToTags("information_science_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("information_science_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        ////MappingToTags("internet_of_things_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("internet_of_things_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        //MappingToTags("genetics_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("genetics_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        //MappingToTags("databases_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("databases_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        ////MappingToTags("chemistry_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("chemistry_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        //MappingToTags("semantic_web_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("semantic_web_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        //MappingToTags("software_engineering_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("software_engineering_sub_tree_wiki_cat.txt",dbo_redir,"taglist_tf3_new.csv",2));
        //MappingToTags("mathematics_sub_tree_wiki_cat_reduced.txt",MapDBpediaConceptToTag("mathematics_sub_tree_wiki_cat_reduced.txt",dbo_redir,"taglist_tf3_new.csv",1));
        
        //System.out.println(removeFirstParenthesis(removeCategory("Lift_(data_mining)")));
        //System.out.println(minimumEditedDistance(removeFirstParenthesis(removeCategory("Lift_(data_mining)")),"lift",1));

// only map the categories, without redirect info, edited distance 1
//        MappingToTags("semantic_web_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("semantic_web_sub_tree_wiki_cat.txt","taglist_tf3_new.csv",1));
//        MappingToTags("machine_learning_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("machine_learning_sub_tree_wiki_cat.txt","taglist_tf3_new.csv",1));
//        MappingToTags("data_mining_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("data_mining_sub_tree_wiki_cat.txt","taglist_tf3_new.csv",1));
//        MappingToTags("natural_language_processing_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("natural_language_processing_sub_tree_wiki_cat.txt","taglist_tf3_new.csv",1));
//        MappingToTags("internet_of_things_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("internet_of_things_sub_tree_wiki_cat.txt","taglist_tf3_new.csv",1));
//        MappingToTags("genetics_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("genetics_sub_tree_wiki_cat.txt","taglist_tf3_new.csv",1));
//        MappingToTags("databases_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("databases_sub_tree_wiki_cat.txt","taglist_tf3_new.csv",1));
//        MappingToTags("software_engineering_sub_tree_wiki_cat.txt",MapDBpediaConceptToTag("software_engineering_sub_tree_wiki_cat.txt","taglist_tf3_new.csv",1));
//        MappingToTags("mathematics_sub_tree_wiki_cat_reduced.txt",MapDBpediaConceptToTag("mathematics_sub_tree_wiki_cat_reduced.txt","taglist_tf3_new.csv",1));

        //MappingToTags("databases_sub_all_data.txt",MapDBpediaConceptToTag("databases_sub_all_data.txt","taglist_tf3_new.csv",1));
        //MappingToTags("semantic_web_sub_all_data.txt",MapDBpediaConceptToTag("semantic_web_sub_all_data.txt","taglist_tf3_new.csv",1));
        //MappingToTags("machine_learning_sub_all_data.txt",MapDBpediaConceptToTag("machine_learning_sub_all_data.txt","taglist_tf3_new.csv",1));
        //MappingToTags("data_minin_sub_all_data.txt",MapDBpediaConceptToTag("data_minin_sub_all_data.txt","taglist_tf3_new.csv",1));
        //MappingToTags("natural_la_sub_all_data.txt",MapDBpediaConceptToTag("natural_la_sub_all_data.txt","taglist_tf3_new.csv",1));
        MappingToTags("genetics_sub_all_data.txt",MapDBpediaConceptToTag("genetics_sub_all_data.txt","taglist_tf3_new.csv",1));
        MappingToTags("software_engineering_sub_all_data.txt",MapDBpediaConceptToTag("software_engineering_sub_all_data.txt","taglist_tf3_new.csv",1));
        MappingToTags("mathematics_sub_all_data.txt",MapDBpediaConceptToTag("mathematics_sub_all_data.txt","taglist_tf3_new.csv",1));
    }
    
    // store wiki page redirect data into hashmap, where key is the standard concept and value is the various forms of the standard concept, separated using " ||| ".
    public static HashMap<String,String> LoadRedirectsData(String InputPageRedirectTTLfileName){
        File fileRedirect = new File(InputPageRedirectTTLfileName);
        BufferedReader readerRedirect = null;
        HashMap <String, String> dbo_redir = new HashMap <String, String>();
        try{
            readerRedirect = new BufferedReader(new FileReader(fileRedirect));
            String tempString = null;
            
            while ((tempString = readerRedirect.readLine()) != null) {
                String [] concepts = tempString.split(" = ");
                if (concepts.length == 2){
                    if (dbo_redir.containsKey(concepts[1])){
                        dbo_redir.put(concepts[1], dbo_redir.get(concepts[1]) + " ||| " + concepts[0]);
                    }else{
                        dbo_redir.put(concepts[1], concepts[0]);
                    }
                }else{
                    System.out.println("[loading redirect data error]: wrong format");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readerRedirect != null) {
                try {
                    readerRedirect.close();
                } catch (IOException e1) {
                }
            }
        }
        System.out.println("load redirects data successful");
        return dbo_redir;
    }
            
    //return all tags mapped to any DBpedia concepts from a dbpedia category tree file.
    //output the tag-dbpedia mapping results as a csv format text file.
    // [not used]
    public static void GetAllConceptMapping(String InputDBpediaTreeFileName, String InputTagListFileName, int edit_dist_theshold){
        String OutputFileName = InputDBpediaTreeFileName.substring(0,InputDBpediaTreeFileName.length()-4) + "_mapped_to_tags.csv";
        File fileW = new File(OutputFileName);
        BufferedWriter writer = null;
        HashMap <String, String> htagdb = MapTagToDBpediaConcepts(InputDBpediaTreeFileName, InputTagListFileName, edit_dist_theshold);
        Set<Map.Entry<String, String>> set = htagdb.entrySet();
        try{
            writer = new BufferedWriter(new FileWriter(fileW));
            //Iterate through Entry 
            for(Map.Entry<String, String> en: set){
                writer.write(en.getKey() + "," + en.getValue());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }    
    }
    
    //will output to a txt file, ended by "_matched_to_tags.txt".
    public static void MappingToTags(String InputDBpediaTreeFileName, HashMap<String,String> hdbtag){
        System.out.println("start mapping concept pairs");
        File filetree = new File(InputDBpediaTreeFileName);
        //File filetag = new File(InputTagList);
        String OutputFileName = InputDBpediaTreeFileName.substring(0,InputDBpediaTreeFileName.length()-4) + "_matched_to_tags_noredir_[original]_ed1.txt";
        File fileW = new File(OutputFileName);
        BufferedWriter writer = null;
        BufferedReader readertree = null;
        try{
            readertree = new BufferedReader(new FileReader(filetree));
            writer = new BufferedWriter(new FileWriter(fileW));
            String tempString = null;

            while ((tempString = readertree.readLine()) != null) {
                String [] concepts = tempString.split(" <- ");
                if (hdbtag.containsKey(concepts[0]) && hdbtag.containsKey(concepts[1])){
                    //System.out.println(tempString);
                    //System.out.println(hdbtag.get(concepts[0]) + " <- " + hdbtag.get(concepts[1]));
                    writer.write(tempString);
                    writer.newLine();
                    writer.write(hdbtag.get(concepts[0]) + " <- " + hdbtag.get(concepts[1]));
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readertree != null) {
                try {
                    readertree.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    
    public static HashMap<String,String> MapDBpediaConceptToTag(String InputDBpediaTreeFileName, String InputTagList, int edit_dist_theshold){
        System.out.println("start mapping dbpedia concepts to tags");
        //File filetree = new File(InputDBpediaTreeFileName);
        File filetag = new File(InputTagList);
        //BufferedReader readertree = null;
        BufferedReader readertag = null;
        //BufferedWriter writer = null;
        HashMap<String,Integer> hm = getDistinctConcepts(InputDBpediaTreeFileName);
        Object [] db_ob = hm.keySet().toArray();
        int num_dbc = db_ob.length;
        System.out.println("There are " + num_dbc + " DBpedia concepts in total for matching.");
        HashMap<String,String> hdbtag = new HashMap<String,String>();
        
        try{
            readertag = new BufferedReader(new FileReader(filetag));
            String tag = null;
            int num_tag=0;
            while ((tag = readertag.readLine()) != null) {
                num_tag++;
                if (num_tag % 500 == 0){
                    System.out.println(num_tag);
                }
                for (Object dbc: db_ob){
                    String dbc_str = dbc.toString();
                    if (minimumEditedDistance(removeFirstParenthesis(removeCategory(dbc_str),false),tag,1) <= min(max(tag.length()-3,0),edit_dist_theshold)){
                        //System.out.println(tag.toString() + " [mapped to] " + tempString);
                        if (!hdbtag.containsKey(dbc_str)){
                            hdbtag.put(dbc_str,tag);
                        }else{
                            hdbtag.put(dbc_str, hdbtag.get(dbc_str) + " " + tag);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readertag != null) {
                try {
                    readertag.close();
                } catch (IOException e1) {
                }
            }
            System.out.println("finished mapping dbpedia concepts to tags");
            return hdbtag;
        }
    }
    
    // MapDBpediaConceptToTag with redirects
    public static HashMap<String,String> MapDBpediaConceptToTag(String InputDBpediaTreeFileName, HashMap<String,String> InputRedirectHashMap, String InputTagList, int edit_dist_theshold){
        System.out.println(InputDBpediaTreeFileName);
        System.out.println("start mapping dbpedia concepts to tags");
        //File filetree = new File(InputDBpediaTreeFileName);
        File filetag = new File(InputTagList);
        //BufferedReader readertree = null;
        BufferedReader readertag = null;
        //BufferedWriter writer = null;
        HashMap<String,Integer> hm = getDistinctConcepts(InputDBpediaTreeFileName);
        Object [] db_ob = hm.keySet().toArray();
        int num_dbc = db_ob.length;
        System.out.println("There are " + num_dbc + " DBpedia concepts in total for matching.");
        //HashMap<String,String> dbo_redir = LoadRedirectsData(InputRedirectHashMap);
        HashMap<String,String> dbo_redir = InputRedirectHashMap;
                
        HashMap<String,String> hdbtag = new HashMap<String,String>();
        try{
            readertag = new BufferedReader(new FileReader(filetag));
            String tag = null;
            String redirect_output;
            //int n=0;
            int num_tag = 0;
            while ((tag = readertag.readLine()) != null) {
                num_tag++;
                if (num_tag % 500 == 0){
                    System.out.println(num_tag);
                }
                //System.out.println(tag);
                //for (Object dbc: db_ob){
                for (int i=0;i<db_ob.length;i++){
                    //n++;
                    //System.out.println((i+1) + " out of " + num_dbc);
                    String dbc_str = db_ob[i].toString();
                    String cleaned_dbc_str = removeCategory(dbc_str);
                    //System.out.println(dbc_str + " ||| " + cleaned_dbc_str);
                    //System.out.println(dbo_redir.containsKey(cleaned_dbc_str));
                    //String dbc_str_to_compare = removeFirstParenthesis(removeCategory(dbc_str));
                    if (minimumEditedDistance(removeFirstParenthesis(removeCategory(dbc_str),false),tag,1) <= min(max(tag.length()-3,0),edit_dist_theshold)){
                        //here changed min(tag.length()-3,edit_dist_theshold) to min(max(tag.length()-3,0),edit_dist_theshold), 
                        //further revised to min(Math.abs(tag.length()-3),edit_dist_theshold) on July 21, 2017.
                        //System.out.println(tag.toString() + " [mapped to] " + tempString);
                        if (!hdbtag.containsKey(dbc_str)){
                            hdbtag.put(dbc_str,tag);
                        }else{
                            hdbtag.put(dbc_str, hdbtag.get(dbc_str) + " " + tag);
                        }
                    
                    // if this tag was not mapped to the standard concept, try to map it to the redirected concepts.
                    }else if (dbo_redir.containsKey(cleaned_dbc_str)){
                        String [] redirected_concepts = dbo_redir.get(cleaned_dbc_str).split(" \\|\\|\\| ");
                        for (String concept: redirected_concepts){
                            if (minimumEditedDistance(removeFirstParenthesis(removeCategory(concept),false),tag,1) <= min(tag.length()-3,edit_dist_theshold)){
                                //System.out.println(tag + " [mapped to redirected] " + concept + " = " + dbc_str);
                                redirect_output = tag + "[" + concept + "=" + dbc_str + "]";
                                if (!hdbtag.containsKey(dbc_str)){                                    
                                    hdbtag.put(dbc_str,redirect_output);
                                }else{
                                    hdbtag.put(dbc_str, hdbtag.get(dbc_str) + " " + redirect_output);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readertag != null) {
                try {
                    readertag.close();
                } catch (IOException e1) {
                }
            }
            System.out.println("finished mapping dbpedia concepts to tags");
            return hdbtag;
        }
    }
    
    //this method is added to generate a list of tags where each of them is matched to any least one DBpedia concept.
    public static HashMap<String,String> MapTagToDBpediaConcepts(String InputDBpediaTreeFileName, String InputTagListFileName, int edit_dist_theshold){
        System.out.println("start mapping tags to DBpedia concepts");
        
        // get distinct dbpedia concepts
        HashMap<String,Integer> hm = getDistinctConcepts(InputDBpediaTreeFileName);
        Object [] db_ob = hm.keySet().toArray();
        
        // get distinct tags from the tag list file
        String [] taglist = getTagListFromFile(InputTagListFileName);
        
        // hashmap to store the tag-dbconceptA_dbconceptB_dbconceptC key-value sets,
        // where "_" is a white space
        HashMap<String,String> htagdb = new HashMap<String,String>();
        
        for (String tag: taglist){
            for (Object dbc: db_ob){
                String dbc_str = dbc.toString();
                if (minimumEditedDistance(removeFirstParenthesis(removeCategory(dbc_str)),tag,1) <= min(tag.length(),edit_dist_theshold)){
                    if (!htagdb.containsKey(tag)){
                        htagdb.put(tag, dbc_str);
                    }else{
                        htagdb.put(tag, htagdb.get(tag) + " " + dbc_str);
                    }
                }
            }
        }
        
        return htagdb;
    }
    
    //get tag list from file. Note in the file, all tags are distinct from each other, 
    //and each tag should be on a single line.
    public static String [] getTagListFromFile(String InputTagListFileName){
        File filetag = new File(InputTagListFileName);
        BufferedReader readertag = null;
        String [] taglist = null;
        try{
            readertag = new BufferedReader(new FileReader(filetag));
            String tag = null;
            
            // get line number which is the number of distinct tags.
            int line = 0;
            while ((tag = readertag.readLine()) != null) {
                line++;
            }
            
            readertag.close();
            taglist = new String[line];
            
            readertag = new BufferedReader(new FileReader(filetag));
            line = 0;
            while ((tag = readertag.readLine()) != null) {
                taglist[line] = tag;
                line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readertag != null) {
                try {
                    readertag.close();
                } catch (IOException e1) {
                }
            }
        }
        return taglist;
    }
    
    public static HashMap<String,Integer> getDistinctConcepts(String InputDBpediaTreeFileName){
        File fileupper = new File(InputDBpediaTreeFileName);
        BufferedReader readerupper = null;
        HashMap<String, Integer> ht = new HashMap<String, Integer>();
        try {
            readerupper = new BufferedReader(new FileReader(fileupper));
            String tempString = null;
            while ((tempString = readerupper.readLine()) != null) {
                String [] concepts = tempString.split(" <- ");
                for (String concept: concepts){
                    ht.put(concept, 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readerupper != null) {
                try {
                    readerupper.close();
                } catch (IOException e1) {
                }
            }
        }
        return ht;
    }
    
    public static String removeFirstParenthesis(String target){
        int lp = target.indexOf("(");
        int rp = target.indexOf(")");
        String changed;
        if (lp>0 && rp>0 && rp>lp){
            changed= (target.substring(0,lp) + target.substring(rp+1)).trim();
            if (changed.charAt(changed.length()-1) == '_'){
                return changed.substring(0,changed.length()-1);
            }else{
                return changed;
            }
        }else{
            return target;
        }
    }
    
    public static String removeFirstParenthesis(String target, boolean activation){
        if (activation){
            return removeFirstParenthesis(target);
        }else{
            return target;
        }
    }

        
    public static String removeCategory(String concept){
        if (concept.indexOf("Category:") == 0){
            return concept.substring(9);
        }else{
            return concept;
        }
    }
    
    public static int minimumEditedDistance(String target, String source){
        return minimumEditedDistance(target, source, 2);
    }
    
    public static int minimumEditedDistance(String target, String source, int SubCost){
        int n = target.length();
        int m = source.length();
        int [][] distance = new int[n+1][m+1];
        distance[0][0] = 0;
        
        for (int i=1;i<=n;i++){
            distance[i][0] = distance[i-1][0] + 1;
        }
        for (int j=1;j<=m;j++){
            distance[0][j] = distance[0][j-1] + 1;
        }
        
        for (int i=1;i<=n;i++){
            for (int j=1;j<=m;j++){
                distance[i][j] = min(min(distance[i-1][j] + 1, 
                        distance[i-1][j-1] + getSubCost(target.charAt(i-1),source.charAt(j-1),SubCost)),
                        distance[i][j-1] + 1);
            }
        }
        return distance[n][m];
    }
    
    private static int getSubCost(char target, char source, int SubCost){
        // Get the subcost, while ignoring the case issue.
        if ((target == source) || (target+"").toUpperCase().equals((source + "").toUpperCase())){
            return 0;
        }else{
            return SubCost;
        }
    }
}
