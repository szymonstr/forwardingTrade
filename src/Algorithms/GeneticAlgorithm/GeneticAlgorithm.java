package Algorithms.GeneticAlgorithm;

import System.Map;

public class GeneticAlgorithm {

    // GA PARAMETERS

    private final double mutationRate = 0.15;
    private final int tournamentSize = 5;
    private final boolean elitism = true;
    private Map map;

    public GeneticAlgorithm(Map map) {
        this.map = map;
    }

    //Evolves a population over one generation
    public  Population evolvePopulation(Population pop){
        Population newPopulation = new Population(pop.populationSize(), false, map);

        //Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism){
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;
        }

        //Crossover population
        //Loop over the new population's size and create individuals
        //Current population
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++){
            //select parents
            Tour parent1 = tournamentSelection(pop);
            Tour parent2 = tournamentSelection(pop);
            //Crossover parents
            Tour child = crossover(parent1, parent2);
            //add child to new population
            newPopulation.saveTour(i, child);
        }

        //mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++){
            mutate(newPopulation.getTour(i));
        }

        return newPopulation;
    }

    //Applies crossover to a set of parents and creates offspring
    public Tour crossover(Tour parent1, Tour parent2){
        //Create new child tour
        Tour child = new Tour(map);
        //Get start and end sub tour positions for parent1's tour
        int startPos = (int) (Math.random() * parent1.tourSize());
        int endPos = (int) (Math.random() *  parent1.tourSize());

        //Loop and add the sub tour form parent1 to our child
        for(int i = 0; i <child.tourSize(); i++){
            //if our start position is less than the end position
            if( startPos < endPos && i > startPos && i < endPos){
                child.setPoint(i, parent1.getPoint(i));
            }else if (startPos > endPos) { //if our start position is larger
                if (!(i<startPos && i > endPos)){
                    child.setPoint(i, parent1.getPoint(i));
                }
            }
        }

        // Loop through parent2's city tour
        for(int i = 0; i < parent2.tourSize(); i++){
            //If child doesn't have the city add it
            if (!child.containsPoint(parent2.getPoint(i))){
                //loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.tourSize(); ii++){
                    //Spare position found, add point
                    if(child.getPoint(ii) == null){
                        child.setPoint(ii, parent2.getPoint(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    //mutate a tour using swap mutation
    private void mutate(Tour tour){
        //loop through tour points
        for (int tourPos1 = 0; tourPos1 < tour.tourSize(); tourPos1++){
            //Apply  mutation rate
            if (Math.random() < mutationRate){
                // Get a second random position in the tour
                int tourPos2 = (int) (tour.tourSize() * Math.random());

                //Get the points at target position in tour
                String point1 = tour.getPoint(tourPos1);
                String point2 = tour.getPoint(tourPos2);

                //Swap them around
                tour.setPoint(tourPos2, point1);
                tour.setPoint(tourPos1, point2);
            }
        }
    }

    //Select candidate to crossover
    private  Tour tournamentSelection(Population pop){
        //Create tournament population
        Population tournament = new Population(tournamentSize, false, map);
        //For each place in the tournament get a random candidate tour and add it
        for (int i = 0; i< tournamentSize; i++){
            int randomId = (int)(Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        //Get the fittest tour
        Tour fittest = tournament.getFittest();
        return fittest;
    }
}
