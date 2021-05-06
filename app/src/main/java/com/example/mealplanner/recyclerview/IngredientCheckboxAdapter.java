package com.example.mealplanner.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.database.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientCheckboxAdapter extends RecyclerView.Adapter<IngredientCheckboxAdapter.ViewHolder>{
    private List<Ingredient> ingredients;
    private Map<Ingredient, Boolean> checked; // whether an ingredient is checked
    private boolean allChecked = false;

    //pass this list into the constructor of the adapter
    public IngredientCheckboxAdapter(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        this.checked = new HashMap<>();
    }

    //pass this list into the constructor of the adapter
    public IngredientCheckboxAdapter(List<Ingredient> ingredients, boolean allChecked){
        this.ingredients = ingredients;
        this.checked = new HashMap<>();
        this.allChecked = allChecked;
    }

    @NonNull
    @Override
    public IngredientCheckboxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // inflate the customer layout
        View ingredientCheckboxView = inflater.inflate(R.layout.item_ingredient_checkbox, parent, false);
        return new IngredientCheckboxAdapter.ViewHolder(ingredientCheckboxView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientCheckboxAdapter.ViewHolder holder, int position) {
        // populate data into the item through holder
        Ingredient ingredient = ingredients.get(position);

        holder.checkBox_ingredient.setText(ingredient.ingredient);
        holder.checkBox_ingredient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checked.put(ingredient, b);
            }
        });
        if (allChecked){
            holder.checkBox_ingredient.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        // could be null initially
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    public ArrayList<Ingredient> getChecked(){
        ArrayList<Ingredient> checkedIngredient = new ArrayList<>();
        for (Map.Entry<Ingredient, Boolean> entry : checked.entrySet()) {
            if (entry.getValue()){
                checkedIngredient.add(entry.getKey());
            }
        }
        return checkedIngredient;
    }

    public void updateIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox_ingredient;

        public ViewHolder(View itemView){
            super(itemView);
            checkBox_ingredient = itemView.findViewById(R.id.checkBox_ingredient);
        }

    }
}
