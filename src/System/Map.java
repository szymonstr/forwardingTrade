package System;

import System.Exception.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;
import java.lang.String;

/**
 * Changing map from file to usable data for algorithms
 */

public class Map {

    //private HashMap<String,Integer> points = new HashMap<String, Integer>();
    private ArrayList<String> pointsList = new ArrayList<String>();
    private HashMap<String,Integer> adjacencyMatrix = new HashMap<String, Integer>();
    private ArrayList<Integer> sizes = new ArrayList<Integer>();
    //private ArrayList<Connection> connections = new ArrayList<Connection>();
    private Scanner in = new Scanner(System.in);
    private String path = "./MAP.txt";
    private File file = new File(path);
    private Logger logger = Logger.getLogger(Map.class.getName());
    private BufferedReader bufferedReader;
    private String line = "";
    //private String csvSplit = ",";
    //private String [] tab;
    private String key;
    private String read;
    private boolean running;
    private boolean test;
    //private char[] coordinates;
    private int x;
    private int y;
    private int k;
    private int row;
    private int size1;
    private int size2;
    //private int number;
    //private String pointSrc;
    //private String pointDest;



    public ArrayList<String> getPointsList() {
        return pointsList;
    }

    public HashMap<String, Integer> getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
    /*
    public HashMap<String, Integer> getPoints() {
        return points;
    }

    public ArrayList<Connection> getConnections() {

        return connections;
    }
    */

    public boolean load() throws FileException {

        //start settings
        row = 0;
        running = true;
        x = 0;
        k = 0;

        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                while ((line = bufferedReader.readLine()) != null && running) {


                    y=1;    //in all of iteration we need y set to 1

                    //checking rows of the date file
                    char[] chars = line.toCharArray();
                    if (k%2 == 0) {
                        size1 = chars.length;
                        size2 = size1;
                        sizes.add(size1);
                    }else{

                        size2 = chars.length;
                    }

                    //checking data
                    for (int i = 0; i < chars.length; i++) {

                        if (Character.isDigit(chars[i]) && size1 == size2) {
                            running = true;
                        }else if (size1 == size2){
                            logger.warning(chars[i] + "is not a digit!!!");
                            test = true;
                            while (test) {
                            System.out.println("Do you want to change it? -y- \nOr exit? -n-");

                                read = in.nextLine();
                                if (read.equals("y")) {
                                    System.out.println("Write int value:");
                                    read = in.nextLine();
                                    if(Character.isDigit(read.toCharArray()[0])){
                                        chars[i] = read.toCharArray()[0];
                                        test = false;
                                        running = true;
                                    }else{
                                        System.out.println("Wrong value. Try again.");
                                        test = true;
                                        running = false;
                                    }
                                }else if(read.equals("n")){
                                    test = false;
                                    running = false;
                                }else{
                                    System.out.println("Error! Try again.");
                                }
                            }
                        }else {
                            logger.warning("Critical Error!!! Program will be closed.");
                            running = false;
                        }
                    }

                    //checking rows (should be the same length)
                    for (int i = 1; i < sizes.size(); i++){
                        if (sizes.get(i) != sizes.get(i-1)){
                            logger.warning("Critical Error!!! Program will be closed.");
                            running = false;
                        }
                    }


                    //prepare adjacency matrix
                    if (running) {


                        if (k % 2 == 0) {
                            row++;
                            //System.out.println(x + " x"+ row + "  row");
                        }

                        x = row;

                        //if row of file is even we need one sign less from chars array
                        if (k % 2 == 0) {
                            for (int i = 0; i < chars.length-1; i++) {

                                key = "(" + x + "," + y + ")-(" + x  + "," + (y+1) + ")";
                                //System.out.println(key + "\t\t" + Character.getNumericValue(chars[i]));
                                adjacencyMatrix.put(key, Character.getNumericValue(chars[i]));
                                y++;
                            }
                        } else {

                            for (int i = 0; i < chars.length ; i++) {

                                key = "(" + x + "," + y + ")-(" + (x+1) + "," + y  + ")";
                                //System.out.println(key + "\t\t" + Character.getNumericValue(chars[i]));
                                adjacencyMatrix.put(key, Character.getNumericValue(chars[i]));
                                y++;
                             }
                        }

                        k++;
                    }

                }


                //change data to useful to algorithms
                if (running) {
                    //number = 0;
                    for (int x = 1; x <= row; x++) {
                        for (int y = 1; y <= row; y++) {
                            //points.put("(" + x + "," + y + ")", number);
                            //System.out.println("(" + x + "," + y + ")    " + number);
                            pointsList.add("(" + x + "," + y + ")");
                           // number++;
                        }
                    }
                    /*
                    for (int i = 0; i < pointsList.size(); i++) {
                        pointSrc = pointsList.get(i);
                        for (int j = 0; j < pointsList.size(); j++) {
                            pointDest = pointsList.get(j);
                            if (adjacencyMatrix.get(pointSrc + "-" + pointDest) != null) {
                                connections.add(new Connection(points.get(pointSrc), points.get(pointDest), adjacencyMatrix.get(pointSrc + "-" + pointDest)));
                            }
                        }
                    }
                    */
                    /*
                    System.out.println(adjacencyMatrix.size());


                    System.out.println(connections.size());
                    for (int i = 0; i < connections.size(); i++) {
                        System.out.println(connections.get(i).getSrc() + "   " + connections.get(i).getDest() + "   " + connections.get(i).getWeight());
                    }
                    */
                }

                    //System.out.println("while");
            } else {
                throw new FileException("Not found!");
            }
            bufferedReader.close();
        }catch (IOException e){

        }

        return running;

    }


}
