/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.map2knowledgeBase;

import com.BoxOfC.LevenshteinAutomaton.LevenshteinAutomaton;
import com.BoxOfC.MDAG.MDAG;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author cleme
 */
public class MapTags2MCG {
    public static void main(String [] args){
        int edited_dist = 0;
        String path1 = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\mallet-2.0.8";
        String path2 = "C:\\Users\\cleme\\OneDrive\\NetBeansProjects\\DBpediaHierarchyExtraction";
        
        File filetag = new File(path1 + "\\" + "bibsonomy-taglist-final-f1.csv");
        File fileconcept = new File(path2 + "\\" + "distinct_MCG5_concept.txt"); 
        File fileW = new File(path2 + "\\" + "bibsonomy-tag-mcg-concpet-ed" + edited_dist + "test.txt");
        BufferedReader readertags = null;
        BufferedReader readerconcept = null;
        BufferedWriter writer = null;
        String tempString;
        String tag="";
        String matched;
        String [] concepts;
        //String [] concepts_raw;
        //int dist;
        HashMap <String,String> hm = new HashMap<String,String>();
        //boolean contain;
        //int max_len=0;
        try {
            readertags = new BufferedReader(new FileReader(filetag));
            readerconcept = new BufferedReader(new FileReader(fileconcept));
            writer = new BufferedWriter(new FileWriter(fileW));
            
            // storing concepts to an array concepts [].
            int line = 0;
            // get tag max_length;
            while ((tempString = readerconcept.readLine()) != null) {
                //max_len=max(max_len,tempString.length());
                line++;
            }
            readerconcept.close();
            readerconcept = new BufferedReader(new FileReader(fileconcept));
            
            concepts = new String[line];
            //concepts_raw = new String[line];
            line=0;
            while ((tempString = readerconcept.readLine()) != null) {
                //concepts_raw[line] = tempString;
                //concepts[line] = tempString;
                concepts[line] = tempString.toLowerCase();
                line++;
            }
            
            System.out.println("concept array created");
            System.out.println("start to create arraylist");
            ArrayList<String> concepts_arrl = new ArrayList(Arrays.asList(concepts));
            MDAG concepts_MDAG = new MDAG(concepts_arrl);
            System.out.println("ready for matching");
            
            while ((tempString = readertags.readLine()) != null) {
                String [] arr = tempString.split(",");
                if (arr.length != 2){                    
                    if (arr.length == 3){
                        tempString = arr[0] + "," + arr[1];
                        tag = arr[0];
                    }else{
                        System.out.println("[wrong format: more than 2 comma]" + tempString);
                        continue;
                    }    
                }else{
                    tempString = arr[0];
                    tag = arr[0];
                }
                tag = tag.replace("_", " ");
                //System.out.println(tempString);
                //for(String concept: concepts){
                    LinkedList<String> ldNeighborsLinkedList = LevenshteinAutomaton.tableFuzzySearch(edited_dist, tag.toLowerCase(), concepts_MDAG);
                    for (int i=0;i<ldNeighborsLinkedList.size();i++){
                        matched = ldNeighborsLinkedList.get(i);
                        //System.out.println(matched);
                        if (hm.containsKey(tempString)){
                            hm.put(tempString, hm.get(tempString) + "  " + matched);
                            System.out.println(tempString + " = " + hm.get(tempString) + "  " + matched); // displaying a tag matching to multiple concepts: but not happened so far.
                        }else{
                            hm.put(tempString, matched);
                        }
                    }
                //}
//                for(String concept: concepts){
//                    if (tag.toLowerCase().equals(concept.toLowerCase())){
//                        //matched = concept;
//                        if (hm.containsKey(tempString)){
//                            hm.put(tempString, hm.get(tempString) + "  " + concept);
//                            System.out.println(tempString + " = " + hm.get(tempString) + "  " + concept);
//                        }else{
//                            hm.put(tempString, concept);
//                        }
//                    }
//                }
//                System.out.println(tempString);
            }
            
            Set<Map.Entry<String, String>> set = hm.entrySet();
            for(Map.Entry<String, String> en: set){
                writer.write(en.getKey() + "," + en.getValue());
                writer.newLine();
            }
            writer.flush();
            writer.close();
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
    
    public static int getSubCost(char target, char source, int SubCost){
        // Get the subcost, while ignoring the case issue.
        if ((target == source) || (target+"").toUpperCase().equals((source + "").toUpperCase())){
            return 0;
        }else{
            return SubCost;
        }
    }
}
