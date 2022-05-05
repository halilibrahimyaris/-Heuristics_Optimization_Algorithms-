/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
 author @halilibrahimyaris
 */
package Week_6_simulated_annealing;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSP {
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

    public static Solution simulatedAnnealing(List<City> cities) throws CloneNotSupportedException {
        double T = 1000000000; // Initial T value
        final int MIN_T_VALUE = 1; // Minimum Temperature value
        final int MAX_INNER_LOOP = 10; // Maximum inner loop count
        final double COOLING_RATE = 0.95; // Cooling Rate
        int startPoint = 0;//define start point which array's first element
        int endPoint = cities.size() - 1;//define end point which array's last element

        Solution currentSolution = new Solution(cities.size());//Store Current Solution
        currentSolution.createRndVisitOrder(cities, startPoint, endPoint);
        currentSolution.calculateObjective();//Calculate objective values for current Solution

        Solution bestSolution = currentSolution; // Store Best Solution

        while (T > MIN_T_VALUE) {

            int INNER_LOOP = 0;

            while (INNER_LOOP < MAX_INNER_LOOP) {
                List<Solution> neighbors = randomNeighbors(currentSolution);
                int city1 = (startPoint + 1) + (int) (Math.random() * (endPoint - startPoint - 1));

                Solution s_prime = neighbors.get(city1);

                float delta = s_prime.getObj() - currentSolution.getObj();
                //define delta value for checking which solution is better.
                if (delta < 0) {
                    //if s_prime solution better than current solution

                    currentSolution = s_prime;
                    if (s_prime.getObj() < bestSolution.getObj()) {
                        //change best solution with s_prime which is better solution.
                        bestSolution = s_prime;
                    }

                } else if (Math.random() < Math.exp(-(delta / T))) {
                    //Accept new solution with 𝑒^delta/T probability
                    currentSolution = s_prime;
                }
                INNER_LOOP++;
            }
            T *= COOLING_RATE;
        }
        return bestSolution; // Best Solution
    }

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {

        List<City> cities = Utils.readFile("data/tsp/tsp_70_1");

        Solution bestSolution = simulatedAnnealing(cities); // Start Probabilistic Iterative Improvement Algorithm
        // Print Solution and Objective Value
        System.out.println("Objective of Best Solution: " + bestSolution.getObj());
        bestSolution.printVisitingOrder();
    }
}


