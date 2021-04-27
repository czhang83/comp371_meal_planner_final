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
        allDishes = dishDAO.getAllDishes();
    }

    LiveData<List<Dish>> getAllDishes() {
        return allDishes;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insertDish(Dish dish) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            dishDAO.insertAllDishes(dish);
        });
    }
}
