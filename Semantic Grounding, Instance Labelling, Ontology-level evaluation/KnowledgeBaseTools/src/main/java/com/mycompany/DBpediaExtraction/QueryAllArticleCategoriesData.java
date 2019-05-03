/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.DBpediaExtraction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.DCTerms;

/**
 *
 * @author hong.dong
 */
public class QueryAllArticleCategoriesData {
    static public final String NL = System.getProperty("line.separator") ; 
    
    public static void main(String [] args){
        generateTxtFile("[part1]article_categories_en.ttl","all_article_categories_data_part1.txt");
        System.out.println("[part1] done.");
        generateTxtFile("[part2]article_categories_en.ttl","all_article_categories_data_part2.txt");
        System.out.println("[part2] done.");
        generateTxtFile("[part3]article_categories_en.ttl","all_article_categories_data_part3.txt");
        System.out.println("[part3] done.");
        generateTxtFile("[part4]article_categories_en.ttl","all_article_categories_data_part4.txt");
        System.out.println("[part4] done.");
//          File fileW = new File("all_article_categories_data_part1.txt");
//        BufferedWriter writer = null;
//        
//        Model model = RDFDataMgr.loadModel("[part1]article_categories_en.ttl");
//        //model.
//        System.out.println("load successful");
//        
//        // First part or the query string 
//        String prolog = "PREFIX dct: <"+DCTerms.getURI()+">" ;
//        
//        // Query string.
//        String queryString = prolog + NL +
//            "SELECT ?narrowconcepturl ?broaderconcepturl WHERE {"
//                + "?narrowconcepturl dct:subject ?broaderconcepturl."
//                + "}" ;
//        
//        Query query = QueryFactory.create(queryString) ;
//        // Print with line numbers
//        query.serialize(new IndentedWriter(System.out,true)) ;
//        System.out.println() ;
//        
//        // Create a single execution of this query, apply to a model
//        // which is wrapped up as a Dataset
//        
//        // Or QueryExecutionFactory.create(queryString, model) ;        
//        try(QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
//            // A ResultSet is an iterator - any query solutions returned by .next()
//            // are not accessible again.
//            // Create a ResultSetRewindable that can be reset to the beginning.
//            // Do before first use.
//            
////            ResultSetRewindable rewindable = ResultSetFactory.makeRewindable(qexec.execSelect()) ;
////            ResultSetFormatter.out(rewindable) ;
////            rewindable.reset() ;
////            ResultSetFormatter.out(rewindable) ;
//            //System.out.println("Titles: ") ;
//
//            // Assumption: it's a SELECT query.
//            ResultSet rs = qexec.execSelect() ;
//            
//            try{
//                writer = new BufferedWriter(new FileWriter(fileW));
//                // The order of results is undefined. 
//                for ( ; rs.hasNext() ; )
//                {
//                    QuerySolution rb = rs.nextSolution() ;
//
//                    String narrowconcepturl = rb.get("narrowconcepturl").toString();
//                    String narrowconcept="";
//                    if (narrowconcepturl.indexOf("http://dbpedia.org/resource/") == 0){
//                        narrowconcept = narrowconcepturl.substring("http://dbpedia.org/resource/".length());
//                    }else{
//                        System.out.println(narrowconcepturl);
//                    }
//                    
//                    String broaderconcepturl = rb.get("broaderconcepturl").toString();
//                    String broaderconcept="";
//                    if (broaderconcepturl.indexOf("http://dbpedia.org/resource/") == 0){
//                        broaderconcept = broaderconcepturl.substring("http://dbpedia.org/resource/".length());
//                    }else{
//                        System.out.println(broaderconcepturl);
//                    }
//                    
//                    System.out.println(narrowconcept + " < " + broaderconcept);
//                    writer.write(narrowconcept + " < " + broaderconcept);
//                    writer.newLine();
//                }
//                writer.flush();
//                writer.close();
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//        }
    }    
    
    public static void generateTxtFile(String InputTTLFileName, String OuputTextFileName){
        File fileW = new File(OuputTextFileName);
        BufferedWriter writer = null;
        
        Model model = RDFDataMgr.loadModel(InputTTLFileName);
        //model.
        System.out.println("load successful");
        
        // First part or the query string 
        String prolog = "PREFIX dct: <"+DCTerms.getURI()+">" ;
        
        // Query string.
        String queryString = prolog + NL +
            "SELECT ?narrowconcepturl ?broaderconcepturl WHERE {"
                + "?narrowconcepturl dct:subject ?broaderconcepturl."
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

                    String narrowconcepturl = rb.get("narrowconcepturl").toString();
                    String narrowconcept="";
                    if (narrowconcepturl.indexOf("http://dbpedia.org/resource/") == 0){
                        narrowconcept = narrowconcepturl.substring("http://dbpedia.org/resource/".length());
                    }else{
                        System.out.println(narrowconcepturl);
                    }
                    
                    String broaderconcepturl = rb.get("broaderconcepturl").toString();
                    String broaderconcept="";
                    if (broaderconcepturl.indexOf("http://dbpedia.org/resource/") == 0){
                        broaderconcept = broaderconcepturl.substring("http://dbpedia.org/resource/".length());
                    }else{
                        System.out.println(broaderconcepturl);
                    }
                    
                    //System.out.println(narrowconcept + " < " + broaderconcept);
                    writer.write(narrowconcept + " <- " + broaderconcept);
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}