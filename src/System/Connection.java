package System;

public class Connection {

    private int src;
    private int dest;
    private int weight;

    // connections between points on map, and weight of this connections
    public Connection(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public int getSrc() {
        return src;
    }

    public int getDest() {
        return dest;
    }

    public int getWeight() {
        return weight;
    }
}
