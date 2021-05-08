package com.example.mealplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.SearchDishActivity;
import com.example.mealplanner.database.AppViewModel;
import com.example.mealplanner.recyclerview.DishAdapter;
import com.example.mealplanner.utilities.CustomizedView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DishesFragment extends Fragment {

    private AppViewModel appViewModel;
    public FloatingActionButton floatingActionButtonAddDish;
    public RecyclerView recyclerView_dish;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dishes, container, false);
        floatingActionButtonAddDish = root.findViewById(R.id.floatingActionButton_add_dish);
        recyclerView_dish = root.findViewById(R.id.recyclerView_dish);

        // create recycler adapter
        DishAdapter adapter = new DishAdapter(appViewModel.getAllDishes().getValue());
        recyclerView_dish.setAdapter(adapter);
        CustomizedView.setUpRecyclerView(root.getContext(), recyclerView_dish);

        appViewModel.getAllDishes().observe(getViewLifecycleOwner(), adapter::updateDishes);

        floatingActionButtonAddDish.setOnClickListener(view -> {
            Intent intent = new Intent(root.getContext(), SearchDishActivity.class);
            startActivity(intent);
        });
        return root;
    }

}