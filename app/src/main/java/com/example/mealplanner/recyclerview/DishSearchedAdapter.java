package com.example.mealplanner.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealplanner.AddDishFromSearchActivity;
import com.example.mealplanner.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DishSearchedAdapter extends RecyclerView.Adapter<DishSearchedAdapter.ViewHolder>{
    private List<JSONObject> dishes;
    int ingredient_count = 20; // api has at most 20 ingredients for a dish

    //pass this list into the constructor of the adapter
    public DishSearchedAdapter(){
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // inflate the customer layout
        View dishView = inflater.inflate(R.layout.item_dish, parent, false);
        return new ViewHolder(dishView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = "";
        ArrayList<String> ingredients = new ArrayList<>();
        String imageUrl = "";
        // populate data into the item through holder
        JSONObject dish = dishes.get(position);

        try {
            name = dish.getString("strMeal");
            imageUrl = dish.getString("strMealThumb");
            for (int i = 1; i <= ingredient_count; i++){
                String ingredient = dish.getString("strIngredient" + i);
                if (!ingredient.isEmpty() && ingredient != null){
                    ingredients.add(ingredient);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.textView_item_dish_name.setText(name);
        Picasso.get().load(imageUrl).into(holder.imageView_dish);
        StringBuilder ingredientsBuilder = new StringBuilder();
        for(String i : ingredients){
            ingredientsBuilder.append(i);
            ingredientsBuilder.append(", ");
        }
        String ingString = ingredientsBuilder.toString();
        if (!ingString.isEmpty()) ingString = ingString.substring(0, ingString.length() - 2);
        holder.textView_item_ingredients.setText(ingString);

        // open to a add dish page with info loaded
        String finalName = name;
        String finalImageUrl = imageUrl;
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddDishFromSearchActivity.class);
            intent.putExtra("dish_name", finalName);
            intent.putExtra("dish_image_url", finalImageUrl);
            intent.putStringArrayListExtra("dish_ingredients", ingredients);
            view.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        // could be null initially
        if (dishes == null) return 0;
        return dishes.size();
    }


    // update when text input change
    public void updateDishes(List<JSONObject> dishes){
        this.dishes = dishes;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_item_dish_name;
        TextView textView_item_ingredients;
        ImageView imageView_dish;
        View itemView;

        // ignore dish_type TextView
        // use meal_type TextView to hold ingredients
        public ViewHolder(View itemView){
            super(itemView);
            textView_item_dish_name = itemView.findViewById(R.id.textView_item_dish_name);
            textView_item_ingredients = itemView.findViewById(R.id.textView_item_meal_type);
            imageView_dish = itemView.findViewById(R.id.imageView_dish);
            this.itemView = itemView;
        }

    }

}
