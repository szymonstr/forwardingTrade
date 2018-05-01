package System;

import System.Exception.*;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.lang.String;

public class Map {

    private ArrayList<String> points = new ArrayList<String>();
    private String path = "./MAP.csv";
    private File file = new File(path);
    private Logger logger = Logger.getLogger(Map.class.getName());
    private BufferedReader bufferedReader;
    private Coordinates coordinates;
    private String line = "";
    private String csvSplit = ",";
    private String [] tab;
    int x;
    int y;


    public ArrayList<String> getPoints() {
        return points;
    }

    public void load() throws FileException {
        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                while ((line = bufferedReader.readLine()) != null) {

                    tab = line.split(csvSplit);

                    for (int i = 0; i < tab.length; i += 2) {  // i += 2 or i++
                            points.add(tab[i]+","+tab[i+1]);             //if csvSplit == "," -> points.add(tab[i] + "," + tab[i+1]) else if csvSplit == ";" -> points.add(tab[i])
                            //System.out.println("if");
                        }
                        //System.out.println("for");
                    }
                    //System.out.println("while");
            } else {
                throw new FileException("Not found!");
            }
            bufferedReader.close();
        }catch (IOException e){

        }
    }


}
