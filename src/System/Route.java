package System;

import java.time.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Route {

    private Logger logger = Logger.getLogger(Route.class.getName());
    private ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();
    private ArrayList<Coordinates> mapCoordinates = new ArrayList<Coordinates>();

    private int distance_alg1;
    private int distance_alg2;
    private int distance_alg3;
    private int time_alg1;
    private int time_alg2;
    private int time_alg3;

    private Instant epoch;
    private String [] data;
    private String [] points;
    private String dateTime;
    private String [] xy = new String[2];
    private Map map;
    private int checkPoints = 0;
    private String split = ",";

    public Route(String[] data, Map cityMap) {
        this.data = data;
        this.map = cityMap;
        this.PrepareData();
        this.CheckPointsOnMap();
        this.ChangeDataToCoordinates();
        this.ChangeMapToCoordinates();

    }

    //prepare data to calculate the route
    private void PrepareData(){
        this.dateTime = this.data[0];
        this.points = new String[data.length - 2];
        for (int i = 0; i < this.points.length; i++){
            this.points[i] = this.data[i+2];
        }
    }

    private void CheckPointsOnMap(){
        for (int k=0 ; k < map.getPoints().size(); k++){
            for (int i = 0; i < points.length; i++){
                if (map.getPoints().get(k).equals(points[i])){
                    checkPoints++;
                }
            }
        }
        if (checkPoints != points.length){
            logger.warning("Unknown point! Driver: " + data[1]);
        }
    }

    private void ChangeDataToCoordinates(){
        for (int i= 0; i < points.length; i++){
            points[i].replace('(','');
            points[i].replace(')','');
            xy = points[i].split(split);
            coordinates.add(new Coordinates(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
        }
    }

    private void ChangeMapToCoordinates(){
        for (int i =0; i< map.getPoints().size(); i++){
            map.getPoints().get(i).replace('(','');
            map.getPoints().get(i).replace(')','');
            xy = map.getPoints().get(i).split(split);
            mapCoordinates.add(new Coordinates(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
        }
    }


}
