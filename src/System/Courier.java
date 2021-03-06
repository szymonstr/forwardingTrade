/**
 * Main class of forwarding trade system
 */


package System;

import System.Exception.FileException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class Courier {

    private static Map map = new Map();
    private static Data data;
    private static Logger logger = Logger.getLogger(Courier.class.getName());
    private static Scanner in = new Scanner(System.in);
    private static String adminDecision = new String();
    private static ArrayList<Driver> drivers = new ArrayList<Driver>();
    private static boolean test;
    private static int count = 0;
    private static boolean running;

    public static void main(String args[]){

        try{
            running = map.load();
        }catch (FileException e) {
            logger.warning(e.getMessage());
        }

        if (running) {
            do {
                data = new Data(drivers, count, map);
                try {
                    data.load();
                } catch (FileException e) {
                    logger.warning(e.getMessage());
                }
                System.out.println("Do you want to load a new DATA.csv file \ny - yes; other sign - no");
                adminDecision = in.nextLine();
                adminDecision.toLowerCase();
                if (adminDecision.equals("y")) {
                    test = true;
                    drivers = data.getDrivers();
                    count = data.getCount();
                } else {
                    test = false;
                }
            } while (test);

            if (data.getDrivers().size() > 0) {
                new Report(data.getDrivers(), data.getLogs());
            }
        }
        System.out.println("Goodbye.");
    }
}
