/**
 * Data class witch is loading data from file to the program
 *
 * It checks correctness off data.
 *
 * Generates new drivers if it necessary and assigns routes to drivers
 *
 */

package System;

import System.Exception.FileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class Data {


    private String[] points;
    private ArrayList<Driver> drivers;
    private ArrayList<String> logs = new ArrayList<String>();
    private ArrayList<String> wrongPoints = new ArrayList<String>();
    private String path = "./DATA.csv";
    private File file;
    private Logger logger = Logger.getLogger(Data.class.getName());
    private Map cityMap;
    private BufferedReader bufferedReader;
    private Scanner in = new Scanner(System.in);
    private String line = "";
    private String csvSplit = ",";
    private String [] tab;
    private String pathOldData = "./old_DATA.csv";
    private String adminDecision = new String();
    private String log;
    private int checkPoints = 0;
    private int numberOldDataFile = 0;
    private int count = 0;
    private boolean added = false;
    private boolean newDriver = false;
    private boolean endOfFile = false;
    private long epoch = 0;

    public Data(ArrayList<Driver> drivers, int count, Map cityMap ) {
        this.drivers = drivers;
        this.count = count;
        this.cityMap = cityMap;
    }

    public ArrayList<Driver> getDrivers() {
        return drivers;
    }

    public ArrayList<String> getLogs() {
        return logs;
    }

    public int getCount() {
        return count;
    }

    public void load() throws FileException {
        file = new File(path);
        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
               while (!endOfFile) {
                    if ((line = bufferedReader.readLine()) != null) {

                        tab = line.split(csvSplit);
                        tab[tab.length -1] = tab[tab.length-1].replace(';', ' ');
                        tab[tab.length -1] = tab[tab.length-1].trim();
                        added = false;
                        newDriver = true;

                        //checking time stamp
                        //routes lower in file should be newer
                        if (epoch <= Long.parseLong(tab[0])) {
                            //System.out.println(epoch + "<=" +tab[0]);
                            addRoutesToDrivers();
                            epoch = Long.parseLong(tab[0]);
                        }else{
                            log = "Driver: " + tab[1] +" EPOCH: " + tab[0] + " (" + EpochToString.convertLong(Long.parseLong(tab[0]))+ ") Route is older then "+ epoch +" (" + EpochToString.convertLong(epoch) +").";
                            logger.warning(log);
                            System.out.println("Do you want calculate this route? \n y - yes other sign - no");
                            adminDecision = in.nextLine();
                            adminDecision.toLowerCase();
                            //adminDecision = "y";        //<- if admin wants accept all routes with problems connection with time stamps
                            if (adminDecision.equals("y")){
                                addRoutesToDrivers();
                                logs.add("Warning! " + log + "\r\nAdministrator accepted this route." );
                            }else{
                                logs.add("Warning! " + log + "\r\nAdministrator rejected this route." );
                            }
                        }

                        // if system loaded 100 commissions, should be generated a report
                        if (count == 100) {
                            new Report(drivers, logs);
                            logger.info("New report was generated");
                            count = 0;
                            drivers = new ArrayList<Driver>();
                            logs.clear();
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
                        logger.info("All routes from data file were loaded.");
                    }
                }
            } else {
                throw new FileException("Not found!");
            }

        }catch (IOException e){

        }
    }


    private void addRoutesToDrivers(){

        prepareData();

        if (checkPointsOnMap()) {

            //looking for driver and adding his route
            for (int i = 0; i < drivers.size() && added == false; i++) {
                if (drivers.get(i).getName().equals(tab[1])) {
                    drivers.get(i).addRoute(points, Long.parseLong(tab[0]));
                    added = true;
                    newDriver = false;
                }
            }

            //if driver does not exist
            if (newDriver) {
                drivers.add(new Driver(tab[1], cityMap));
                drivers.get(drivers.size() - 1).addRoute(points, Long.parseLong(tab[0]));
                newDriver = false;
            }

            count++;
        }

    }

    //prepare data to calculate the route
    private void prepareData(){
        points = new String[(tab.length - 2)/2];
        for (int i = 0; i < points.length; i++){
            points[i] = tab[2 * i + 2] + "," + tab[2 * i + 3];
        }

    }

    //checking boxes coordinates with points on map
    private boolean checkPointsOnMap(){
        checkPoints = 0;
        boolean testPoints; // = false;
        wrongPoints.clear();

        for (int i = 0; i < points.length; i++){
            if (cityMap.getPointsList().contains(points[i])){
                checkPoints++;
            }else{
                wrongPoints.add(points[i]);
            }
        }
        //if points where should be delivered boxes are unreachable, program inform administrator that found unknown point
        if (checkPoints != points.length){
            if (wrongPoints.size()>1) {
                log = "Driver: " + tab[1] + " EPOCH: " + tab[0] + "\r\n Unknown points: " + wrongPoints.toString();
            }else{
                log = "Driver: " + tab[1] + " EPOCH: " + tab[0] + "\r\n Unknown point: " + wrongPoints.toString();
            }
            logger.warning(log);
            logs.add("Warning! " + log + "\r\nRoute was rejected." );
            testPoints = false;
        }else{
            testPoints = true;
        }
        return testPoints;


    }
}
