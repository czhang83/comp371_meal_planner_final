package com.example.mealplanner.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DishIngredientDAO {
    @Query("SELECT ingredient FROM dish_ingredient WHERE :name LIKE dish_name")
    LiveData<List<String>> getDishIngredients(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDishIngredient(DishIngredient... dishIngredients);

    @Delete
    void deleteDishIngredient(DishIngredient dishIngredient);
}
