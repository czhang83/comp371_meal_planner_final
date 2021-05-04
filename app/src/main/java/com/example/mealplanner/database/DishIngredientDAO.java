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
    // ofr ingredients not in ingredient_table, status is null
    @Query("SELECT ingredient, status FROM dish_ingredient LEFT OUTER JOIN ingredient_table WHERE :name LIKE dish_name")
    LiveData<List<Ingredient>> getDishIngredients(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDishIngredient(DishIngredient... dishIngredients);


    @Delete
    void deleteDishIngredient(DishIngredient dishIngredient);

}
