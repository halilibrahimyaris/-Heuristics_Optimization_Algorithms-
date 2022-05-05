/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week_4_graph_coloring;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author zekikus
 */
public class GraphColoring {

    public static void main(String[] args) throws FileNotFoundException {

        int[][] adjacencyMatrix = Utils.readFile("src/data/graphcoloring/gc_4_1");
        Test solution = new Test();
        solution.createRndSolution(adjacencyMatrix);
        solution.calculateObjective(adjacencyMatrix);
        solution.printSolution();
        
    }
}
