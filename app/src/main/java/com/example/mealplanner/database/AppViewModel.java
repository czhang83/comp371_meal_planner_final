package com.example.mealplanner.database;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AppViewModel extends AndroidViewModel {

    private AppRepository repository;

    private final LiveData<List<Dish>> allDishes;

    public AppViewModel (Application application) {
        super(application);
        repository = new AppRepository(application);
        allDishes = repository.getAllDishes();
    }

    public LiveData<List<Dish>> getAllDishes() { return allDishes; }

    public void insertDish(Dish dish) { repository.insertDish(dish); }
}