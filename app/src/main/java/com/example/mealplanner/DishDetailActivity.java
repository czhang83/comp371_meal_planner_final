package com.example.mealplanner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealplanner.database.AppViewModel;
import com.example.mealplanner.database.Dish;
import com.example.mealplanner.database.Ingredient;
import com.example.mealplanner.recyclerview.IngredientAdapter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DishDetailActivity extends AppCompatActivity {
    private AppViewModel appViewModel;
    TextView textView_detail_dish_name;
    TextView textView_detail_dish_type;
    TextView textView_detail_meal_type;
    ImageView imageView_detail_dish;
    Button button_delete;
    Button button_edit;


    private RecyclerView recyclerView_detail_ingredient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        textView_detail_dish_name = findViewById(R.id.textView_detail_dish_name);
        textView_detail_dish_type = findViewById(R.id.textView_detail_dish_type);
        textView_detail_meal_type = findViewById(R.id.textView_detail_meal_type);
        imageView_detail_dish = findViewById(R.id.imageView_detail_dish);
        button_delete = findViewById(R.id.button_delete_dish);
        button_edit = findViewById(R.id.button_edit_dish);
        recyclerView_detail_ingredient = findViewById(R.id.recyclerView_detail_ingredient);

        String dish_name = getIntent().getStringExtra("dish_name");
        LiveData<Dish> dish = appViewModel.getDishByName(dish_name);
        dish.observe(this, d ->{
            if (d != null){
                textView_detail_dish_name.setText(d.getDish_name());
                textView_detail_meal_type.setText(getString(R.string.meal) + "\n" + d.getMealTypes());
                textView_detail_dish_type.setText(getString(R.string.type) + " " + d.getDish_type());
                // TODO show image
            }
        });

        LiveData<List<Ingredient>> ingredients = appViewModel.getDishIngredients(dish_name);
        // create recycler adapter
        IngredientAdapter adapter = new IngredientAdapter(ingredients.getValue());
        recyclerView_detail_ingredient.setAdapter(adapter);
        recyclerView_detail_ingredient.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_detail_ingredient.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL);
        recyclerView_detail_ingredient.addItemDecoration(itemDecoration);

        ingredients.observe(this, adapter::updateIngredients);

        button_delete.setOnClickListener(view -> {
            appViewModel.deleteDish(dish_name);
            finish();
        });


    }
}
