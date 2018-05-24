package Generator;

import java.util.Scanner;



/**
 * Main Generator class
 */

public class DataGenerator {

    private static MapGenerator mapGenerator = new MapGenerator();
    private static FileGenerator fileGenerator = new FileGenerator();

    private static Scanner in = new Scanner(System.in);

    private static int width;
    private static int height;
    private static int size;
    private static int maxBoxes;
    private static boolean test;
    private static String adminDecision = new String();




    public static void main(String args[]){
            System.out.println("Hello Administrator!");

            System.out.println("Please enter map width:");
            width = in.nextInt();
            System.out.println("Please write map height:");
            height = in.nextInt();

            //System.out.println("Please enter map size:");
            //size = in.nextInt();

            System.out.println("Please write maximum count of boxes:");
            maxBoxes = in.nextInt();

            mapGenerator.generate(height, width); //height,width
            test = true;
            adminDecision = in.nextLine();

            while (test) {
                fileGenerator.generate(maxBoxes, width, height);
                System.out.println("Should I generate new DATA file? \ny - yes; other sign - no");
                adminDecision = in.nextLine();
                adminDecision.toLowerCase();
                if (adminDecision.equals("y")){
                    test = true;
                }else{
                    test = false;
                }
            }

            System.out.println("Goodbye!");

    }

}
