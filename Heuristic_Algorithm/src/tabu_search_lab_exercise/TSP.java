
/*

    Implemented by Abdussamed KACI

*/

import java.io.FileNotFoundException;
import java.util.*;

public class TSP {
    
    static int seed = 0;
    static Random rnd = new Random(seed);

    public static Solution tabuSearch(List<City> items) throws CloneNotSupportedException {
        // YOUR CODE HERE
        final int MAX_TABU_SIZE = 5;
        final int MAX_ITERATION = 3000000;
        /*
        * for tsp_70_1 file
        * MAX_TABU_SIZE = 100;
        * MAX_ITERATION = 4000000;
        * Objective value of the best solution:735.3755  */
        /*
        * for tsp_100_1 file
        * MAX_TABU_SIZE = 400;
        * MAX_ITERATION = 1000000;
        * Objective value of the best solution:26031.494
        *
        *  MAX_TABU_SIZE = 5;
         int MAX_ITERATION = 1000000;
        * Objective value of the best solution:23892.143
        * */

        int start = 0;
        int end = items.size() - 1;
        //Create a new solution (s0) and set this solution to the bestCandidate and sBest.
        Solution bestCandidate = new Solution(items.size());
        //Pick a random one among these neighbors and named this solution as bestCandidate.
        bestCandidate.createRndVisitOrder(items, start, end);
        bestCandidate.calculateObjective();

        Solution sBest = new Solution(items.size());
        //Pick a random one among these neighbors and named this solution as bestCandidate.
        sBest.createRndVisitOrder(items, start, end);
        sBest.calculateObjective();

        Queue<Solution> tabuList = new LinkedList<>();
        tabuList.add(bestCandidate);
        /*Iterate each solution (sCandidate) in the tabuList and compare the solutions:
        a. If TabuList does not contain solution sCandidate and the fitness value of this solution is
        better than bestCandidate, replace sCandidate solution with bestCandidate.*/
        int counter = 0;
        while (counter < MAX_ITERATION) {
            List<Solution> neighbors = Utils.getRndNeighbors(bestCandidate);
            Collections.shuffle(neighbors);

            bestCandidate = neighbors.get(0);
            for (Solution neighbor : neighbors) {
                if (!Utils.isTabuListContain(tabuList, neighbor.getRndSolution()) && (neighbor.getObj() < bestCandidate.getObj())) {
                    //If new solution (‘bestCandidate’) better than best solution (sBest), change it to the best.
                    bestCandidate = neighbor;
                }
            }

            if (bestCandidate.getObj() < sBest.getObj()) {
                sBest = bestCandidate;
            }

            if (!Utils.isTabuListContain(tabuList, bestCandidate.getRndSolution())) {
                //Add bestCandidate solution into the tabuList.
                tabuList.add(bestCandidate);
            }

            if (tabuList.size() > MAX_TABU_SIZE) {
                tabuList.remove();
            }

            counter++;
        }

        // --------------
        return sBest; // return best solution
    }
    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        
        List<City> cities = Utils.readFile("data/tsp/tsp_100_1");
        
        Solution bestSolution = tabuSearch(cities);
        // Print best solution and objective value of this solution
        System.out.println("Objective value of the best solution:" + bestSolution.getObj());
        bestSolution.printVisitingOrder();
      

    }

}
