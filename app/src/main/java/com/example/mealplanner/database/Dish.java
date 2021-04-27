package com.example.mealplanner.database;

import org.jetbrains.annotations.NotNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dishes_table")
public class Dish {
    @PrimaryKey @NotNull
    @ColumnInfo(name = "dish_name")
    public String dish_name;

    @ColumnInfo(name = "dish_type")
    public String type;

    // boolean 1 - true, 0 - false
    @ColumnInfo(name = "breakfast")
    public int breakfast;
    @ColumnInfo(name = "lunch")
    public int lunch;
    @ColumnInfo(name = "dinner")
    public int dinner;
    @ColumnInfo(name = "dessert")
    public int dessert;

    public Dish(String dish_name, String type, int breakfast, int lunch, int dinner, int dessert){
        this.dish_name = dish_name;
        this.type = type;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.dessert = dessert;
    }

    // list out type + \n
    public String getTypes(){
        String meal_types = "";
        if (this.breakfast == 1) meal_types = meal_types.concat("Breakfast\n");
        if (this.lunch == 1) meal_types = meal_types.concat("Lunch\n");
        if (this.dinner == 1) meal_types = meal_types.concat("Dinner\n");
        if (this.dessert == 1) meal_types = meal_types.concat("Dessert\n");
        return meal_types;
    }
}
