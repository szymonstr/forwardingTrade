package Algorithms.GeneticAlgorithm;

import System.Map;

public class Population {

    private Tour[] tours;
    private Map map;

    //Construct population
    public Population(int populationSize, boolean initialise, Map map){
        this.map = map;
        tours = new Tour[populationSize];
        // If we need to initialise a population of tours do so
        if (initialise){
            // Loop and create individuals
            for (int i = 0; i < populationSize(); i++){
                Tour newTour = new Tour(map);
                newTour.generateIndividual();
                saveTour(i, newTour);
            }
        }
    }

    //Saves a tour
    public void saveTour(int index, Tour tour){
        tours[index] = tour;
    }

    public Tour getTour(int index){
        return tours[index];
    }

    //Gets the best tour in the population
    public Tour getFittest(){
        Tour fittest = tours[0];
        //Loop through individuals to find fittest
        for (int i = 1; i < populationSize(); i++){
            if (fittest.getFitness() <= getTour(i).getFitness()){
                fittest = getTour(i);
            }
        }
        return fittest;
    }

    //Gets population size
    public int populationSize(){
        return tours.length;
    }
}
