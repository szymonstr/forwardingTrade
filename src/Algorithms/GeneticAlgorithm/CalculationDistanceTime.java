/**
 * Calculates short paths between two point for genetic algorithm
 *
 * To calculate this it uses Bellman Ford algorithm
 *
 * Crossover with two very good algorithm gives the best results
 */

package Algorithms.GeneticAlgorithm;

import System.Map;
import System.Connection;
import java.util.ArrayList;
import java.util.HashMap;


public class CalculationDistanceTime {

    private ArrayList<Connection> connections = new ArrayList<Connection>();
    private HashMap<String,Integer> point = new HashMap<String, Integer>();

    private int times[];
    private int previousPoint[];
    private int V;
    private int E;
    private int previous;
    private int number;

    private String pointSrc;
    private String pointDest;

    private Map map;

    public CalculationDistanceTime(Map map) {
        this.map = map;
    }

    //calculates time between two points
    public int calcTime(String fromPoint, String destinationPoint) {
        int time = 0;

        //System.out.println(destinationPoint);
        //prepares graph
        prepareData(fromPoint);
        //calculates time from starting point to all other point in graph
        calcRoute(point.get(fromPoint));
        //finds time to destination point
        time = times[point.get(destinationPoint)];

        return time;
    }

    //calculates distance between two points
    public int calcDistance(String fromPoint, String destinationPoint) {
        int distance = 0;
        //prepares graph
        prepareData(fromPoint);
        //calculates distance from starting point to all other point in graph
        calcRoute(point.get(fromPoint));
        //finds distance to destination point
        previous = point.get(destinationPoint);
        if (!destinationPoint.equals(fromPoint)) {
            distance = 1;
        }else{
            distance = 0;
        }
        while (previousPoint[previous] != 0){
            distance++;
            previous = previousPoint[previous];
        }

        return distance;
    }

    private void calcRoute(int start) {

        int src = start;
        V = point.size();
        E = connections.size();
        previousPoint = new int[V];

        times = new int[V];

        //set distance to infinity
        for (int i = 0; i < V; ++i) {

            previousPoint[i] = 0;
            times[i] = Integer.MAX_VALUE;
        }
        times[src] = 0;

        // shortest path from src to any other vertex
        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                int u = connections.get(j).getSrc();
                int v = connections.get(j).getDest();
                int weight = connections.get(j).getWeight();
                if (times[u] != Integer.MAX_VALUE && times[u] + weight < times[v]) {
                    times[v] = times[u] + weight;
                    previousPoint[v] = u;
                }
            }
        }
    }

    //prepares date to useful form for algorithm
    private void prepareData(String source){
        point.clear();
        connections.clear();
        //paginates vertex in graph
        number = 0;
        point.put(source, number);
        for (int i =  0; i< map.getPointsList().size(); i++){

            if (source.equals(map.getPointsList().get(i))){
                //empty
            }else {
                number++;
                point.put(map.getPointsList().get(i), number);
            }
        }

        //creates arraylist of connetions in the graph
        //connections includes to point and weight of edge between them
        for (int i = 0; i< map.getPointsList().size(); i++){
            pointSrc = map.getPointsList().get(i);
            for (int j = 0; j < map.getPointsList().size(); j++){
                pointDest = map.getPointsList().get(j);
                if (map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest) != null){
                    connections.add(new Connection(point.get(pointSrc), point.get(pointDest),map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest)));
                    connections.add(new Connection(point.get(pointDest),point.get(pointSrc), map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest)));
                }
            }
        }
    }
}

