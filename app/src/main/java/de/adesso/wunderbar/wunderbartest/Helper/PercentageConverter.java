package de.adesso.wunderbar.wunderbartest.Helper;

import io.relayr.java.model.action.Reading;

/**
 * Created by sdrees on 18.05.2016.
 */
public class PercentageConverter extends ValueConverter {

    private final String unit;

    public PercentageConverter(){
        this.unit = "%";
    }

    @Override
    public String getReadableString(Reading reading) {
        return round((Double) reading.value, 2) + this.unit;
    }

    @Override
    public double getCompareValue(Reading reading) {
        return round((Double) reading.value, 2);
    }
}
