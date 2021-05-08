package com.example.mealplanner.database;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AppViewModel extends AndroidViewModel {

    private final AppRepository repository;

    private final LiveData<List<Dish>> allDishes;
    private final LiveData<List<Ingredient>> allIngredients;

    public AppViewModel (Application application) {
        super(application);
        repository = AppApplication.getRepository();

        allDishes = repository.getAllDishes();
        allIngredients = repository.getAllIngredients();
    }

    public LiveData<List<Dish>> getAllDishes() { return allDishes; }

    public LiveData<List<Ingredient>> getAllIngredients(){
        return allIngredients;
    }

    public void insertDish(Dish dish) { repository.insertDish(dish); }

    public void deleteDish(String name) {
        repository.deleteDish(name);
    }

    public void updateImagePath(String dish_name, String image_path){
        repository.updateImagePath(dish_name, image_path);
    }

    public void insertIngredient(Ingredient ingredient){
        repository.insertIngredient(ingredient);
    }

    public void deleteIngredient(String ingredient) {
        repository.deleteIngredient(ingredient);
    }

    public void insertDishIngredient(DishIngredient dishIngredient){
        repository.insertDishIngredient(dishIngredient);
    }

    public void insertDishWithDishIngredient(Dish dish, ArrayList<DishIngredient> dishIngredients){
        repository.insertDishWithDishIngredient(dish, dishIngredients);
    }

    public LiveData<Dish> getDishByName(String dish_name){
        return repository.getDishByName(dish_name);
    }

    public LiveData<List<Ingredient>> getDishIngredients(String dish_name){
        return repository.getDishIngredients(dish_name);
    }

    public void updateStatusByIngredient(String ingredient, String status){
        repository.updateStatusByIngredient(ingredient, status);
    }

    public LiveData<Integer> containsIngredient(String ingredient){
        return repository.containsIngredient(ingredient);
    }

    public LiveData<String> getRandomMealByType(String dish_type, String meal_type){
        return repository.getRandomMealByType(dish_type, meal_type);
    }

    public LiveData<String> getRandomMealByTypeInPantry(String dish_type, String meal_type){
        return repository.getRandomMealByTypeInPantry(dish_type, meal_type);
    }
}