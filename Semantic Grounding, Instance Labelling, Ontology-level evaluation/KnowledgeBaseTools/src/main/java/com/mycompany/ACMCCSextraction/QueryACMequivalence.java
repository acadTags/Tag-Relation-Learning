/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ACMCCSextraction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.SKOS;

/** output a file of all equivalence relations between standard concepts and alternative concepts
 *
 * @author cleme
 */
public class QueryACMequivalence {
    
    static public final String NL = System.getProperty("line.separator") ; 
    
    public static void main(String [] args){
        
        File fileW = new File("acm_data_equivalence_new.txt");
        BufferedWriter writer = null;
        
        Model model = RDFDataMgr.loadModel("ACMComputingClassificationSystemSKOSTaxonomy.xml");
        //model.
        System.out.println("load successful");
        
        // First part or the query string 
        String prolog = "PREFIX skos: <"+SKOS.getURI()+">" ;
        
        // Query string.
        String queryString = prolog + NL +
            "SELECT DISTINCT ?label1 ?label2 WHERE {"
                + "?concept skos:prefLabel ?label1."
                + "?concept skos:altLabel ?label2."
                + "}" ;
        
        Query query = QueryFactory.create(queryString) ;
        // Print with line numbers
        query.serialize(new IndentedWriter(System.out,true)) ;
        System.out.println() ;
        
        // Create a single execution of this query, apply to a model
        // which is wrapped up as a Dataset
        
        // Or QueryExecutionFactory.create(queryString, model) ;        
        try(QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            // A ResultSet is an iterator - any query solutions returned by .next()
            // are not accessible again.
            // Create a ResultSetRewindable that can be reset to the beginning.
            // Do before first use.
            
//            ResultSetRewindable rewindable = ResultSetFactory.makeRewindable(qexec.execSelect()) ;
//            ResultSetFormatter.out(rewindable) ;
//            rewindable.reset() ;
//            ResultSetFormatter.out(rewindable) ;
            //System.out.println("Titles: ") ;

            // Assumption: it's a SELECT query.
            ResultSet rs = qexec.execSelect() ;
            
            try{
                writer = new BufferedWriter(new FileWriter(fileW));
                // The order of results is undefined. 
                for ( ; rs.hasNext() ; )
                {
                    QuerySolution rb = rs.nextSolution() ;

//                    String narrowconcept = rb.get("narrowconcept").toString();
//    //                if (narrowconcept.indexOf("http://dbpedia/resource/") == 0){
//    //                    narrowconcept = narrowconcept.substring(len("http://dbpedia/resource/"));
//    //                }
//                    String broaderconcept = rb.get("broaderconcept").toString();
//                    
//                    //remove @en after narrowconcept and broaderconcept.
//                    if (narrowconcept.substring(narrowconcept.length()-3).equals("@en")){
//                        narrowconcept = narrowconcept.substring(0,narrowconcept.length()-3);
//                    }else{
//                        System.out.print(narrowconcept);//for testing
//                    }
//                    
//                    if (broaderconcept.substring(broaderconcept.length()-3).equals("@en")){
//                        broaderconcept = broaderconcept.substring(0,broaderconcept.length()-3);
//                    }else{
//                        System.out.print(broaderconcept);//for testing
//                    }
                    
                    String label1 = rb.get("label1").toString();
                    label1 = label1.substring(0, label1.length()-3).trim();
                    
                    String label2 = rb.get("label2").toString();
                    label2 = label2.substring(0, label2.length()-3).trim();
                    
                    System.out.println(label2 + " = " + label1);
                    writer.write(label2 + " = " + label1);
                    writer.newLine();
                    
                    //String label3 = rb.get("label3").toString();
                    //label3 = label3.substring(0, label3.length()-3).trim();
                    
//                    System.out.println(label1 + " = " + label3);
//                    writer.write(label1 + " = " + label3);
//                    writer.newLine();
                    
//                    if (!label2.equals(label3)){
//                        System.out.println(label2 + " = " + label3);
//                        writer.write(label2 + " = " + label3);
//                        writer.newLine();
//                    }
                }
                writer.flush();
                writer.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        
        RemoveRedundancy("","acm_data_equivalence_reversed.txt");
    }
    
    public static void RemoveRedundancy(String path, String InputFileName){
        System.out.println(InputFileName);
        File file = new File(path + InputFileName);
        File fileW = new File(path + InputFileName.substring(0, InputFileName.length()-4) + "_reduced.txt");
        BufferedReader reader = null;
        BufferedWriter writer = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(fileW));
            
            HashMap<String, Integer> hm = new HashMap<String, Integer>();
            
            String tempString;
            
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                if (!hm.containsKey(tempString)){
                    hm.put(tempString, 1);
                }else{
                    hm.put(tempString, hm.get(tempString) + 1);
                    //System.out.println(line + " " + tempString);
                }
                line++;
            }
            
            Set<Map.Entry<String, Integer>> set = hm.entrySet();
        
            //Iterate through Entry 
            for(Map.Entry<String, Integer> en: set){
                //if (en.getValue() == 1){
                    if (!AreSameConcepts(en.getKey())){
                        writer.write(en.getKey());
                        writer.newLine();
                    }
                    
                    if (en.getValue() > 1){
                        System.out.println(en.getKey());
                    }
                //}else{
                //    System.out.println(en.getKey());
                //}
            }
            
            writer.flush();
            writer.close();
        } catch (Exception e) {
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
    
    public static boolean AreSameConcepts(String tagpair){
        String [] concepts = tagpair.split(" = ");
        if (concepts.length == 2){
            // check whether the concept contains white space.
//            if (concepts[0].indexOf(" ") > 0 || concepts[1].indexOf(" ") > 0){
//                System.out.println("[problem]: white space in concept in " + tagpair);
//            }
            
            if (concepts[0].equals(concepts[1])){
                return true;
            }else{
                return false;
            }
        }else{
            System.out.println("[problem]: length not 2 with " + tagpair);
            return false;
        }
    }
}
