package com.example.mealplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.AddIngredientActivity;
import com.example.mealplanner.R;
import com.example.mealplanner.database.AppViewModel;
import com.example.mealplanner.database.Ingredient;
import com.example.mealplanner.recyclerview.IngredientAdapter;
import com.example.mealplanner.recyclerview.IngredientEditAdapter;
import com.example.mealplanner.utilities.CustomizedView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
        CustomizedView.setUpRecyclerView(root.getContext(), recyclerView_ingredient);

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

        appViewModel.getAllIngredients().observe(getViewLifecycleOwner(), new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> ingredients) { // only observe until getting the values
                if (ingredients != null){
                    // adapter would alter elements in the list, to avoid affecting the LiveData
                    List<Ingredient> tempList = new ArrayList<>();
                    for(Ingredient i : ingredients){
                        tempList.add(i.copy());
                    }
                    adapter.updateIngredients(tempList);
                    appViewModel.getAllIngredients().removeObserver(this);
                }
            }
        });
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