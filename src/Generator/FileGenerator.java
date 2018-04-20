package Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.Random;

public class FileGenerator {

    private DriverGenerator driverGenerator = new DriverGenerator();
    private BoxGenerator boxGenerator = new BoxGenerator();
    private Logger logger = Logger.getLogger(FileGenerator.class.getName());

    private LocalDate localDate;
    private LocalTime localTime;
    private String name = new String();
    private String load = new String();
    private String line = new String();
    private String date = new String();
    private String time = new String();

    private Random rand = new Random();
    private int count;

    private String path = "./DATA.csv";
    private File file = new File(path);


    public void generate(int maxBoxes, int width, int height) {


        while (!file.exists()) {

            count = rand.nextInt(500) + 1;

            try {
                file.createNewFile();

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);

                for (int i = 1; i <= count; i++) {
                    localDate = LocalDate.now();
                    localTime = LocalTime.now();
                    name = driverGenerator.generate();
                    load = boxGenerator.generate(maxBoxes, width, height);
                    date = localDate.toString();
                    time = localTime.getHour() + ":" + localTime.getMinute();
                    line = date + ";" + time + ";" + name + ";" + load + "\r\n";
                    bw.write(line);
                }

                bw.close();

            } catch (IOException e) {
                logger.warning(e.getMessage());

            }
        }


    }
}
