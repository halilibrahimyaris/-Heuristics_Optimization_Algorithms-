/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week_10_GA;

import week_11_GA.Individual;
import java.util.Random;

/**
 *
 * @author zekikus
 */
public class Population {

     int popSize; // Store the population size
    public int chromosomeLength; // Store the length of the chrosome array
    public Individual[] individuals; // Store the each individual, chromosome in this population
    private final double MUTATION_RATE = 0.5;
    public Population(int popSize, int chromosomeLength) {
        this.popSize = popSize;
        this.chromosomeLength = chromosomeLength;
        individuals = new Individual[popSize];
    }

    // Initialize the population. Create random individuals and put this individual to the individuals array
    public void initializePopulation() {
        for (int i = 0; i < popSize; i++) {
            individuals[i] = new Individual(chromosomeLength, true);
        }
    }
    
    // Print each individual's chromosomes
    public void printPopulation(){
        for (int i = 0; i < individuals.length; i++) {
            System.out.println("Chrosome #" + i + ": " + individuals[i]);
        }
    }
    /* my pseudo code for selection
    *  func tournament_selection(pop, k):
       best = null
       for i=1 to k
            ind = pop[random(1, N)]
            if (best == null) or fitness(ind) > fitness(best)
                best = ind
       return best*/
    public Individual tournamentSelection(Individual[] population, int k){
        Random rand = new Random();
        Individual best =null;
        for (int i = 0; i < k; i++) {
            Individual individual = population[rand.nextInt(population.length)];
            if(best == null || individual.fitness>best.fitness){
                best=individual;
            }
        }
        return best;
    }

    public Individual crossover(Individual parent1,Individual parent2){
        Individual newGen = new Individual(chromosomeLength,false);
        for (int i = 0; i < newGen.getChromosomeLength(); i++) {
            int currentChromosome = 0;
            if (Math.random()>MUTATION_RATE){
                currentChromosome = parent1.getChromosome()[i];
            }
            else{
                currentChromosome = parent2.getChromosome()[i];
            }
            newGen.getChromosome()[i]=currentChromosome;
        }
        return newGen;
    }



}
