/**
 * This class is a description of driver
 */


package System;

import java.util.ArrayList;

public class Driver {

    private ArrayList<Route> routes = new ArrayList<Route>();
    private String name = new String();
    private Map map;


    public Driver(String name, Map cityMap) {
        this.map = cityMap;
        this.name = name;
    }

    public ArrayList<Route> getRoutes() {

        return routes;
    }

    public String getName() {

        return name;
    }

    public void setRoutes(ArrayList<Route> routes) {

        this.routes = routes;
    }

    public void addRoute(String [] tab,Long epoch){

        routes.add(new Route(tab, map, epoch));
    }
}
