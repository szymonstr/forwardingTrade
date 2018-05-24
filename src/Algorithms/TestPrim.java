/**
 * Prim's algorithm test
 */

package Algorithms;


import System.Exception.FileException;
import System.Map;


public class TestPrim{

    private static Map map = new Map();
    private static boolean running;
    private static Prim prim ;


    public static void main(String[] args) {

        try {
            running = map.load();
        } catch (FileException e) {
            e.getMessage();
        }
        //creates destinations points
        String[] points = {"(1,3)", "(2,3)"};

        //calculates the distance and time
        prim = new Prim(map,points);
        prim.calculation("(1,1)");

        //prints results
        System.out.println(prim.getSumTime() + " <- sumTime");
        System.out.println(prim.getSumDistance() + " <- sumDistance");

    }
}