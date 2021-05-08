package com.example.mealplanner.recyclerview;

public class DishGenerated {
    private String dish_name;
    private String dish_type;

    public DishGenerated(String dish_name, String dish_type){
        this.dish_name = dish_name;
        this.dish_type = dish_type;
    }


    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_type() {
        return dish_type;
    }

    public void setDish_type(String dish_type) {
        this.dish_type = dish_type;
    }
}
