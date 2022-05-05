/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week_11_GA;

import java.util.Random;

/**
 *
 * @author zekikus
 */
public class GA {
    static final int POP_SIZE = 10; // Store the population size
    static final int MAX_GENERATION = 50; // Store the maximum generation count
    static final int CHROMOSOME_LENGTH = 11; // Store the chromosome length or number of gene count
    static final double MUTATION_RATE = 0.5;
    public static void main(String[] args) {


        Population currentPopulation = new Population(POP_SIZE, CHROMOSOME_LENGTH);
        currentPopulation.initializePopulation(); // Generate the initial population
        currentPopulation.printPopulation();

        int generation = 0;


        while ( generation< MAX_GENERATION) {

            Population newPop = new Population(POP_SIZE, CHROMOSOME_LENGTH);
            int counter = 0;
            while (counter < POP_SIZE) {
                //creating mating pool
                Individual[] parents = Utils.createMatingPool(currentPopulation.individuals, POP_SIZE-1);
                // Selection
                Individual parent1 = currentPopulation.tournamentSelection(parents, 5);
                Individual parent2 = currentPopulation.tournamentSelection(parents, 5);

                // Crossover operation to the selected pairs
                Individual child = currentPopulation.crossover(parent1, parent2);

                // Mutate this child
                bitwiseMutation(child);
                child.evaluate();
                newPop.individuals[counter] = child;

                counter++;
            }
            currentPopulation = newPop;
            generation++;
            System.out.println("\n GENERATION " + generation);
            currentPopulation.printPopulation();
        }
    }

    public static void bitwiseMutation(Individual child){
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if(Math.random() > MUTATION_RATE){
                child.getChromosome()[i] = child.getChromosome()[i] ^ 1;
            }

        }
    }
}
