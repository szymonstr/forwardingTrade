/**
 * Useful if we need convert Long timestamp to date and time
 */

package System;

import java.util.Date;

public class EpochToString {

    private static final String SPLIT = "T";
    private static final char REPLACE =  'Z';
    private static String[] dateTime = new String[2];


    public static String convertLong(Long epoch) {
        String data = new Date(epoch).toInstant().toString();
        dateTime = data.split(SPLIT);
        String date = dateTime[0];
        String time = dateTime[1];
        time = time.replace(REPLACE, ' ');
        time = time.trim();

        return date + " " + time;

    }
}
