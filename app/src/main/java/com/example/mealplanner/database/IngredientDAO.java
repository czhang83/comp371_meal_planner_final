package com.example.mealplanner.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
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

    @Query("DELETE FROM ingredient_table WHERE ingredient LIKE :ingredient")
    void deleteIngredient(String ingredient);

    @Query("UPDATE ingredient_table SET status=:status WHERE ingredient LIKE :ingredient")
    void updateStatusByIngredient(String ingredient, String status);

    @Query("SELECT EXISTS(SELECT ingredient FROM ingredient_table WHERE ingredient LIKE :ingredient)")
    LiveData<Integer> containsIngredient(String ingredient);

    // check no duplicate newName beforehand
    @Query("UPDATE ingredient_table SET ingredient=:newName WHERE ingredient LIKE :ingredient")
    void updateIngredientName(String ingredient, String newName);
}
