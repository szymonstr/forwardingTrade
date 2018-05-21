package Algorithms.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import System.Map;

public class Tour {

    private ArrayList<String> tour = new ArrayList<String>();

    private Map map;

    private double fitness = 0;
    private int time = 0;  // in our problem time == distance in algorithm

    //Constructor - blank tour
    public Tour(Map map){
        this.map = map;
        for (int i = 0; i< TourManager.numberOfPoints(); i++){
            tour.add(null);
        }
    }

    public Tour(ArrayList<String> tour, Map map){
        this.map = map;
        this.tour = tour;
    }

    //Create a random individual
    public void generateIndividual(){
        // Loop through all our destination points and add them to our tour
        for (int pointIndex = 0; pointIndex < TourManager.numberOfPoints(); pointIndex++){
            setPoint(pointIndex, TourManager.getPoint(pointIndex));
        }
        //Randomly reorder the tour
        Collections.shuffle(tour);
    }

    //Sets a city in a certain position within a tour
    public void setPoint(int tourPosition, String point){
        tour.set(tourPosition, point);
        // If the tours been aletered we need to reset the fitness and distance
        fitness = 0;
        time = 0;
    }

    //Gets a point form the tour
    public String getPoint(int tourPosition){
        return tour.get(tourPosition);
    }

    //Gets the tour fitness
    public double getFitness(){
        if (fitness == 0){
            fitness = 1/(double)getTime();
        }
        return fitness;
    }

    //Gets the total time of the tour
    public int getTime(){
        if (time == 0){
            int tourTime = 0;
            //Loop through our tour's points
            for (int pointIndex=0; pointIndex < tourSize(); pointIndex++){
                //Get point we're traveling from
                String fromPoint = getPoint(pointIndex);
                //Point we're traveling to
                String destinationPoint;
                //Check we're not our tour's last point, if we are set our
                //tour's final destination city to our starting point
                if (pointIndex+1 < tourSize()){
                    destinationPoint = getPoint(pointIndex+1);
                }else{
                    destinationPoint= getPoint(0);
                }
                //Get the distance between the two points

                tourTime += new CalculationDistanceTime(map).calcTime(fromPoint, destinationPoint);
            }
            time = tourTime;
        }
        return time;
    }

    public int tourSize(){
        return tour.size();
    }

    //Check if the tour contains a point
    public boolean containsPoint(String point){
        return tour.contains(point);
    }

    @Override
    public String toString(){
        String geneString = "|";
        for (int i = 0; i< tourSize(); i++){
            geneString += getPoint(i) + "|";
        }
        return geneString;
    }

}
