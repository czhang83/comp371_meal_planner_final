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
    @Query("UPDATE dishes_table SET image_path = :image_path WHERE dish_name LIKE :dish_name")
    void updateImagePath(String dish_name, String image_path);

    @Query("SELECT * FROM dishes_table")
    LiveData<List<Dish>> getAllDishes();

    @Query("SELECT * FROM dishes_table WHERE :name Like dish_name")
    LiveData<Dish> getDishByName(String name);



    @Query("SELECT dish_name FROM dishes_table WHERE :dish_type LIKE dish_type AND breakfast = 1 ORDER BY RANDOM() LIMIT 1")
    LiveData<String> getRandomBreakfastByType(String dish_type);

    @Query("SELECT dish_name FROM dishes_table NATURAL JOIN dish_ingredient NATURAL JOIN ingredient_table " +
            "WHERE :dish_type LIKE dish_type AND breakfast = 1 AND (status LIKE 'Regular' OR status LIKE 'Low')" +
            "ORDER BY RANDOM() LIMIT 1")
    LiveData<String> getRandomBreakfastByTypeInPantry(String dish_type);

    @Query("SELECT dish_name FROM dishes_table WHERE :dish_type LIKE dish_type AND lunch = 1 ORDER BY RANDOM() LIMIT 1")
    LiveData<String> getRandomLunchByType(String dish_type);

    @Query("SELECT dish_name FROM dishes_table NATURAL JOIN dish_ingredient NATURAL JOIN ingredient_table " +
            "WHERE :dish_type LIKE dish_type AND lunch = 1 AND (status LIKE 'Regular' OR status LIKE 'Low')" +
            "ORDER BY RANDOM() LIMIT 1")
    LiveData<String> getRandomLunchByTypeInPantry(String dish_type);

    @Query("SELECT dish_name FROM dishes_table WHERE :dish_type LIKE dish_type AND dinner = 1 ORDER BY RANDOM() LIMIT 1")
    LiveData<String> getRandomDinnerByType(String dish_type);

    @Query("SELECT dish_name FROM dishes_table NATURAL JOIN dish_ingredient NATURAL JOIN ingredient_table " +
            "WHERE :dish_type LIKE dish_type AND dinner = 1 AND (status LIKE 'Regular' OR status LIKE 'Low')" +
            "ORDER BY RANDOM() LIMIT 1")
    LiveData<String> getRandomDinnerByTypeInPantry(String dish_type);


    @Query("SELECT dish_name FROM dishes_table WHERE :dish_type LIKE dish_type AND dessert = 1 ORDER BY RANDOM() LIMIT 1")
    LiveData<String> getRandomDessertByType(String dish_type);

    @Query("SELECT dish_name FROM dishes_table NATURAL JOIN dish_ingredient NATURAL JOIN ingredient_table " +
            "WHERE :dish_type LIKE dish_type AND dessert = 1 AND (status LIKE 'Regular' OR status LIKE 'Low')" +
            "ORDER BY RANDOM() LIMIT 1")
    LiveData<String> getRandomDessertByTypeInPantry(String dish_type);



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
