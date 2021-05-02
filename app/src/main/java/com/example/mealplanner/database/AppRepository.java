package com.example.mealplanner.database;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

public class AppRepository {

    private DishDAO dishDAO;
    private IngredientDAO ingredientDAO;
    private DishIngredientDAO dishIngredientDAO;

    private LiveData<List<Dish>> allDishes;
    private LiveData<List<Ingredient>> allIngredients;

    AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
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
        AppDatabase.databaseWriteExecutor.execute(() -> {
            dishDAO.insertAllDishes(dish);
        });
    }

    void insertIngredient(Ingredient ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            ingredientDAO.insertIngredient(ingredient);
        });
    }
}
