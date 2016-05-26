package de.adesso.wunderbar.wunderbartest.Helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.relayr.java.model.action.Reading;

/**
 * Created by sdrees on 18.05.2016.
 */
public abstract class ValueConverter {

    protected int maxValue = 0;

    public abstract String getReadableString(Reading reading);
    public abstract double getCompareValue(Reading reading);

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public int getPercentage(double data){
        return ((int) Math.round((data / getMaxValue()) * 100));
    }

    public abstract int getMaxValue();

}
