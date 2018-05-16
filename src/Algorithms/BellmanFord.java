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
    private int weight;

    private ArrayList<String> packages = new ArrayList<String>();
    private ArrayList<Connection> connections = new ArrayList<Connection>();
    private HashMap<String,Integer> point = new HashMap<String, Integer>();


    private int dist[];
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

    public void calculation(String source){
        for (int i = 0; i < points.length; i++){
            packages.add(points[i]);
        }
        /*
        for (int i = 0 ;  i< packages.size(); i++){
            System.out.println(packages.get(i));
        }
        */

        //courier always come back to the base to get new package
        sumTime = 0;
        sumDistance = 0;
        prepareData(source);
        calcRoute(point.get(source));
        //printArr();
        time = 0;
        for (int i = 0; i < packages.size(); i++) {
            time = time + 2 * dist[point.get(packages.get(i))];
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
            sumDistance += 2 *distance;
        }

        sumTime = time;
        packages.clear();
        //System.out.println("Sum Time: " + sumTime + "\t\t Sum Distance: " +  sumDistance);

    }


    private void calcRoute(int start) {

        int src = start;
        V = point.size();
        E = connections.size();
        previousPoint = new int[V];

        dist = new int[V];

        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i = 0; i < V; ++i) {
            previousPoint[i] = 0;
            dist[i] = Integer.MAX_VALUE;
        }
        dist[src] = 0;

        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                int u = connections.get(j).getSrc();
                int v = connections.get(j).getDest();
                int weight = connections.get(j).getWeight();
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    previousPoint[v] = u;
                }
            }
        }
        /*
        // Step 3: check for negative-weight cycles.  The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        //  path, then there is a cycle.
        for (int j=0; j<E; ++j)
        {
            int u = connections.get(j).getSrc();
            int v = connections.get(j).getDest();
            int weight = connections.get(j).getWeight();
            if (dist[u] != Integer.MAX_VALUE &&
                    dist[u]+weight < dist[v])
                System.out.println("Graph contains negative weight cycle");
        }
        */


    }

    private void prepareData(String source){
        point.clear();
        connections.clear();

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
        for (int i = 0; i< map.getPointsList().size(); i++){
            pointSrc = map.getPointsList().get(i);
            for (int j = 0; j < map.getPointsList().size(); j++){
                pointDest = map.getPointsList().get(j);
                if (map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest) != null){
                    connections.add(new Connection(point.get(pointSrc), point.get(pointDest),map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest)));
                }
            }
        }
    }

    public void printArr() {
        System.out.println("Vertex   Distance from Source");
        for (int i=0; i<V; ++i)
            System.out.println(i+"\t\t"+dist[i] + " \t\t" + previousPoint[i]);
    }
}
