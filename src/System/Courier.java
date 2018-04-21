package System;

import System.Exception.FileExecption;

import java.io.IOException;
import java.util.logging.Logger;

public class Courier {

    private static Map map = new Map();
    private static Logger logger = Logger.getLogger(Courier.class.getName());

    public static void main(String args[]){

        try{
            map.load();
        }catch (FileExecption e) {
            logger.warning(e.getMessage());
        }
    }
}
