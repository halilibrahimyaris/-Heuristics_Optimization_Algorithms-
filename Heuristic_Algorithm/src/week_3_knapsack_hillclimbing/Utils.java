package week_3_knapsack_hillclimbing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    
    static int knapsackSize; // Capacity of Knapsack
    
    public static List<Item> readFile(String fileName) throws FileNotFoundException{
        Scanner reader = new Scanner(new File(fileName));
        
        int nbrLine = 0;
        int solutionNumber = 0;
        List<Item> itemList = new ArrayList<>();
        
        // The loop will run until the lines in the file are finished.
        while (reader.hasNext()) {
            String nextLine = reader.nextLine(); // Get Next Line
            
            if(nbrLine == 0){ 
                nbrLine++;
                // Read capacity of knapsack from file
                knapsackSize = Integer.parseInt(nextLine.replace("\n", "").split(" ")[1]); 
                continue;
            } // Skip First Row
            
            // Split Line
            String[] line = nextLine.replace("\n", "").split(" ");
            int value = Integer.parseInt(line[0]); // Get Value of Item
            int weight = Integer.parseInt(line[1]); // Get Weight of Item
            
            // Create the item based on the information read from the file.
            itemList.add(new Item("" + solutionNumber++, value, weight)); // Add city
        }
        
        return itemList;
    }
    
    // Get Neighbors of Given Solution
    public static List<Solution> getNeighbors(Solution givenSolution) throws CloneNotSupportedException{
        List<Solution> neighbors = new ArrayList<>();
        // Iterate each city in givenSolution and create Neighbors
        for (int i = 0; i < givenSolution.getRndSolution().size(); i++) {
            
            Solution temp = (Solution) givenSolution.clone(); // Get clone of given solution, because we dont want change original solution
            Item selectedItem = (Item) temp.getRndSolution().get(i); // Get ith item
            int isSelected = selectedItem.getIsSelected(); // Get selected status of ith item
            selectedItem.setIsSelected(isSelected ^ 1); // And flip that value
            temp.calculateObjective(); // Calculate Objective Value
            neighbors.add(temp);
        }
        
        return neighbors;
    }
    
    // Get Best Neighbor in the Neighbor List
    public static Solution getBestNeighbor(List<Solution> neighbors){
        Solution bs = neighbors.get(0);
        for(int i = 1; i < neighbors.size(); i++){
            if(neighbors.get(i).getObj() <= knapsackSize && neighbors.get(i).getObj() > bs.getObj()){
                bs = neighbors.get(i);
            }
        }
        return bs;
    }
}
