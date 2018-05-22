package Algorithms.GeneticAlgorithm;


import System.Exception.FileException;
import System.Map;

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



        String[] points = {"(1,3)", "(2,3)"};
        for (int i = 0; i<points.length; i++) {
            TourManager.addPoint(points[i]);
        }

        Population pop = new Population(50, true, map);
        CalculationDistanceTime calc = new CalculationDistanceTime(map);

        int time = 0;
        time = pop.getFittest().getTime();
        time += calc.calcTime(source, pop.getFittest().getPoint(0) );
        time += calc.calcTime(pop.getFittest().getPoint(pop.getFittest().tourSize()-1), source);


        System.out.println("Initial Time: "+time);

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(map);

        pop = geneticAlgorithm.evolvePopulation(pop);
        for (int i = 0; i<  100; i++){
            pop = geneticAlgorithm.evolvePopulation(pop);
        }




        time = pop.getFittest().getTime();
        time += calc.calcTime(source, pop.getFittest().getPoint(0) );
        time += calc.calcTime(pop.getFittest().getPoint(pop.getFittest().tourSize()-1), source);

        System.out.println("Finished!");
        System.out.println("Final time: " + time );
        System.out.println("Solution:");
        System.out.println(pop.getFittest());

        System.out.print("Final distance: ");

        int distance = 0;

        for (int i = pop.getFittest().tourSize() - 1; i > 0; i--){
            distance += calc.calcDistance(pop.getFittest().getPoint(i), pop.getFittest().getPoint(i-1));
        }

        distance += calc.calcDistance(source, pop.getFittest().getPoint(0));
        distance += calc.calcDistance(pop.getFittest().getPoint(pop.getFittest().tourSize() -1), source);

        System.out.println(distance);



    }
}
