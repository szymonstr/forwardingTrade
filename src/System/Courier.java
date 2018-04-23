package System;

import System.Exception.FileException;

import java.util.logging.Logger;

public class Courier {

    private static Map map = new Map();
    private static Data data = new Data();
    private static Logger logger = Logger.getLogger(Courier.class.getName());

    public static void main(String args[]){

        try{
            map.load();
        }catch (FileException e) {
            logger.warning(e.getMessage());
        }

        try {
            data.load(map);
        } catch (FileException e) {
            logger.warning(e.getMessage());
        }
    }
}
