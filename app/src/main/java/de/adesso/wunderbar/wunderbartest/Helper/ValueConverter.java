package de.adesso.wunderbar.wunderbartest.Helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.relayr.java.model.action.Reading;

/**
 * Created by sdrees on 18.05.2016.
 */
public abstract class ValueConverter {

    public abstract String getReadableString(Reading reading);
    public abstract double getCompareValue(Reading reading);
/*
    private static Map<SensorType, String> sSensorMap = new HashMap<>();
    private static Map<SensorType, Pair<Integer, Integer>> sSensorValues = new HashMap<>();
    public ValueConverter(){
        sSensorMap.put(SensorType.TEMPERATURE, context.getString(R.string.measurement_temperature));
        sSensorMap.put(SensorType.HUMIDITY, context.getString(R.string.measurement_humidity));
        sSensorMap.put(SensorType.NOISE_LEVEL, context.getString(R.string.measurement_noise));
        sSensorMap.put(SensorType.PROXIMITY, context.getString(R.string.measurement_proximity));
        sSensorMap.put(SensorType.LUMINOSITY, context.getString(R.string.measurement_light));

        sSensorValues.put(SensorType.TEMPERATURE, new Pair<>(-40, 140));
        sSensorValues.put(SensorType.HUMIDITY, new Pair<>(0, 100));
        sSensorValues.put(SensorType.NOISE_LEVEL, new Pair<>(0, 100));
        sSensorValues.put(SensorType.PROXIMITY, new Pair<>(0, 100));
        sSensorValues.put(SensorType.LUMINOSITY, new Pair<>(0, 100));
    }
*/
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

/*
    public int scaleToUiData(SensorType type, Double data) {
        switch (type) {
            case PROXIMITY:
                return (int) Math.round(data / 2047 * getMaxValue(type));
            case LUMINOSITY:
                return (int) Math.round(data / 4095 * getMaxValue(type));
            case NOISE_LEVEL:
                return (int) Math.round(data / 1023 * getMaxValue(type));
        }

        return data.intValue();
    }
    public float scaleToServerData(SensorType type, float data) {
        switch (type) {
            case PROXIMITY:
                return (data / getMaxValue(type) * 2047);
            case LUMINOSITY:
                return (data / getMaxValue(type) * 4095);
            case NOISE_LEVEL:
                return (data / getMaxValue(type) * 1023);
        }

        return data;
    }
    public static int getMaxValue(SensorType type) {
        return sSensorValues.get(type).second;
    }
*/
}
