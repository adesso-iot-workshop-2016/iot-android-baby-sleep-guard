package de.adesso.wunderbar.wunderbartest.Helper;

import com.google.gson.Gson;

import io.relayr.java.model.AccelGyroscope;
import io.relayr.java.model.action.Reading;

/**
 * Created by sdrees on 18.05.2016.
 */
public class AccelerationConverter extends ValueConverter{

    private final String unit;
    private double lastDist = 0;

    public AccelerationConverter(){
        this.unit = "g";
        this.maxValue = 100;
    }

    @Override
    public String getReadableString(Reading reading) {
        AccelGyroscope.Acceleration acc = new Gson().fromJson(reading.value.toString(),
                AccelGyroscope.Acceleration.class);
        return ("x: " + acc.x + unit + "\ny: " + acc.y + unit + "\nz: " + acc.z + unit);
    }

    @Override
    public double getCompareValue(Reading reading) {
        AccelGyroscope.Acceleration acc = new Gson().fromJson(reading.value.toString(),
                AccelGyroscope.Acceleration.class);
        double dist = Math.sqrt(acc.x*acc.x + acc.y*acc.y + acc.z*acc.z);
        if(lastDist==0){
            lastDist = dist;
            return 0;
        }
        double abs = Math.abs(lastDist - dist);
        lastDist = dist;
        return abs;
    }

    @Override
    public int getMaxValue() {
        return maxValue;
    }

}
