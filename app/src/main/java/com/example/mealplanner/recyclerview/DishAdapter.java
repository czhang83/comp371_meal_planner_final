package com.example.mealplanner.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealplanner.DishDetailActivity;
import com.example.mealplanner.R;
import com.example.mealplanner.database.Dish;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder>{
    private List<Dish> dishes;

    //pass this list into the constructor of the adapter
    public DishAdapter(List<Dish> dishes){
        this.dishes = dishes;
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
        // populate data into the item through holder
        Dish dish = dishes.get(position);

        holder.textView_item_dish_name.setText(dish.getDish_name());
        holder.textView_item_dish_type.setText(dish.getDish_type());
        holder.textView_item_meal_type.setText(dish.getMealTypes());


        // TODO load image
        //Picasso.get().load(dish.getImageUrl()).into(holder.imageView_beer);

        // open to a detail page
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DishDetailActivity.class);
            intent.putExtra("dish_name", dish.getDish_name());
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
    public void updateDishes(List<Dish> dishes){
        this.dishes = dishes;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_item_dish_name;
        TextView textView_item_dish_type;
        TextView textView_item_meal_type;
        ImageView imageView_dish;
        View itemView;

        public ViewHolder(View itemView){
            super(itemView);
            textView_item_dish_name = itemView.findViewById(R.id.textView_item_dish_name);
            textView_item_dish_type = itemView.findViewById(R.id.textView_item_dish_type);
            textView_item_meal_type = itemView.findViewById(R.id.textView_item_meal_type);
            imageView_dish = itemView.findViewById(R.id.imageView_dish);
            this.itemView = itemView;
        }

    }

}
