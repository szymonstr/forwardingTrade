/**
 * Prim's algorithm
 *
 * this is a greedy algorithm
 *
 */

package Algorithms;

import java.util.*;
import java.lang.*;
import java.io.*;
import System.*;
import System.Map;

public class Prim
{

    private Map map;

    private ArrayList<String> packages = new ArrayList<String>();
    private ArrayList<Connection> connections = new ArrayList<Connection>();
    private HashMap<String,Integer> point = new HashMap<String, Integer>();

    private String[] points;
    private String pointSrc;
    private String pointDest;
    private String firstSurce;

    private int V;
    private int E;
    private int number;
    private int sumDistance;
    private int sumTime;
    private int time;
    private int time_t;
    private int distance;
    private int distance_t;
    private int newSource;
    private int weight;
    private int x;
    private int y;
    private int graph[][];
    private int [] parent;


    public Prim(Map map, String[] points) {
        this.map = map;
        this.points = points;
    }

    public int getSumDistance() {
        return sumDistance;
    }

    public int getSumTime() {
        return sumTime;
    }

    //prepares a graph, adjacency matrix is used to represent the connection in graph
    private void prepareGraph() {
        V = point.size();  // Number of vertices in graph
        E = connections.size();  // Number of edges in graph

        graph = new int[V][V];

        for (int j = 0; j < V; j++) {
            for (int i = 0; i < V; i++) {
                graph[j][i] = 0;
            }
        }
        for (int j = 0; j < connections.size(); j++) {
            x = connections.get(j).getSrc();
            y = connections.get(j).getDest();
            weight = connections.get(j).getWeight();
            graph[x][y] = weight;
            graph[y][x] = weight;
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
                }
            }
        }
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

        sumTime = 0;
        sumDistance = 0;
        firstSurce = source;

        //algorithms the nearest point form base to go to it
        //next step: from this point to next nearest point
        // and again, and again
        //finally add route from base to last point
       while (packages.size()>0){
          time = Integer.MAX_VALUE;
           prepareData(source);
           prepareGraph();
           primMST();
           for (int i =0; i < packages.size(); i++){
               time_t =0;
               distance_t = 0;
               int destination = point.get(packages.get(i));
               while(destination !=0 ){
                   time_t += graph[destination][parent[destination]];
                   destination = parent[destination];
                   distance_t++;
               }
               if (time_t < time){
                   time = time_t;
                   distance = distance_t;
                   newSource = i;
               }
           }
           sumTime += time;
           sumDistance += distance;
           source = packages.get(newSource);
           packages.remove(newSource);
       }

       prepareData(firstSurce);
       prepareGraph();
       primMST();
       time_t =0;
       distance_t = 0;
       int destination = point.get(source);
       while(destination !=0 ){
           time_t += graph[destination][parent[destination]];
           destination = parent[destination];
           distance_t++;
       }

       sumTime += time_t;
       sumDistance += distance_t;

        packages.clear();
    }


    private int minKey(int key[], Boolean mstSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;

        for (int v = 0; v < V; v++)
            if (mstSet[v] == false && key[v] < min)
            {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    /*
    // A utility function to print the constructed MST stored in
    // parent[]
    void printMST( int n, int graph[][]) {

        System.out.println("Edge   Weight");
        for (int i = 1; i < V; i++)
            System.out.println(parent[i]+" - "+ i+"    "+
                    graph[i][parent[i]]);
    }
    */

    // Function to construct and print MST for a graph represented
    //  using adjacency matrix representation
    private void primMST() {

        // Array to store constructed MST
        parent = new int[V];

        // Key values used to pick minimum weight edge in cut
        int key[] = new int [V];

        // To represent set of vertices not yet included in MST
        Boolean mstSet[] = new Boolean[V];

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        // Always include first 1st vertex in MST.
        key[0] = 0;     // Make key 0 so that this vertex is
        // picked as first vertex
        parent[0] = -1; // First node is always root of MST

        // The MST will have V vertices
        for (int count = 0; count < V-1; count++) {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++) {
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        //printMST( V, graph);
    }


}
