package com.example.mealplanner.database;

import org.jetbrains.annotations.NotNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "dish_ingredient",
        primaryKeys = {"dish_name", "ingredient"},
        foreignKeys = {@ForeignKey(entity = Dish.class,
        parentColumns = "dish_name",
        childColumns = "dish_name",
        onDelete = ForeignKey.CASCADE)
})
public class DishIngredient {
    @NotNull
    @ColumnInfo(name = "dish_name")
    public String dish_name;

    // ingredient table don't need to contain all ingredients used in the dishes
    @NotNull
    @ColumnInfo(name = "ingredient")
    public String ingredient;

    public DishIngredient(@NotNull String dish_name, @NotNull String ingredient){
        this.dish_name = dish_name;
        this.ingredient = ingredient;
    }

    @NotNull
    public String getDish_name(){
        return this.dish_name;
    }

    @NotNull
    public String getIngredient() {
        return ingredient;
    }
}