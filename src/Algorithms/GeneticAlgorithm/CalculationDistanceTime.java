package Algorithms.GeneticAlgorithm;



import System.Map;
import System.Connection;

import java.util.ArrayList;
import java.util.HashMap;

public class CalculationDistanceTime {


    //private HashMap<String,Integer> connections = new HashMap<String, Integer>();

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


    private final String split = ",";
    private String[] xy;
    private int xFrom;
    private int yFrom;
    private int xDest;
    private int yDest;
    private int xDistance;
    private int yDistance;



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

        prepareData(fromPoint);
        calcRoute(point.get(fromPoint));

        time = times[point.get(destinationPoint)];

        return time;
    }

    public int calcDistance(String fromPoint, String destinationPoint) {
        int distance = 0;
        //changePointsToCoordinates(fromPoint, destinationPoint);
        //xDistance = xDest - xFrom;
        //yDistance = yDest - yFrom;
        //distance = Math.abs(xDistance) + Math.abs(yDistance);
        prepareData(fromPoint);
        calcRoute(point.get(fromPoint));
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

        // shortest path from src to any other vertex can
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
                    connections.add(new Connection(point.get(pointDest),point.get(pointSrc), map.getAdjacencyMatrix().get(pointSrc + "-" + pointDest)));
                }
            }
        }
    }






}

