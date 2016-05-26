package de.adesso.wunderbar.wunderbartest.Helper;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

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

    private LineChart chart;
    private LineDataSet lineDataSet;
    private LineData lineData;
    private int entryIndex = 0;


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
        double value = getCompareValue(reading);
        if(value < low){
            return lowInfo;
        }
        if(value > high){
            return highInfo;
        }
        return normalInfo;
    }

    public int getColor(Reading reading){
        double value = getCompareValue(reading);
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

    private float getCompareValue(Reading reading){
        return (float) this.converter.getCompareValue(reading);
    }

    public void setData(LineChart chart, Reading reading){
        setData(chart,this.converter.getPercentage(getCompareValue(reading)));
    }

    public void setData(LineChart chart, float readingValue){
        if((this.chart==null) || entryIndex > 50){
            entryIndex = 0;
            this.chart = chart;
            lineDataSet = createSet();
            lineData = new LineData();
            lineData.addDataSet(lineDataSet);
        }
        Entry entry = new Entry(readingValue,entryIndex);
        lineData.addXValue("" + entryIndex);
        lineDataSet.addEntry(entry);
        lineDataSet.notifyDataSetChanged();
        lineData.notifyDataChanged();
        this.chart.setData(lineData);
        this.chart.notifyDataSetChanged();
        this.chart.setVisibleXRangeMaximum(120);
        this.chart.moveViewToX(lineData.getXValCount() - 121);
        this.chart.invalidate();
        entryIndex++;
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "humidity");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

}
