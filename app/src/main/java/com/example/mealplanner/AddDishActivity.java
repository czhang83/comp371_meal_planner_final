package com.example.mealplanner;

import android.os.Bundle;

import com.example.mealplanner.database.AppViewModel;
import com.example.mealplanner.database.Dish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddDishActivity extends AppCompatActivity {
    private AppViewModel appViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        //appViewModel.insertDish(new Dish("test", "breakfast", 1, 0, 0, 0));


    }


}
