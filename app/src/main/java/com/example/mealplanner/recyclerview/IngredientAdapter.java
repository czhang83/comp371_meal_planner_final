package com.example.mealplanner.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.database.Ingredient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    private List<Ingredient> ingredients;

    //pass this list into the constructor of the adapter
    public IngredientAdapter(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // inflate the customer layout
        View ingredientView = inflater.inflate(R.layout.item_ingredient, parent, false);
        return new IngredientAdapter.ViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        // populate data into the item through holder
        Ingredient ingredient = ingredients.get(position);

        holder.textView_item_ingredient_name.setText(ingredient.ingredient);
        holder.textView_item_ingredient_status.setText(ingredient.status);

    }

    @Override
    public int getItemCount() {
        // could be null initially
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    public void updateIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_item_ingredient_name;
        TextView textView_item_ingredient_status;

        public ViewHolder(View itemView){
            super(itemView);
            textView_item_ingredient_name = itemView.findViewById(R.id.textView_item_ingredient_name);
            textView_item_ingredient_status = itemView.findViewById(R.id.textView_item_ingredient_status);
        }

    }
}
