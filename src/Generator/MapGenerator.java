package Generator;

import java.io.*;
import java.util.Random;
import java.util.logging.Logger;

public class MapGenerator {

    private String path = "./MAP.csv";
    private File file = new File(path);
    private Logger logger = Logger.getLogger(MapGenerator.class.getName());
    private String coordinates = new String();
    private String line = "";
    private Random random = new Random();
    private int k;

    public void generate(int size) { //int width, int height

        //if file exists generator will delete it
        if (file.exists()){
            file.delete();
            logger.warning("Old map was deleted!");
        }


        try{
            file.createNewFile();  //create a map file

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            k=1;
            for (int i = 0; i < size + size - 1 ; i++){
                if (k%2 ==0){
                    line = "";
                    for (int p = 0; p < size; p++){
                        line = line + (random.nextInt(9) + 1);

                    }
                    line = line + "\r\n";
                    bw.write(line);
                }else{
                    line ="";
                    for (int p = 0; p < size -1; p++){
                        line = line + (random.nextInt(9) + 1);

                    }
                    line = line + "\r\n";
                    bw.write(line);
                }
                k++;
            }
            /*
            for (int y = 1; y<=height ; y++ ){
                for (int x=1; x<=width ; x++){
                    coordinates = "(" + x + "," + y + ")";  //coordinates of points
                    if (x==width){
                        line = line + coordinates + "\r\n";
                    }else{
                        line = line + coordinates + ",";
                    }
                    bw.write(line);  //write coordinates to file
                    line = "";
                }

            }
            */
            bw.close();
        }catch (IOException e){
            logger.warning(e.getMessage());
        }

        logger.info("Map is ready to use.");
    }


}
