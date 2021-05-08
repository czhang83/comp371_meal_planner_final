package com.example.mealplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.mealplanner.database.Ingredient;
import com.example.mealplanner.recyclerview.IngredientAdapter;
import com.example.mealplanner.recyclerview.IngredientEditAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;

public class PantryFragment extends Fragment {

    private AppViewModel appViewModel;
    public FloatingActionButton floatingActionButtonAddIngredient;
    public RecyclerView recyclerView_ingredient;
    public Button button_edit_ingredient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pantry, container, false);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        floatingActionButtonAddIngredient = root.findViewById(R.id.floatingActionButton_add_ingredient);
        recyclerView_ingredient = root.findViewById(R.id.recyclerView_ingredient);
        button_edit_ingredient = root.findViewById(R.id.button_edit_ingredient);

        // create recycler adapter
        IngredientAdapter adapter = new IngredientAdapter(appViewModel.getAllIngredients().getValue());
        recyclerView_ingredient.setAdapter(adapter);
        recyclerView_ingredient.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView_ingredient.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                root.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView_ingredient.addItemDecoration(itemDecoration);

        appViewModel.getAllIngredients().observe(getViewLifecycleOwner(), adapter::updateIngredients);

        floatingActionButtonAddIngredient.setOnClickListener(view -> {
            Intent intent = new Intent(root.getContext(), AddIngredientActivity.class);
            startActivity(intent);
        });

        button_edit_ingredient.setOnClickListener(this::onClickEdit);

        return root;
    }

    public void onClickEdit(View view){
        // create recycler adapter
        IngredientEditAdapter adapter = new IngredientEditAdapter(appViewModel.getAllIngredients().getValue());
        recyclerView_ingredient.setAdapter(adapter);

        appViewModel.getAllIngredients().observe(getViewLifecycleOwner(), adapter::updateIngredients);
        button_edit_ingredient.setText(R.string.button_save);
        button_edit_ingredient.setOnClickListener(this::onClickSave);
    }

    public void onClickSave(View view){
        ((IngredientEditAdapter)recyclerView_ingredient.getAdapter()).updateDatabase();

        IngredientAdapter adapter = new IngredientAdapter(appViewModel.getAllIngredients().getValue());
        recyclerView_ingredient.setAdapter(adapter);

        appViewModel.getAllIngredients().observe(getViewLifecycleOwner(), adapter::updateIngredients);

        button_edit_ingredient.setText(R.string.button_edit);
        button_edit_ingredient.setOnClickListener(this::onClickEdit);
    }
}