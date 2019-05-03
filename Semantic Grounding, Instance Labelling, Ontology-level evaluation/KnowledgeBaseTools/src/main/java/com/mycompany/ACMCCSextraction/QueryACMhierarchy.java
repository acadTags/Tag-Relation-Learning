/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ACMCCSextraction;

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
import org.apache.jena.vocabulary.SKOS;

/**
 *
 * @author cleme
 */
public class QueryACMhierarchy {
    static public final String NL = System.getProperty("line.separator") ; 
    
    public static void main(String [] args){
        
        File fileW = new File("acm_data_hierarchy.txt");
        BufferedWriter writer = null;
        
        Model model = RDFDataMgr.loadModel("ACMComputingClassificationSystemSKOSTaxonomy.xml");
        //model.
        System.out.println("load successful");
        
        // First part or the query string 
        String prolog = "PREFIX skos: <"+SKOS.getURI()+">" ;
        
        // Query string.
        String queryString = prolog + NL +
            "SELECT ?broaderconcept ?narrowerconcept WHERE {"
                + "?broaderconcept skos:narrower ?narrowerconcept."
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

                    String narrowerconcept = rb.get("narrowerconcept").toString();
                    narrowerconcept = narrowerconcept.substring(narrowerconcept.indexOf("#"));
                    String broaderconcept = rb.get("broaderconcept").toString();
                    broaderconcept = broaderconcept.substring(broaderconcept.indexOf("#"));    
                    System.out.println(narrowerconcept + "," + broaderconcept);
                    writer.write(narrowerconcept + "," + broaderconcept);
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
