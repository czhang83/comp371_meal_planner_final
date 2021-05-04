package com.example.mealplanner.database;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AppViewModel extends AndroidViewModel {

    private AppRepository repository;

    private final LiveData<List<Dish>> allDishes;
    private final LiveData<List<Ingredient>> allIngredients;

    public AppViewModel (Application application) {
        super(application);
        repository = new AppRepository(application);

        allDishes = repository.getAllDishes();
        allIngredients = repository.getAllIngredients();
    }

    public LiveData<List<Dish>> getAllDishes() { return allDishes; }

    public LiveData<List<Ingredient>> getAllIngredients(){
        return allIngredients;
    }

    public void insertDish(Dish dish) { repository.insertDish(dish); }

    public void insertIngredient(Ingredient ingredient){
        repository.insertIngredient(ingredient);
    }

    public void insertDishIngredient(DishIngredient dishIngredient){
        repository.insertDishIngredient(dishIngredient);
    }

    public void insertDishWithDishIngredient(Dish dish, ArrayList<DishIngredient> dishIngredients){
        repository.insertDishWithDishIngredient(dish, dishIngredients);
    }
}