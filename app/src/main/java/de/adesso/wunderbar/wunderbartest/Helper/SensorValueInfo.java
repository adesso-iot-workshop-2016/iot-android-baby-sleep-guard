package de.adesso.wunderbar.wunderbartest.Helper;

import android.graphics.Color;

import io.relayr.java.model.action.Reading;

/**
 * Created by sdrees on 17.05.2016.
 */
public class SensorValueInfo {

    private final String sensorName;
    private final double low;
    private final int lowColor;
    private final double high;
    private final int highColor;
    private final String lowInfo;
    private final String highInfo;
    private final String normalInfo;
    private ValueConverter converter;

    public SensorValueInfo(String sensorName, double low,int lowColor, double high, int highColor, String lowInfo, String highInfo, String normalInfo, ValueConverter converter){
        this.sensorName = sensorName;
        this.low = low;
        this.lowColor = lowColor;
        this.high = high;
        this.highColor = highColor;
        this.lowInfo = lowInfo;
        this.highInfo = highInfo;
        this.normalInfo = normalInfo;
        this.converter = converter;
    }

    public String getSensorName(){
        return this.sensorName;
    }

    public String getInfo(Reading reading){
        double value = this.converter.getCompareValue(reading);
        if(value < low){
            return lowInfo;
        }
        if(value > high){
            return highInfo;
        }
        return normalInfo;
    }

    public int getColor(Reading reading){
        double value = this.converter.getCompareValue(reading);
        if(value < low){
            return lowColor;
        }
        if(value > high){
            return highColor;
        }
        return Color.BLUE;
    }

    public String getReadableString(Reading reading){
        return this.converter.getReadableString(reading);
    }
}
