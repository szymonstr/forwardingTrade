package Algorithms.GeneticAlgorithm;



import System.Map;
import System.Connection;

import java.util.ArrayList;
import java.util.HashMap;

public class CalculationDistanceTime {


    //private HashMap<String,Integer> connections = new HashMap<String, Integer>();
    //private ArrayList<String> packages = new ArrayList<String>();
    private ArrayList<Connection> connections = new ArrayList<Connection>();
    private HashMap<String, Integer> point = new HashMap<String, Integer>();


    private final String split = ",";
    private String[] xy;
    private String pointSrc;
    private String pointDest;
    private int xFrom;
    private int yFrom;
    private int xDest;
    private int yDest;
    private int xDistance;
    private int yDistance;
    private int number;

    private int weight;
    private int x;
    private int y;
    private int V;
    private int E;
    private int graph[][];
    private int[] parent;

    private Map map;


    public CalculationDistanceTime(Map map) {
        this.map = map;
    }

    public int calcTime(String fromPoint, String destinationPoint) {
        int time = 0;
        //changePointsToCoordinates(fromPoint, destinationPoint);
        //prepareData();
        //xDistance = xDest - xFrom;
        //yDistance = yDest - yFrom;


        prepareData(fromPoint);
        prepareGraph();
        primMST();

        int destination = point.get(destinationPoint);
        while(destination != 0){
            time += graph[destination][parent[destination]];
            destination = parent[destination];
        }




        /*
        if (xDistance > 0){
            for (int x = xFrom; x< xDest; x++){
                time += connections.get("("+ x + "," +yFrom + ")-(" + (x+1)+ "," + yFrom+")");
            }

        }else if (xDistance < 0){
            for (int x = xDest; x < xFrom; x++)
                time += connections.get("(" + x + "," + yDest + ")-(" + (x + 1) + "," + yDest + ")");
        }
        if (yDistance > 0){
            for (int y = yFrom; y < yDest; y++){
                time += connections.get("("+ xFrom + "," +y + ")-(" + xFrom+ "," + (y+1)+")");
            }
        }else if (yDistance < 0){
            for (int y = yDest; y < yFrom; y++){
                time += connections.get("("+ xFrom + "," +y + ")-(" + xFrom+ "," + (y+1)+")");
            }
        }
        */


        return time;
    }

    public int calcDistance(String fromPoint, String destinationPoint) {
        int distance = 0;
        //changePointsToCoordinates(fromPoint, destinationPoint);
        //xDistance = xDest - xFrom;
        //yDistance = yDest - yFrom;
        //distance = Math.abs(xDistance) + Math.abs(yDistance);


        prepareData(fromPoint);
        prepareGraph();
        primMST();

        int destination = point.get(destinationPoint);
        while(destination != 0){
            destination = parent[destination];
            distance++;
        }

        return distance;
    }

    /*
    private void changePointsToCoordinates(String fromPoint, String destinationPoint) {
        fromPoint = fromPoint.replace('(', ' ');
        fromPoint = fromPoint.replace(')', ' ');
        fromPoint = fromPoint.trim();
        xy = fromPoint.split(split);
        xFrom = Integer.parseInt(xy[0]);
        yFrom = Integer.parseInt(xy[1]);
        destinationPoint = destinationPoint.replace('(', ' ');
        destinationPoint = destinationPoint.replace(')', ' ');
        destinationPoint = destinationPoint.trim();
        xy = destinationPoint.split(split);
        xDest = Integer.parseInt(xy[0]);
        yDest = Integer.parseInt(xy[1]);

    }



    private void prepareData(){

       for (int i = 0; i< map.getPointsList().size(); i++){
           pointSrc = map.getPointsList().get(i);
            for (int j = 0; j < map.getPointsList().size(); j++){
                pointDest = map.getPointsList().get(j);
                if (map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest) != null){
                   connections.put(pointSrc + "-" + pointDest, map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest));
                   connections.put(pointDest + "-" + pointSrc, map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest));
                }
            }
       }
    }
    */

    private void prepareData(String source) {
        point.clear();
        connections.clear();

        number = 0;
        point.put(source, number);

        for (int i = 0; i < map.getPointsList().size(); i++) {

            if (source.equals(map.getPointsList().get(i))) {
                //empty
            } else {
                number++;
                point.put(map.getPointsList().get(i), number);
            }
        }
        for (int i = 0; i < map.getPointsList().size(); i++) {
            pointSrc = map.getPointsList().get(i);
            for (int j = 0; j < map.getPointsList().size(); j++) {
                pointDest = map.getPointsList().get(j);
                if (map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest) != null) {
                    connections.add(new Connection(point.get(pointSrc), point.get(pointDest), map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest)));
                }
            }
        }
    }

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


    }

}

