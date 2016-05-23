package de.adesso.wunderbar.wunderbartest;

import android.graphics.Color;

import java.util.ArrayList;

import de.adesso.wunderbar.wunderbartest.Helper.AccelerationConverter;
import de.adesso.wunderbar.wunderbartest.Helper.AngularSpeedConverter;
import de.adesso.wunderbar.wunderbartest.Helper.LightColorConverter;
import de.adesso.wunderbar.wunderbartest.Helper.PercentageConverter;
import de.adesso.wunderbar.wunderbartest.Helper.SensorValueInfo;
import de.adesso.wunderbar.wunderbartest.Helper.TemperatureConverter;

/**
 * Created by sdrees on 17.05.2016.
 */
public class SensorValueCollection {

    private static SensorValueInfo humidity = new SensorValueInfo("humidity",25,Color.RED,60,Color.RED,"Humidity too low!","Humidity too high!","Perfect humidity", new PercentageConverter(100));
    private static SensorValueInfo temperature = new SensorValueInfo("temperature",18,Color.RED,25,Color.RED,"Temperature too low!","Temperature too high!","Perfect temperature",new TemperatureConverter("°C"));
    private static SensorValueInfo angularSpeed = new SensorValueInfo("angularSpeed",10,Color.BLACK,15,Color.RED,"No Movement","Too much movement!","Almost no Movement", new AngularSpeedConverter());
    private static SensorValueInfo acceleration = new SensorValueInfo("acceleration",0,Color.BLACK,1,Color.RED,"No Movement","Too much movement!","Almost no Movement",new AccelerationConverter());
    private static SensorValueInfo luminosity = new SensorValueInfo("luminosity",200,Color.RED,500,Color.RED,"Too dark!","Too bright!","Dark enough",new PercentageConverter(4096));
    private static SensorValueInfo color = new SensorValueInfo("color",0,Color.BLUE,0,Color.BLUE,"Color irrelevant","Color irrelevant","Color irrelevant",new LightColorConverter());
    private static SensorValueInfo proximity = new SensorValueInfo("proximity",100,Color.BLACK,1000,Color.RED,"Nobody is touching","Somebody is touching!","Somebody is near!",new PercentageConverter(2047));
    private static SensorValueInfo noiseLevel = new SensorValueInfo("noiseLevel",135,Color.BLACK,145,Color.RED,"No Sound","Loud noise!","A little noisy",new PercentageConverter(1024));

    public static SensorValueInfo getByName(String name){
        for(SensorValueInfo svi : all){
            if(svi.getSensorName().equals(name)){
                return svi;
            }
        }
        return null;
    }

    private static ArrayList<SensorValueInfo> all = new ArrayList<SensorValueInfo>();
    static{
        all.add(humidity);
        all.add(temperature);
        all.add(angularSpeed);
        all.add(acceleration);
        all.add(luminosity);
        all.add(color);
        all.add(proximity);
        all.add(noiseLevel);
    }

}
