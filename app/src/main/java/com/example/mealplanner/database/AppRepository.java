package com.example.mealplanner.database;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class AppRepository {

    private final DishDAO dishDAO;
    private final IngredientDAO ingredientDAO;
    private final DishIngredientDAO dishIngredientDAO;

    private final LiveData<List<Dish>> allDishes;
    private final LiveData<List<Ingredient>> allIngredients;

    AppRepository() {
        AppDatabase db = AppApplication.getDatabase();
        dishDAO = db.dishDAO();
        ingredientDAO = db.ingredientDAO();
        dishIngredientDAO = db.dishIngredientDAO();

        allDishes = dishDAO.getAllDishes();
        allIngredients = ingredientDAO.getAllIngredient();
    }

    LiveData<List<Dish>> getAllDishes() {
        return allDishes;
    }
    LiveData<List<Ingredient>> getAllIngredients(){
        return allIngredients;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insertDish(Dish dish) {
        AppDatabase.databaseWriteExecutor.execute(() -> dishDAO.insertAllDishes(dish));
    }

    void deleteDish(String name){
        AppDatabase.databaseWriteExecutor.execute(() -> dishDAO.deleteDish(name));
    }

    void updateImagePath(String dish_name, String image_path){
        AppDatabase.databaseWriteExecutor.execute(() -> dishDAO.updateImagePath(dish_name, image_path));
    }

    void insertIngredient(Ingredient ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> ingredientDAO.insertIngredient(ingredient));
    }

    void deleteIngredient(String ingredient){
        AppDatabase.databaseWriteExecutor.execute(() -> ingredientDAO.deleteIngredient(ingredient));
    }

    void insertDishIngredient(DishIngredient dishIngredient){
        AppDatabase.databaseWriteExecutor.execute(() -> dishIngredientDAO.insertDishIngredient(dishIngredient));
    }

    // when first creating a dish with dishIngredient
    // insert dishIngredient after dish is inserted - foreign key constraint
    void insertDishWithDishIngredient(Dish dish, ArrayList<DishIngredient> dishIngredients){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            dishDAO.insertAllDishes(dish);
            for (DishIngredient dishIngredient : dishIngredients){
                dishIngredientDAO.insertDishIngredient(dishIngredient);
            }
        });
    }

    LiveData<Dish> getDishByName (String dish_name){
        return dishDAO.getDishByName(dish_name);
    }

    LiveData<List<Ingredient>> getDishIngredients(String dish_name){
        return dishIngredientDAO.getDishIngredients(dish_name);
    }

    void updateStatusByIngredient(String ingredient, String status){
        AppDatabase.databaseWriteExecutor.execute(() -> ingredientDAO.updateStatusByIngredient(ingredient, status));
    }

    LiveData<Integer> containsIngredient(String ingredient){
        return ingredientDAO.containsIngredient(ingredient);
    }

    LiveData<String> getRandomMealByType(String dish_type, String meal_type){
        switch (meal_type) {
            case "Breakfast":
                return dishDAO.getRandomBreakfastByType(dish_type);
            case "Lunch":
                return dishDAO.getRandomLunchByType(dish_type);
            case "Dinner":
                return dishDAO.getRandomDinnerByType(dish_type);
            case "Dessert":
                return dishDAO.getRandomDessertByType(dish_type);
            default:
                return null;
        }
    }

    LiveData<String> getRandomMealByTypeInPantry(String dish_type, String meal_type){
        switch (meal_type) {
            case "Breakfast":
                return dishDAO.getRandomBreakfastByTypeInPantry(dish_type);
            case "Lunch":
                return dishDAO.getRandomLunchByTypeInPantry(dish_type);
            case "Dinner":
                return dishDAO.getRandomDinnerByTypeInPantry(dish_type);
            case "Dessert":
                return dishDAO.getRandomDessertByTypeInPantry(dish_type);
            default:
                return null;
        }
    }
}
