package System;

import System.Exception.FileException;
import System.Map;

import java.util.HashMap;

public class MapTest {

    private static boolean running;
    private static Map map = new Map();

    public static void main(String[] args) {

       HashMap<String,Integer> point = new HashMap<String, Integer>();

        try {
            running = map.load();
        }catch(FileException e) {
            e.getMessage();
        }
        System.out.println(map.getPointsList().toString());
        int number;
        String source = "(1,1)";
        number = 0;
        point.put(source, number);
        for (int i =  0; i< map.getPointsList().size(); i++){

            if (source.equals(map.getPointsList().get(i))){
                //empty
            }else {
                number++;
                point.put(map.getPointsList().get(i), number);
            }
        }

        //System.out.println(point.toString());

    }
}
