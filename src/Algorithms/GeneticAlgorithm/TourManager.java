package Algorithms.GeneticAlgorithm;

import java.util.ArrayList;

public class TourManager {

    // our points to delivery the packages
    private static ArrayList<String> destiationPoints = new ArrayList<String>();

    public static void addPoint (String point){
        destiationPoints.add(point);
    }

    public static String getPoint(int index){
        return destiationPoints.get(index);
    }

    public static int numberOfPoints(){
        return destiationPoints.size();
    }
}
