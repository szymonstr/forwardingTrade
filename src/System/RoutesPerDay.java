/**
 * This class counts routes out per day
 */

package System;

public class RoutesPerDay {

    private int count;
    private String date;

    public RoutesPerDay(String date) {
        this.date = date;
        this.count = 1;
    }

    public int getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
