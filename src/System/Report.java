/**
 *
 * TODO
 * calculation average distance and average time per algorithm
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
    private boolean firstDate = true;
    private boolean added = false;
    private long averageDistanceBellmanFord;
    private long averageTimeBellmanFord;
    private long amountBellmanFord;

    public Report(ArrayList<Driver> drivers) {

        this.drivers = drivers;
        this.generateRaport();
        this.writeToFile();
    }

    private void generateRaport() {

        amountBellmanFord = 0;
        averageTimeBellmanFord = 0;
        averageDistanceBellmanFord = 0;

        for (int i = 0; i < drivers.size(); i++) {
            for (int k = 0; k < drivers.get(i).getRoutes().size(); k++) {
                date = new Date(drivers.get(i).getRoutes().get(k).getEpoch()).toInstant().toString();
                dateTime = date.split(split);

                added = false;

                for (int n = 0; n <routesPerDays.size() && added == false; n++ ){
                    if (routesPerDays.get(n).getDate().equals(dateTime[0])){
                        routesPerDays.get(n).setCount(routesPerDays.get(n).getCount() + 1);
                        added = true;
                    }
                }

                if(!added){
                    routesPerDays.add(new RoutesPerDay(dateTime[0]));
                }

                //average route time and distance
                //BellmanFord


                amountBellmanFord++;
                averageDistanceBellmanFord += drivers.get(i).getRoutes().get(k).getDistanceBellmanFord();
                averageTimeBellmanFord +=  drivers.get(i).getRoutes().get(k).getTimeBellmanFord();



            }
        }
        averageTimeBellmanFord = averageTimeBellmanFord/amountBellmanFord;
        averageDistanceBellmanFord = averageDistanceBellmanFord/amountBellmanFord;

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

            bw.write("Routes per day:\r\n");
            for (int i = 0; i < routesPerDays.size(); i++) {
                line = routesPerDays.get(i).getDate() + ": " + routesPerDays.get(i).getCount() + "routes.\r\n";
                bw.write(line);
            }

            bw.write("\r\n");
            bw.write("Routes per driver:\r\n");
            for (int i = 0; i < drivers.size(); i++){
                line = drivers.get(i).getName() + ": " + drivers.get(i).getRoutes().size() + "routes.\r\n";
                bw.write(line);
            }

            bw.write("\r\n");
            bw.write("BellmanFord\r\n");
            line = "Average Time: " + averageTimeBellmanFord + "\r\n" + "Average Distance: " + averageDistanceBellmanFord + "\r\n";
            bw.write(line);


            bw.write("\r\n");
            bw.write("alg2\r\n");

            bw.write("\r\n");
            bw.write("alg3\r\n");

            bw.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }


    }
}
