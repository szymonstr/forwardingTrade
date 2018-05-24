package Generator;

import java.util.Random;

public class BoxGenerator {

    private Random rand = new Random();
    private int x;
    private int y;
    private String coordinates = new String();
    private String load = new String();

    //generates packages to delivery for drivers
    public String generate (int maxBoxes, int width, int height){

        load = "";

        for (int i = 0; i < maxBoxes; i++){
            x = rand.nextInt(width) + 1;
            y = rand.nextInt(height) + 1;
            coordinates = "(" + x + "," + y + ")";
            if (i == (maxBoxes - 1)) {
                load = load + coordinates;
            }else{
                load = load + coordinates + ",";
            }
        }

        return load;
    }
}
