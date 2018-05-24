package Generator;


import java.util.Random;

public class DriverGenerator {

    private String name = new String();
    private Random rand = new Random();
    private int number;

    //generates drivers
    public String generate (){
        number = rand.nextInt(5) + 1;

        switch (number){
            case 1:
                name = "Tom";
                break;
            case 2:
                name = "Dom";
                break;
            case 3:
                name = "Bem";
                break;
            case 4:
                name = "Janusz";
                break;
            case 5:
                name = "Simon";
                break;
        }

        return name;

    }
}
