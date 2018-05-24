/**
 * Bellman Ford algorithm test
 */

package Algorithms;

import System.*;
import System.Exception.FileException;

public class BelmanFordTest {

    private static Map map = new Map();
    private static boolean running;
    private static BellmanFord bellmanFord;

    public static void main(String[] args){

        try{
            running = map.load();
        }catch (FileException e ){
            e.getMessage();
        }

        //creates destinations points
        String[] points = {"(1,3)", "(2,2)"};


        //calculates the distance snd time
        bellmanFord = new BellmanFord(map, points);
        bellmanFord.calculation("(1,1)");

        //prints results
        System.out.println(bellmanFord.getSumTime() + " <- sumTime");
        System.out.println(bellmanFord.getSumDistance() + " <- sumDistance");
    }
}
