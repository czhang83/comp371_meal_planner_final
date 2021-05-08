package com.example.mealplanner.utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

// only use once when app starts, to decide the mode for the app
// behaviours depends on the light sensor
// recent phones will trigger onSensorChange when listener is registered -> assumed behavior
public class LightSensor {
    // register sensor
    // switch between dark and light mode depending on light sensor data
    // then remove sensor
    private final Context context;
    private final SensorManager sensorManager;
    private final Sensor lightSensor;

    public LightSensor(Context context){
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                // switch between dark and light mode
                int sensorType = sensorEvent.sensor.getType();
                if (sensorType == Sensor.TYPE_LIGHT) {
                    float currentValue = sensorEvent.values[0];
                    if (currentValue <= lightSensor.getMaximumRange()) {
                        // dark mode
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        sensorManager.unregisterListener(this);
                    } else {
                        // light mode
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        sensorManager.unregisterListener(this);
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        if (lightSensor != null){
            sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }
}