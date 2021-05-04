package com.example.mealplanner.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DishDAO {
    @Query("SELECT * FROM dishes_table")
    LiveData<List<Dish>> getAllDishes();

    @Query("SELECT * FROM dishes_table WHERE :name Like dish_name")
    LiveData<Dish> getDishByName(String name);

    @Query("SELECT * FROM dishes_table WHERE :name LIKE dish_type")
    LiveData<List<Dish>> getDishesByType(String name);

    @Query("SELECT * FROM dishes_table WHERE breakfast = 1")
    LiveData<List<Dish>> getAllBreakfast();

    @Query("SELECT * FROM dishes_table WHERE lunch = 1")
    LiveData<List<Dish>> getAllLunch();

    @Query("SELECT * FROM dishes_table WHERE dinner = 1")
    LiveData<List<Dish>> getAllDinner();

    @Query("SELECT * FROM dishes_table WHERE dessert = 1")
    LiveData<List<Dish>> getAllDessert();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllDishes(Dish... dishes);

    @Query("DELETE FROM dishes_table WHERE dish_name LIKE :name")
    void deleteDish(String name);



}
