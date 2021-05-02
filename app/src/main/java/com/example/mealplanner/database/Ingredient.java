package com.example.mealplanner.database;

import org.jetbrains.annotations.NotNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_table")
public class Ingredient {
    @PrimaryKey @NotNull
    @ColumnInfo(name = "ingredient")
    public String ingredient;

    @ColumnInfo(name = "status")
    public String status;

    public Ingredient(String ingredient, String status){
        this.ingredient = ingredient;
        this.status = status;
    }

}