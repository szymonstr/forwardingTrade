/**
 * Bellman Ford algorithm
 *
 * greedy implementation
 */

package Algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import System.*;


public class BellmanFord {

    private Map map;

    private String[] points;
    private String firstSource;
    private String pointSrc;
    private String pointDest;

    private ArrayList<String> packages = new ArrayList<String>();
    private ArrayList<Connection> connections = new ArrayList<Connection>();
    private HashMap<String,Integer> point = new HashMap<String, Integer>();

    private int times[];
    private int previousPoint[];
    private int V;
    private int E;
    private int distance;
    private int sumDistance;
    private int time;
    private int sumTime;
    private int newSource;
    private int previous;
    private int number;


    public BellmanFord(Map map, String[] points) {
        this.map = map;
        this.points = points;
    }


    public int getSumDistance() {
        return sumDistance;
    }

    public int getSumTime() {
        return sumTime;
    }

    //calculates shortest paths from source to all points on the map
    public void calculation(String source){
        for (int i = 0; i < points.length; i++){
            packages.add(points[i]);
        }
        /*
        for (int i = 0 ;  i< packages.size(); i++){
            System.out.println(packages.get(i));
        }
        */

        sumTime = 0;
        sumDistance = 0;
        firstSource = source;

        //algorithms the nearest point form base to go to it
        //next step: from this point to next nearest point
        // and again, and again
        //finally add route from base to last point
        while(packages.size()>0){
            time = Integer.MAX_VALUE;
            prepareData(source);
            calcRoute(point.get(source));
            for (int i = 0; i <  packages.size(); i++){
                int time_t = times[point.get(packages.get(i))];
                if (time_t < time){
                    time = time_t;
                    newSource = i;
                    previous = point.get(packages.get(i));
                    if (!packages.get(i).equals(source)) {
                        distance = 1;
                    }else{
                        distance = 0;
                    }
                    while (previousPoint[previous] != 0){
                        distance++;
                        previous = previousPoint[previous];
                    }
                }
            }
            sumTime += time;
            sumDistance += distance;
            source = packages.get(newSource);
            packages.remove(newSource);
        }

        prepareData(firstSource);
        calcRoute(point.get(firstSource));
        sumTime += times[point.get(source)];
        previous = point.get(source);
        if (!source.equals(firstSource)) {
            distance = 1;
        }else{
            distance = 0;
        }
        while (previousPoint[previous] != 0){
            distance++;
            previous = previousPoint[previous];
        }
        sumDistance += distance;

        packages.clear();
        //System.out.println("Sum Time: " + sumTime + "\t\t Sum Distance: " +  sumDistance);

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

    /*
    public void showArray() {
        System.out.println("Vertex   Distance from Source    Previous point ");
        for (int i=0; i<V; ++i)
            System.out.println(i+"\t\t"+times[i] + " \t\t" + previousPoint[i]);
    }
    */
}
