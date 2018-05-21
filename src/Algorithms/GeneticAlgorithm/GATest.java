package Algorithms.GeneticAlgorithm;


import System.Exception.FileException;
import System.Map;

public class GATest {

    private static Map map = new Map();
    private static boolean running;

    public static void main(String[] args){

        try{
            running = map.load();
        }catch (FileException e ){
            e.getMessage();
        }



        String[] points = {"(2,4)", "(3,2)","(2,1)","(4,4)"};
        for (int i = 0; i<points.length; i++) {
            TourManager.addPoint(points[i]);
        }

        Population pop = new Population(50, true, map);
        System.out.println("Initial Time: "+pop.getFittest().getTime());

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(map);

        pop = geneticAlgorithm.evolvePopulation(pop);
        for (int i = 0; i<  100; i++){
            pop = geneticAlgorithm.evolvePopulation(pop);
        }

        System.out.println("Finished!");
        System.out.println("Final time: " + pop.getFittest().getTime());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());

        System.out.print("Final distance: ");
        CalculationDistanceTime calc = new CalculationDistanceTime(map);
        int distance = 0;

        for (int i = pop.getFittest().tourSize() - 1; i > 0; i--){
            distance += calc.calcDistance(pop.getFittest().getPoint(i), pop.getFittest().getPoint(i-1));
        }

        System.out.println(distance);



    }
}
