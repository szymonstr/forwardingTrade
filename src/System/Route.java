package System;

import java.time.*;

public class Route {

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

    public Route(String[] data) {
        this.data = data;
        this.PrepareData();

    }

    //prepare data to calculate the route
    private void PrepareData(){
        this.dateTime = this.data[0];
        this.points = new String[data.length - 2];
        for (int i = 0; i < this.points.length; i++){
            this.points[i] = this.data[i+2]
        }
    }


}
