package com.example.mealplanner.utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.appcompat.app.AppCompatDelegate;

public class LightSensor {
    // each activity have one sensor instance
    // switch between dark and light mode depending on light sensor data
    private final Context context;
    private final SensorManager sensorManager;
    private final Sensor lightSensor;

    public LightSensor(Context context){
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor != null){
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    // switch between dark and light mode
                    int sensorType = sensorEvent.sensor.getType();
                    if (sensorType == Sensor.TYPE_LIGHT){
                        float currentValue = sensorEvent.values[0];
                        if (currentValue <= lightSensor.getMaximumRange()){
                            // dark mode
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        } else {
                            // light mode
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}