package com.example.mealplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealplanner.database.AppViewModel;
import com.example.mealplanner.recyclerview.DishGenerated;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// recyclerView adapter as nested class - need to access database through view model
public class GenerateResultActivity extends AppCompatActivity {
    private AppViewModel appViewModel;

    String mealType;
    Boolean pantry_only;
    ArrayList<String> dish_types;

    ArrayList<DishGenerated> generatedDishes = new ArrayList<>();
    GenerateResultActivity activity = this;
    RecyclerView recyclerView_dishes_generated;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_result);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        recyclerView_dishes_generated = findViewById(R.id.recyclerView_dishes_generated);
        // create recycler adapter
        DishGeneratedAdapter adapter = new DishGeneratedAdapter(generatedDishes);
        recyclerView_dishes_generated.setAdapter(adapter);
        recyclerView_dishes_generated.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_dishes_generated.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL);
        recyclerView_dishes_generated.addItemDecoration(itemDecoration);

        Intent intent = getIntent();
        mealType = intent.getStringExtra("meal_type");
        pantry_only = intent.getBooleanExtra("pantry_only", false);
        dish_types = intent.getStringArrayListExtra("dish_types");

        Log.d("dish", mealType);
        Log.d("dish", String.valueOf(pantry_only));
        Log.d("dish", dish_types.get(0));

        for (String dish_type: dish_types){
            LiveData<String> resultDish;
            if (pantry_only){
                resultDish = appViewModel.getRandomMealByTypeInPantry(dish_type, mealType);
            } else {
                resultDish = appViewModel.getRandomMealByType(dish_type, mealType);
            }
            resultDish.observe(this, new Observer<String>() { // only observe until a value is returned
                @Override
                public void onChanged(String s) {
                    if (s != null){
                        generatedDishes.add(new DishGenerated(s, dish_type));
                        adapter.updateDishes(generatedDishes);
                        Log.d("dish", s);
                        resultDish.removeObserver(this);
                    }
                }
            });
        }

    }


    class DishGeneratedAdapter extends RecyclerView.Adapter<ViewHolder>{
        private List<DishGenerated> dishes;

        //pass this list into the constructor of the adapter
        public DishGeneratedAdapter(List<DishGenerated> dishes){
            this.dishes = dishes;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // inflate the customer layout
            View dishView = inflater.inflate(R.layout.item_dish_generated, parent, false);
            return new ViewHolder(dishView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // populate data into the item through holder
            DishGenerated dish = dishes.get(position);

            holder.textView_dish_name.setText(dish.getDish_name());
            holder.textView_dish_type.setText(dish.getDish_type());


            holder.button_regenerate.setOnClickListener(view -> {
                Log.d("re", "regenerate clicked");
                LiveData<String> newDish;

                if (pantry_only){
                    newDish = appViewModel.getRandomMealByTypeInPantry(dish.getDish_type(), mealType);
                } else {
                    newDish = appViewModel.getRandomMealByType(dish.getDish_type(), mealType);
                }

                newDish.observe(activity, new Observer<String>() { // only observe until a value is returned
                    @Override
                    public void onChanged(String s) {
                        if (s != null){
                            Log.d("re", "on changed " + s);
                            dish.setDish_name(s);
                            notifyDataSetChanged();
                            newDish.removeObserver(this);
                        }
                    }
                });

            });



        }

        @Override
        public int getItemCount() {
            // could be null initially
            if (dishes == null) return 0;
            return dishes.size();
        }

        // update when text input change
        public void updateDishes(List<DishGenerated> dishes){
            this.dishes = dishes;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_dish_name;
        TextView textView_dish_type;
        Button button_regenerate;

        public ViewHolder(View itemView){
            super(itemView);
            textView_dish_name = itemView.findViewById(R.id.textView_dish_name);
            textView_dish_type = itemView.findViewById(R.id.textView_dish_type);
            button_regenerate = itemView.findViewById(R.id.button_regenerate);
        }

    }
}
