package com.example.mealplanner.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface IngredientDAO {
    @Query("SELECT * FROM ingredient_table")
    LiveData<List<Ingredient>> getAllIngredient();

    @Query("SELECT status FROM ingredient_table WHERE :name LIKE ingredient")
    String getIngredientStatus(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertIngredient(Ingredient... ingredients);

    @Delete
    void deleteIngredient(Ingredient ingredient);
}
