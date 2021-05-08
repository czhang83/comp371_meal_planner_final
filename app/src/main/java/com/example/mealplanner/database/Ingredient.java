package com.example.mealplanner.database;

import com.example.mealplanner.R;

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

    public Ingredient(@NotNull String ingredient, String status){
        this.ingredient = ingredient;
        this.status = status;
    }


    @NotNull
    public String getIngredient(){
        return this.ingredient;
    }

    public String getStatus(){
        if (this.status == null) return AppApplication.getContext().getString(R.string.nonexistent);
        switch (this.status){
            case "Regular":
                return AppApplication.getContext().getString(R.string.regular);
            case "Low":
                return AppApplication.getContext().getString(R.string.low);
            case "None":
                return AppApplication.getContext().getString(R.string.none);
            default:
                return AppApplication.getContext().getString(R.string.nonexistent);
        }
    }

    public Ingredient copy(){
        return new Ingredient(this.ingredient, this.status);
    }

}