package System;

import java.util.ArrayList;

public class Driver {

    private ArrayList<Route> routes = new ArrayList<Route>();
    private String name = new String();


    public Driver(String name) {
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

    public void addRoute(String [] tab){
        routes.add(new Route(tab));
    }
}
