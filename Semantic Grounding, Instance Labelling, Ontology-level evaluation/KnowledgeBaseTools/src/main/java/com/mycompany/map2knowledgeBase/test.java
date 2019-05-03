/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.map2knowledgeBase;

import com.BoxOfC.LevenshteinAutomaton.LevenshteinAutomaton;
import com.BoxOfC.MDAG.MDAG;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author cleme
 */
public class test {
    public static void main(String [] args){
        int editDistance = LevenshteinAutomaton.computeEditDistance("TrEe", "trees"); //1
        System.out.println(editDistance);
        System.out.println("start to create arraylist");
        ArrayList<String> myArrayList = new ArrayList(Arrays.asList(new String[]{"bush", "bushes", "tree", "trees"}));
        MDAG myMDAG = new MDAG(myArrayList);
        System.out.println("ready for matching");
        LinkedList<String> ldNeighborsLinkedList = LevenshteinAutomaton.tableFuzzySearch(2, "tree", myMDAG); //"tree", "trees"
        for (int i=0;i<ldNeighborsLinkedList.size();i++){
            System.out.println(ldNeighborsLinkedList.get(i));
        }
    }
}
