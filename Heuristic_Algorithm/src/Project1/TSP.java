/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
author @halilibrahimyaris
*/
package Project1;

import Week_5_Project.Utils;
import Week_5_Project.City;
import Week_5_Project.Solution;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSP {
    //this method written for getting random Neighbors
    public static List<Solution> randomNeighbors(Solution givenCities) throws CloneNotSupportedException {
        List<Solution> neighbors = new ArrayList<>();
        // Iterate each city in givenSolution and create Neighbors
        int startPoint = 0;//determine a start point that arrays first element
        int endPoint = givenCities.getRndSolution().size() - 1;//determine a last point that arrays last element
        for (int i = 0; i < givenCities.getRndSolution().size(); i++) {
            Solution cloneList = (Solution) givenCities.clone();
            //clonening list for making some changing on that array
            int city1 = (startPoint + 1) + (int) (Math.random() * (endPoint - startPoint - 1));
            int city2 = (startPoint + 1) + (int) (Math.random() * (endPoint - startPoint - 1));
            //select 2 different city randomly
            Collections.swap(cloneList.getRndSolution(), city1, city2);
            //swapping these two cities' orders
            cloneList.calculateObjective();
            //calculate an objective value for clone array
            neighbors.add(cloneList);
            //add this clone array to real
        }
        return neighbors;
    }

    public static Solution probabilisticIterativeImp(List<City> cities) throws CloneNotSupportedException {
        final double CONSTANT_PROB = 0.5; // Constant Probability
        int startPoint = 0;//define start point which array's first element
        int endPoint = cities.size() - 1;//define end point which array's last element

        Solution currentSolution = new Solution(cities.size());//Store Current Solution
        currentSolution.createRndVisitOrder(cities, startPoint, endPoint);
        currentSolution.calculateObjective();//Calculate objective values for current Solution

        Solution bestSolution = new Solution(cities.size()); // Store Best Solution
        bestSolution.createRndVisitOrder(cities, startPoint, endPoint);
        bestSolution.calculateObjective();//Calculate objective values for current Solution

        int counter = 0;
        while (counter < 20000000) {
            //My iteration values for tsp_5_1 45 iteration        Best Solution 4.0
            //My iteration values for tsp_70_1 2000000 iteration  Best Solution 2433.545 
            //My iteration values for tsp_100_1 5000000 iteration Best Solution 116295.44
            List<Solution> neighbors = randomNeighbors(currentSolution);
            int city1 = (startPoint + 1) + (int) (Math.random() * (endPoint - startPoint - 1));

            Solution s_prime = neighbors.get(city1);//define s_prime with random neighbor

            float delta = s_prime.getObj() - currentSolution.getObj();
            //define delta value for checking which solution is better.
            if (delta < 0) {
                //if s_prime solution better than current solution
                currentSolution = s_prime;

                if (s_prime.getObj() < bestSolution.getObj()) {
                    //change best solution with s_prime which better solution.
                    bestSolution = s_prime;
                }
            }
            //if s_prime worse than current solution
            else if (Math.random() > CONSTANT_PROB) {
                //we accept 0.5 probability . We create a random value between 0 to 1. and we accept more than 0.5 prob.
                currentSolution = s_prime;
            }

            counter++;

        }
        //return best solution.
        return bestSolution;
    }

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        List<City> cities = Utils.readFile("Code/data/tsp/tsp_100_1");

        Solution bestSolution = probabilisticIterativeImp(cities); // Start Probabilistic Iterative Improvement Algorithm

        // Print Solution and Objective Value
        System.out.println("Objective of Best Solution: " + bestSolution.getObj());
        bestSolution.printVisitingOrder();

    }
}
