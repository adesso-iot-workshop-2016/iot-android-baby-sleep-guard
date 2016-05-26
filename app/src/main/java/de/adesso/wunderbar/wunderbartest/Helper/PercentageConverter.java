package de.adesso.wunderbar.wunderbartest.Helper;

import io.relayr.java.model.action.Reading;

/**
 * Created by sdrees on 18.05.2016.
 */
public class PercentageConverter extends ValueConverter {

    private final String unit = "%";
    private final int maxValue;

    public PercentageConverter(int maxValue){
        this.maxValue = maxValue;
    }

    @Override
    public String getReadableString(Reading reading) {
        return  getPercentage((double)reading.value) + this.unit;
        //return round((Double) reading.value, 2) + this.unit;
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
