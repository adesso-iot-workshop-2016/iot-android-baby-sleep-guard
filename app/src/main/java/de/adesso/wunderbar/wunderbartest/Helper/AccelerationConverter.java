package de.adesso.wunderbar.wunderbartest.Helper;

import com.google.gson.Gson;

import io.relayr.java.model.AccelGyroscope;
import io.relayr.java.model.action.Reading;

/**
 * Created by sdrees on 18.05.2016.
 */
public class AccelerationConverter extends ValueConverter{

    private final String unit;

    public AccelerationConverter(){
        this.unit = "%";
    }

    @Override
    public String getReadableString(Reading reading) {
        AccelGyroscope.Acceleration acc = new Gson().fromJson(reading.value.toString(),
                AccelGyroscope.Acceleration.class);
        return ("x: " + acc.x + ", y: " + acc.y + ", z:" + acc.z);
    }

    @Override
    public double getCompareValue(Reading reading) {
        AccelGyroscope.Acceleration acc = new Gson().fromJson(reading.value.toString(),
                AccelGyroscope.Acceleration.class);
        double dist = Math.sqrt(acc.x*acc.x + acc.y*acc.y + acc.z*acc.z);
        return dist;
    }

}
