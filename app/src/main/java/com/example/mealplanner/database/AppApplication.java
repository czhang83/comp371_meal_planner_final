package com.example.mealplanner.database;

import android.app.Application;
import android.content.Context;

import com.example.mealplanner.utilities.LightSensor;

public class AppApplication extends Application {

    private static Context context;
    private static AppDatabase database;
    private static AppRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null) {
            context = getApplicationContext();
        }
        if (database == null) {
            database = AppDatabase.getDatabase(this);
        }
        if (repository == null) {
            repository = new AppRepository();
        }
        // determine to use dark or light mode depending on light sensor data
        LightSensor lightSensor = new LightSensor(AppApplication.context);
    }


    public static Context getContext(){
        return AppApplication.context;
    }

    public static AppDatabase getDatabase(){
        return database;
    }

    public static AppRepository getRepository(){
        return repository;
    }
}
