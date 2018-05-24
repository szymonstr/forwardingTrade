/**
 * Route class calculate routes
 *
 * It is using implemented algorithms in Algorithms package
 */

package System;

import Algorithms.*;
import Algorithms.GeneticAlgorithm.CalculationDistanceTime;
import Algorithms.GeneticAlgorithm.GeneticAlgorithm;
import Algorithms.GeneticAlgorithm.Population;

import java.util.ArrayList;

public class Route {

    private final String SOURCE= "(1,1)";

    private int distanceBellmanFord;
    private int distancePrim;
    private int distanceGenetic;
    private int timeBellmanFord;
    private int timePrim;
    private int timeGenetic;

    private String [] points;
    private Map map;
    private BellmanFord bellmanFord;
    private Prim prim;
    private GeneticAlgorithm geneticAlgorithm;
    private long epoch;

    public Route(String[] points, Map cityMap, Long epoch) {
        this.points = points;
        this.map = cityMap;
        this.epoch = epoch;
        //System.out.println("Start");
        this.bellmanFord = new BellmanFord(map, points);
        bellmanFord.calculation(SOURCE);
        timeBellmanFord = bellmanFord.getSumTime();
        distanceBellmanFord = bellmanFord.getSumDistance();
        //System.out.println("BellmanFord");
        this.prim = new Prim(map, points);
        prim.calculation(SOURCE);
        timePrim = prim.getSumTime();
        distancePrim = prim.getSumDistance();
        //System.out.println("Prim");
        genetic();
        //System.out.println("Genetic");
    }


    public long getEpoch() {
        return epoch;
    }

    public int getDistanceBellmanFord() {
        return distanceBellmanFord;
    }

    public int getTimeBellmanFord() {
        return timeBellmanFord;
    }

    public int getDistancePrim() {
        return distancePrim;
    }

    public int getTimePrim() {
        return timePrim;
    }


    public int getDistanceGenetic() {
        return distanceGenetic;
    }

    public int getTimeGenetic() {
        return timeGenetic;
    }

    private void genetic(){

        CalculationDistanceTime calc = new CalculationDistanceTime(map);

        ArrayList<String> GApoints = new ArrayList<String>();
        GApoints.clear();
        GApoints.add(SOURCE);

        // checking coordinates of destinations
        //eliminates doubled (or more) destination points
        for (int i = 0; i < points.length; i++){
            String temp = points[i];
            boolean test;
            int k = 0;
            for (int ii = 0; ii < GApoints.size(); ii++){
                if (temp.equals(GApoints.get(ii))){
                    k++;
                }
            }
            if (k> 0){
                test = false;
            }else{
                test = true;
            }
            if(test){
                GApoints.add(temp);
            }
        }

        //calculations distances using genetic algorithm
        if (GApoints.size() > 2) {
            geneticAlgorithm = new GeneticAlgorithm(map, GApoints);

            //smaller value of population size and amount of loop iterations == faster
            //bigger value of population size and amount of loop iterations == better
            Population pop = new Population(5, true, map, GApoints);    //populationSize: 50 <-Default

            pop = geneticAlgorithm.evolvePopulation(pop);
            for (int i = 0; i < 3; i++) {                         //i <100  <-Default
                pop = geneticAlgorithm.evolvePopulation(pop);
            }

            timeGenetic = 0;
            timeGenetic = pop.getFittest().getTime();

            distanceGenetic = 0;

            for (int i = pop.getFittest().tourSize() - 1; i > 0; i--) {
                distanceGenetic += calc.calcDistance(pop.getFittest().getPoint(i), pop.getFittest().getPoint(i - 1));
            }

        }else if (GApoints.size()>1){
            //if destination point is only one, calculate the distance using method to calc distance without genetic algorithm
            //results are the same, but this solution is faster

            timeGenetic = 0;
            timeGenetic = 2* calc.calcTime(GApoints.get(0), GApoints.get(1));


            distanceGenetic = 0;
            distanceGenetic = 2* calc.calcDistance(GApoints.get(0), GApoints.get(1));



        }else {
            //if courier have no packages
            timeGenetic = 0;
            distanceGenetic = 0;
        }

    }

}
