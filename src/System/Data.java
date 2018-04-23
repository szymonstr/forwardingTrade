package System;

import System.Exception.FileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Data {


    private ArrayList<String> points = new ArrayList<String>();
    private ArrayList<Driver> drivers = new ArrayList<Driver>();
    private String path = "./DATA.csv";
    private File file = new File(path);
    private Logger logger = Logger.getLogger(Data.class.getName());
    private BufferedReader bufferedReader;
    private String line = "";
    private String csvSplit = ";";
    private String [] tab;
    private int count = 0;
    private boolean added = false;
    private boolean newDriver = false;




    public void load(Map cityMap) throws FileException {
        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                while (true) {     // in this place should be other solution in the future
                    if ((line = bufferedReader.readLine()) != null) {

                        tab = line.split(csvSplit);
                        added = false;
                        newDriver = true;

                        //looking for driver and adding his route
                        for (int i = 0; i < drivers.size() && added == false ; i++){
                            if (drivers.get(i).getName().equals(tab[1])){
                                drivers.get(i).addRoute(tab);
                                added = true;
                                newDriver = false;
                            }
                        }

                        //if driver does not exist
                        if (newDriver){
                            drivers.add(new Driver(tab[1]), cityMap);
                            drivers.get(drivers.size() - 1).addRoute(tab);
                            newDriver = false;
                        }

                        // if system loaded 100 commissions, should be generated a report
                        count++;
                        if (count == 100){
                            new Report(drivers);
                            logger.info("New report was generated");
                        }
                    }else {
                        //all routes was loaded so data file will be deleted
                        file.delete();
                        logger.info("File with old data was deleted");
                    }
                }
            } else {
                throw new FileException("Not found!");
            }
        }catch (IOException e){

        }
    }
}
