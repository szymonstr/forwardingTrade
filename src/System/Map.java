/**
 * Changing map from file to usable data for algorithms
 */

package System;

import System.Exception.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;
import java.lang.String;


public class Map {

    private ArrayList<String> pointsList = new ArrayList<String>();
    private HashMap<String,Integer> adjacencyMatrix = new HashMap<String, Integer>();
    private ArrayList<Integer> sizes = new ArrayList<Integer>();
    private ArrayList<char[]> rows = new ArrayList<char[]>();
    private Scanner in = new Scanner(System.in);
    private String path = "./MAP.csv";
    private File file = new File(path);
    private Logger logger = Logger.getLogger(Map.class.getName());
    private BufferedReader bufferedReader;
    private String line = "";
    private String key;
    private String read;
    private boolean running;
    private boolean test;
    private int x;
    private int y;
    private int k;
    private int row;
    private int width;
    private int size1;
    private int size2;

    public ArrayList<String> getPointsList() {
        return pointsList;
    }

    public HashMap<String, Integer> getAdjacencyMatrix() {
        return adjacencyMatrix;
    }


    public boolean load() throws FileException {

        //start settings
        width = 0;
        row = 0;
        running = true;
        x = 0;
        k = 0;
        rows.clear();

        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                while ((line = bufferedReader.readLine()) != null && running) {


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


                    // add good data
                    if (running) {
                        rows.add(chars);
                        k++;
                    }

                }



                //change data to useful to algorithms
                if (running) {

                    if (rows.size()%2 == 0){
                        int last = rows.size() -1;
                        rows.remove(last);
                    }

                    //prepare adjacency matrix
                    for (int i =0; i<rows.size(); i++) {

                        y=1;    //in all of iteration we need y set to 1

                        if (i % 2 == 0) {
                            row++;
                            //System.out.println(x + " x"+ row + "  row");
                        }

                        x = row;

                        //if row of file is even we need one sign less from chars array
                        if (i % 2 == 0) {
                            for (int ii = 0; ii < rows.get(i).length - 1; ii++) {

                                key = "(" + x + "," + y + ")-(" + x + "," + (y + 1) + ")";
                                adjacencyMatrix.put(key, Character.getNumericValue(rows.get(i)[ii]));
                                y++;
                            }
                        } else {

                            for (int ii = 0; ii < rows.get(i).length; ii++) {
                                width = y;
                                key = "(" + x + "," + y + ")-(" + (x + 1) + "," + y + ")";
                                adjacencyMatrix.put(key, Character.getNumericValue(rows.get(i)[ii]));
                                y++;
                            }
                        }

                    }


                    for (int x = 1; x <= row; x++) {
                        for (int y = 1; y <= width; y++) {
                            pointsList.add("(" + x + "," + y + ")");
                        }
                    }

                }

            } else {
                throw new FileException("Not found!");
            }
            bufferedReader.close();
        }catch (IOException e){

        }

        return running;

    }


}
