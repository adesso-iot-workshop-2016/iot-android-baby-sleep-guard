package de.adesso.wunderbar.wunderbartest.Helper;

import io.relayr.java.model.action.Reading;

/**
 * Created by sdrees on 18.05.2016.
 */
public class TemperatureConverter extends ValueConverter {

    private final String unit;

    public TemperatureConverter(String unit){
        this.unit = unit;
        this.maxValue = 100;
    }

    @Override
    public String getReadableString(Reading reading) {
        return round((Double) reading.value, 2) + unit;
    }

    @Override
    public double getCompareValue(Reading reading) {
        return round((Double) reading.value, 2);
    }

    @Override
    public int getMaxValue() {
        return maxValue;
    }

}
