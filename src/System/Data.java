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
    private ArrayList<Driver> drivers;
    private String path = "./DATA.csv";
    private File file;
    private Logger logger = Logger.getLogger(Data.class.getName());
    private BufferedReader bufferedReader;
    private String line = "";
    private String csvSplit = ",";
    private String [] tab;
    private String pathOldData = "./old_DATA.csv";
    private int numberOldDataFile = 0;
    private int count = 0;
    private boolean added = false;
    private boolean newDriver = false;
    private boolean endOfFile = false;
    private long epoch = 0;

    public Data(ArrayList<Driver> drivers, int count) {
        this.drivers = drivers;
        this.count = count;
    }

    public ArrayList<Driver> getDrivers() {
        return drivers;
    }

    public int getCount() {
        return count;
    }

    public void load(Map cityMap) throws FileException {
        file = new File(path);
        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
               while (!endOfFile) {
                    if ((line = bufferedReader.readLine()) != null) {

                        tab = line.split(csvSplit);
                        added = false;
                        newDriver = true;

                        if (epoch <= Long.parseLong(tab[0])) {

                            //looking for driver and adding his route
                            for (int i = 0; i < drivers.size() && added == false; i++) {
                                if (drivers.get(i).getName().equals(tab[1])) {
                                    drivers.get(i).addRoute(tab);
                                    added = true;
                                    newDriver = false;
                                }
                            }

                            //if driver does not exist
                            if (newDriver) {
                                drivers.add(new Driver(tab[1], cityMap));
                                drivers.get(drivers.size() - 1).addRoute(tab);
                                newDriver = false;
                            }

                            // if system loaded 100 commissions, should be generated a report
                            count++;
                            if (count == 100) {
                                new Report(drivers);
                                logger.info("New report was generated");
                                count = 0;
                                drivers = new ArrayList<Driver>();
                            }
                            epoch = Long.parseLong(tab[0]);
                        }else{
                            logger.warning("Problem with time stamp!");
                        }
                    }else {
                        //all routes was loaded
                        endOfFile = true;
                        bufferedReader.close();
                        while(new File(pathOldData).exists()){
                            numberOldDataFile++;
                            pathOldData = "./old" + numberOldDataFile + "_DATA.csv";
                        }
                        file.renameTo(new File(pathOldData));
                        logger.info("All routes from data file was loaded.");
                    }
                }
            } else {
                throw new FileException("Not found!");
            }

        }catch (IOException e){

        }
    }
}
