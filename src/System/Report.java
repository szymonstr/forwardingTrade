/**
 * In this class reports are generating
 *
 */

package System;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;


public class Report {


    private Logger logger = Logger.getLogger(Report.class.getName());

    private String path;
    private File file;
    private int ifExist = 0;

    private String []dateTime = new String[2];
    private String date;
    private String split = "T";
    private String line = new String();
    private ArrayList<Driver>drivers;
    private ArrayList<RoutesPerDay>routesPerDays = new ArrayList<RoutesPerDay>();
    private ArrayList<String> logs;
    private boolean added = false;
    private long averageDistanceBellmanFord;
    private long averageTimeBellmanFord;
    private long amountBellmanFord;
    private long averageDistancePrim;
    private long averageTimePrim;
    private long amountPrim;
    private long averageDistanceGenetic;
    private long averageTimeGenetic;
    private long amountGenetic;

    public Report(ArrayList<Driver> drivers, ArrayList<String> logs) {

        this.drivers = drivers;
        this.logs = logs;
        this.generateRaport();
        this.writeToFile();
    }

    private void generateRaport() {

        amountBellmanFord = 0;
        averageTimeBellmanFord = 0;
        averageDistanceBellmanFord = 0;

        amountPrim = 0;
        averageTimePrim = 0;
        averageDistancePrim = 0;

        amountGenetic =0;
        averageTimeGenetic = 0;
        averageDistanceGenetic = 0;

        for (int i = 0; i < drivers.size(); i++) {
            for (int k = 0; k < drivers.get(i).getRoutes().size(); k++) {
                date = new Date(drivers.get(i).getRoutes().get(k).getEpoch()).toInstant().toString();
                dateTime = date.split(split);

                added = false;
                //counts routes out per day
                for (int n = 0; n <routesPerDays.size() && added == false; n++ ){
                    if (routesPerDays.get(n).getDate().equals(dateTime[0])){
                        routesPerDays.get(n).setCount(routesPerDays.get(n).getCount() + 1);
                        added = true;
                    }
                }

                if(!added){
                    routesPerDays.add(new RoutesPerDay(dateTime[0]));
                }

                //calculate average route time and distance of all routes
                //BellmanFord
                amountBellmanFord++;
                averageDistanceBellmanFord += drivers.get(i).getRoutes().get(k).getDistanceBellmanFord();
                averageTimeBellmanFord +=  drivers.get(i).getRoutes().get(k).getTimeBellmanFord();

                //Prim
                amountPrim++;
                averageDistancePrim += drivers.get(i).getRoutes().get(k).getDistancePrim();
                averageTimePrim += drivers.get(i).getRoutes().get(k).getTimePrim();

                //Genetic
                amountGenetic++;
                averageDistanceGenetic += drivers.get(i).getRoutes().get(k).getDistanceGenetic();
                averageTimeGenetic += drivers.get(i).getRoutes().get(k).getTimeGenetic();
            }
        }
        averageTimeBellmanFord = averageTimeBellmanFord/amountBellmanFord;
        averageDistanceBellmanFord = averageDistanceBellmanFord/amountBellmanFord;

        averageTimePrim = averageTimePrim/amountPrim;
        averageDistancePrim = averageDistancePrim/amountPrim;

        averageTimeGenetic = averageTimeGenetic/amountGenetic;
        averageDistanceGenetic = averageDistanceGenetic/amountGenetic;

    }

    private void writeToFile(){

        path = "./Report.txt";

        while(new File(path).exists()) {
            ifExist++;
            path = "./Report" + ifExist + ".txt";
        }

        file  = new File(path);

        try {
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            //prints routes per day
            bw.write("Routes per day:\r\n");
            for (int i = 0; i < routesPerDays.size(); i++) {
                line = routesPerDays.get(i).getDate() + ": " + routesPerDays.get(i).getCount() + "routes.\r\n";
                bw.write(line);
            }

            //prints routes per driver
            bw.write("\r\n");
            bw.write("Routes per driver:\r\n");
            for (int i = 0; i < drivers.size(); i++){
                line = drivers.get(i).getName() + ": " + drivers.get(i).getRoutes().size() + "routes.\r\n";
                bw.write(line);
            }

            //print Bellman Ford algorithm results
            bw.write("\r\n");
            bw.write("BellmanFord\r\n");
            line = "Average Time: " + averageTimeBellmanFord + "\r\n" + "Average Distance: " + averageDistanceBellmanFord + "\r\n";
            bw.write(line);

            //print Prim algorithm results
            bw.write("\r\n");
            bw.write("Prim\r\n");
            line = "Average Time: " + averageTimePrim + "\r\n" + "Average Distance: " + averageDistancePrim + "\r\n";
            bw.write(line);

            //print Genetic algorithm results
            bw.write("\r\n");
            bw.write("Genetic Algorithm\r\n");
            line = "Average Time: " + averageTimeGenetic + "\r\n" + "Average Distance: " + averageDistanceGenetic + "\r\n";
            bw.write(line);

            //If exist warnings print it at the bottom of report
            if (logs.size()>0){
                bw.write("\r\n");
                bw.write("\r\n");
                bw.write("Logs: \r\n");
                for (int i= 0; i < logs.size(); i++) {
                    bw.write(logs.get(i) + "\r\n");
                }
            }

            bw.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }


    }
}
