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

        String[] points = {"(1,2)", "(2,1)"};

        bellmanFord = new BellmanFord(map, points);
        bellmanFord.calculation("(1,1)");

        System.out.println(bellmanFord.getSumTime() + " <- sumTime");
        System.out.println(bellmanFord.getSumDistance() + " <- sumDistance");
    }
}
