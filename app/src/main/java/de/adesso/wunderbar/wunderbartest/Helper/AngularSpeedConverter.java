package de.adesso.wunderbar.wunderbartest.Helper;

import com.google.gson.Gson;

import io.relayr.java.model.AccelGyroscope;
import io.relayr.java.model.action.Reading;

/**
 * Created by sdrees on 18.05.2016.
 */
public class AngularSpeedConverter extends ValueConverter{

    private final String unit;

    public AngularSpeedConverter(){
        this.unit = "°/s";
        this.maxValue = 100;
    }

    @Override
    public String getReadableString(Reading reading) {
        AccelGyroscope.AngularSpeed aas = new Gson().fromJson(reading.value.toString(),
                AccelGyroscope.AngularSpeed.class);
        return ("x: " + aas.x + unit + "\ny: " + aas.y+ unit + "\nz: " + aas.z + unit);
    }

    @Override
    public double getCompareValue(Reading reading) {
        AccelGyroscope.AngularSpeed aas = new Gson().fromJson(reading.value.toString(),
                AccelGyroscope.AngularSpeed.class);
        double dist = Math.sqrt(aas.x*aas.x + aas.y*aas.y + aas.z*aas.z);
        return dist;
    }

    @Override
    public int getMaxValue() {
        return maxValue;
    }


}
