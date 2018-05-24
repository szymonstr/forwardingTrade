package Algorithms.GeneticAlgorithm;


import System.Exception.FileException;
import System.Map;

import java.util.ArrayList;

public class GATest {

    private static Map map = new Map();
    private static boolean running;
    private static final String source = "(1,1)";

    public static void main(String[] args){

        try{
            running = map.load();
        }catch (FileException e ){
            e.getMessage();
        }



        String[] destinations = {"(1,13)", "(10,12)", "(9,10)", "(8,17)", "(3,3)"};

        ArrayList<String> points = new ArrayList<String>();
        points.clear();
        points.add(source);

        for (int i = 0; i < destinations.length; i++){
            String temp = destinations[i];
            boolean test = false;
            int k = 0;
            for (int ii = 0; ii < points.size(); ii++){
                if (temp.equals(points.get(ii))){
                    k++;
                }
            }
            if (k> 0){
                test = false;
            }else{
                test = true;
            }
            if(test){
                points.add(temp);
            }
        }


        Population pop = new Population(50, true, map, points);
        CalculationDistanceTime calc = new CalculationDistanceTime(map);

        int time = 0;
        time = pop.getFittest().getTime();

        int distance = 0;

        for (int i = pop.getFittest().tourSize() - 1; i > 0; i--){
            distance += calc.calcDistance(pop.getFittest().getPoint(i), pop.getFittest().getPoint(i-1));
        }



        System.out.println("Initial Time: "+time);

        System.out.println("Initial distance: " + distance);

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(map, points);

        pop = geneticAlgorithm.evolvePopulation(pop);
        for (int i = 0; i<  100; i++){
            pop = geneticAlgorithm.evolvePopulation(pop);
        }




        time = pop.getFittest().getTime();



        System.out.println("Finished!");
        System.out.println("Final time: " + time );
        System.out.println("Solution:");
        System.out.println(pop.getFittest());

        System.out.print("Final distance: ");

        distance = 0;

        for (int i = pop.getFittest().tourSize() - 1; i > 0; i--){
            distance += calc.calcDistance(pop.getFittest().getPoint(i), pop.getFittest().getPoint(i-1));
        }




        System.out.println(distance);



    }
}
