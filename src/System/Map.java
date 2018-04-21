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
    private String cvsSplit = ";";
    private String [] tab;
    int x;
    int y;




    public void load() throws FileExecption {
        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                while ((line = bufferedReader.readLine()) != null) {

                    tab = line.split(cvsSplit);

                    for (int i = 0; i < tab.length; i++) {
                            points.add(tab[i]);
                            //System.out.println("if");
                            System.out.println(tab[i]);
                        }
                        //System.out.println("for");
                    }
                    //System.out.println("while");
                } else {
                throw new FileExecption("Not found!");
                }
        }catch (IOException e){

        }
    }
}
