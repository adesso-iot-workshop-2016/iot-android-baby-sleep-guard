package de.adesso.wunderbar.wunderbartest.Helper;

import com.google.gson.Gson;

import io.relayr.java.model.LightColorProx;
import io.relayr.java.model.action.Reading;

/**
 * Created by sdrees on 18.05.2016.
 */
public class LightColorConverter extends ValueConverter{

    private final String unit;

    public LightColorConverter(){
        this.unit = "%";
    }

    @Override
    public String getReadableString(Reading reading) {
        LightColorProx.Color col = new Gson().fromJson(reading.value.toString(),
                LightColorProx.Color.class);
        return ("red: " + getPercentage(col.red,4096) + unit + "\ngreen: " + getPercentage(col.green,4096) + unit +"\nblue: " + getPercentage(col.blue,4096)+ unit);
    }

    @Override
    public double getCompareValue(Reading reading) {
        LightColorProx.Color col = new Gson().fromJson(reading.value.toString(),
                LightColorProx.Color.class);
        double dist = Math.sqrt(col.red*col.red + col.green*col.green + col.blue*col.blue);
        return dist;
    }

}
