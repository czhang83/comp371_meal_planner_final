package com.example.mealplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mealplanner.AddDishActivity;
import com.example.mealplanner.MainActivity;
import com.example.mealplanner.R;
import com.example.mealplanner.database.AppViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DishesFragment extends Fragment {

    private AppViewModel appViewModel;
    public FloatingActionButton floatingActionButtonAddDish;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dishes, container, false);
        floatingActionButtonAddDish = root.findViewById(R.id.floatingActionButton_add_dish);

        appViewModel.getAllDishes().observe(getViewLifecycleOwner(), dishes -> {
            // adapter.submiteList(dishes);
        });

        floatingActionButtonAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), AddDishActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

}