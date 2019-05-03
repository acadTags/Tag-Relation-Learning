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
//import org.apache.jena.vocabulary.SKOS;

/** [important file] Querying all wiki page redirect relations from the .ttl format file.
 * // make take at least 3min.
 * @author cleme
 */
public class QueryAllPageRedirects {
    static public final String NL = System.getProperty("line.separator") ; 
    
    public static void main(String [] args){
        
        File fileW = new File("all_page_redirect_data_dbpedia_url_name.txt");
        BufferedWriter writer = null;
        
        Model model = RDFDataMgr.loadModel("transitive_redirects_en.ttl");
        //model.
        System.out.println("load successful");
        
        // First part or the query string 
        //String prolog = "PREFIX skos: <"+SKOS.getURI()+">" ;
        String prolog = "PREFIX dbo: <http://dbpedia.org/ontology/>" ;
        
        // Query string.
//        String queryString = prolog + NL +
//            "SELECT ?narrowconcept ?broaderconcept WHERE {"
//                + "?narrowconcepturl skos:broader ?broaderconcepturl."
//                + "?narrowconcepturl skos:prefLabel ?narrowconcept."
//                + "?broaderconcepturl skos:prefLabel ?broaderconcept"
//                + "}" ;
        String queryString = prolog + NL +
            "SELECT ?redirectconcepturl ?standardconcepturl WHERE {"
                + "?redirectconcepturl dbo:wikiPageRedirects ?standardconcepturl."
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
                    
                    String redirectconcepturl = rb.get("redirectconcepturl").toString();
                    String redirectconcept="";
                    if (redirectconcepturl.indexOf("http://dbpedia.org/resource/") == 0){
                        redirectconcept = redirectconcepturl.substring("http://dbpedia.org/resource/".length());
                    }else{
                        System.out.println(redirectconcepturl);
                    }
                    
                    String standardconcepturl = rb.get("standardconcepturl").toString();
                    String standardconcept="";
                    if (standardconcepturl.indexOf("http://dbpedia.org/resource/") == 0){
                        standardconcept = standardconcepturl.substring("http://dbpedia.org/resource/".length());
                    }else{
                        System.out.println(standardconcepturl);
                    }
                    
                    //System.out.println(redirectconcept + " = " + standardconcept);
                    writer.write(redirectconcept + " = " + standardconcept);
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
