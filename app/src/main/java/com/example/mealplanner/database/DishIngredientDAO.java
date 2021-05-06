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
    @Query("SELECT dish_ingredient.ingredient, ingredient_table.status  FROM dish_ingredient LEFT JOIN ingredient_table ON dish_ingredient.ingredient LIKE ingredient_table.ingredient WHERE :name LIKE dish_name")
    LiveData<List<Ingredient>> getDishIngredients(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDishIngredient(DishIngredient... dishIngredients);


    @Delete
    void deleteDishIngredient(DishIngredient dishIngredient);

}
