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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.AddDishActivity;
import com.example.mealplanner.AddIngredientActivity;
import com.example.mealplanner.R;
import com.example.mealplanner.database.AppViewModel;
import com.example.mealplanner.recyclerview.IngredientAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PantryFragment extends Fragment {

    private AppViewModel appViewModel;
    public FloatingActionButton floatingActionButtonAddIngredient;
    public RecyclerView recyclerView_ingredient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pantry, container, false);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        floatingActionButtonAddIngredient = root.findViewById(R.id.floatingActionButton_add_ingredient);
        recyclerView_ingredient = root.findViewById(R.id.recyclerView_ingredient);

        // create recycler adapter
        IngredientAdapter adapter = new IngredientAdapter(appViewModel.getAllIngredients().getValue());
        recyclerView_ingredient.setAdapter(adapter);
        recyclerView_ingredient.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView_ingredient.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                root.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView_ingredient.addItemDecoration(itemDecoration);

        appViewModel.getAllIngredients().observe(getViewLifecycleOwner(), ingredients -> {
            adapter.updateIngredients(ingredients);
        });

        floatingActionButtonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), AddIngredientActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}