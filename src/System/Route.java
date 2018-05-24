/**
 * TO DO
 * algorithms to calculate distance and time
 * getters distances and times
 *
 */

package System;

import Algorithms.*;
import Algorithms.GeneticAlgorithm.CalculationDistanceTime;
import Algorithms.GeneticAlgorithm.GeneticAlgorithm;
import Algorithms.GeneticAlgorithm.Population;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Route {

    private final String source= "(1,1)";

    private Logger logger = Logger.getLogger(Route.class.getName());
    private ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();
    private ArrayList<Coordinates> mapCoordinates = new ArrayList<Coordinates>();

    private int distanceBellmanFord;
    private int distancePrim;
    private int distanceGenetic;
    private int timeBellmanFord;
    private int timePrim;
    private int timeGenetic;



    private String [] data;
    private String [] points;
    private String dateTime;
    private String [] xy = new String[2];
    private String point = new String();
    private Map map;
    private BellmanFord bellmanFord;
    private Prim prim;
    private GeneticAlgorithm geneticAlgorithm;
    private int checkPoints = 0;
    private String split = ",";
    private long epoch;

    public Route(String[] data, Map cityMap) {
        this.data = data;
        this.map = cityMap;
        this.epoch = Long.parseLong(data[0]);
        this.PrepareData();
        this.CheckPointsOnMap();
        //this.ChangeDataToCoordinates();
        //this.ChangeMapToCoordinates();
        this.bellmanFord = new BellmanFord(map, points);
        bellmanFord.calculation(source);
        timeBellmanFord = bellmanFord.getSumTime();
        distanceBellmanFord = bellmanFord.getSumDistance();
        this.prim = new Prim(map, points);
        prim.calculation(source);
        timePrim = prim.getSumTime();
        distancePrim = prim.getSumDistance();
        genetic();
    }

    public ArrayList<Coordinates> getCoordinates() {
        return coordinates;
    }

    public long getEpoch() {
        return epoch;
    }

    public int getDistanceBellmanFord() {
        return distanceBellmanFord;
    }

    public int getTimeBellmanFord() {
        return timeBellmanFord;
    }

    public int getDistancePrim() {
        return distancePrim;
    }

    public int getTimePrim() {
        return timePrim;
    }


    public int getDistanceGenetic() {
        return distanceGenetic;
    }

    public int getTimeGenetic() {
        return timeGenetic;
    }

    private void genetic(){

        CalculationDistanceTime calc = new CalculationDistanceTime(map);

        ArrayList<String> GApoints = new ArrayList<String>();
        GApoints.clear();
        GApoints.add(source);

        for (int i = 0; i < points.length; i++){
            String temp = points[i];
            boolean test = false;
            int k = 0;
            for (int ii = 0; ii < GApoints.size(); ii++){
                if (temp.equals(GApoints.get(ii))){
                    k++;
                }
            }
            if (k> 0){
                test = false;
            }else{
                test = true;
            }
            if(test){
                GApoints.add(temp);
            }
        }


        if (GApoints.size() > 2) {
            geneticAlgorithm = new GeneticAlgorithm(map, GApoints);


                //System.out.println(GApoints.toString());
                Population pop = new Population(25, true, map, GApoints);    //populationSize: 50 <-Default

                pop = geneticAlgorithm.evolvePopulation(pop);
                for (int i = 0; i < 25; i++) {                         //i <100  <-Default
                    pop = geneticAlgorithm.evolvePopulation(pop);
                }

                timeGenetic = 0;
                timeGenetic = pop.getFittest().getTime();

                //timeGenetic += 2 * calc.calcTime(source, pop.getFittest().getPoint(0));

                distanceGenetic = 0;

                for (int i = pop.getFittest().tourSize() - 1; i > 0; i--) {
                    distanceGenetic += calc.calcDistance(pop.getFittest().getPoint(i), pop.getFittest().getPoint(i - 1));
                }
                //distanceGenetic += 2 * calc.calcDistance(source, pop.getFittest().getPoint(0));

        }else if (GApoints.size()>1){


            timeGenetic = 0;
            timeGenetic = 2* calc.calcTime(GApoints.get(0), GApoints.get(1));


            distanceGenetic = 0;
            distanceGenetic = 2* calc.calcDistance(GApoints.get(0), GApoints.get(1));



        }else {
            timeGenetic = 0;
            distanceGenetic = 0;
        }

    }


    //prepare data to calculate the route
    private void PrepareData(){
        dateTime = data[0];
        points = new String[(data.length - 2)/2];
        for (int i = 0; i < points.length; i++){
            points[i] = data[2 * i + 2] + "," + data[2 * i + 3];      //this.points[i] = this.data[2*i+2].replace('(', ' ') +","+ this.data[2*i+3].replace(')',' ');
            //this.points[i] = this.points[i].trim();
            //System.out.println(points[i]);

        }


    }

    //checking boxes coordinates with points on map
    private void CheckPointsOnMap(){
        for (int k=0 ; k < map.getPointsList().size(); k++){
            for (int i = 0; i < points.length; i++){
                if (map.getPointsList().get(k).equals(points[i])){   //(map.getPointsList().get(k).equals("(" + points[i] + ")"))
                    checkPoints++;
                }
            }
        }
        if (checkPoints != points.length){
            logger.warning("Unknown point! Driver: " + data[1]);
        }
    }

    /*
    private void ChangeDataToCoordinates(){
        for (int i= 0; i < points.length; i++){
            xy = points[i].split(split);
            coordinates.add(new Coordinates(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
        }
    }

    private void ChangeMapToCoordinates(){
        for (int i =0; i< map.getPointsList().size(); i++){
            point = map.getPointsList().get(i);
            point = point.replace('(', ' ');
            point = point.replace(')', ' ');
            point = point.trim();
            xy = point.split(split);
            mapCoordinates.add(new Coordinates(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
        }
    }
    */


}
