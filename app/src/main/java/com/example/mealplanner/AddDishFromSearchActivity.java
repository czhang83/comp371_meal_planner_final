package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mealplanner.database.AppViewModel;
import com.example.mealplanner.database.Dish;
import com.example.mealplanner.database.DishIngredient;
import com.example.mealplanner.database.Ingredient;
import com.example.mealplanner.recyclerview.IngredientCheckboxAdapter;
import com.example.mealplanner.utilities.CustomizedView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddDishFromSearchActivity extends AppCompatActivity {
    private AppViewModel appViewModel;

    private EditText editText_dish_name_from_search;
    private Spinner spinner_dish_type_from_search;
    private Button button_add_dish_from_search;
    private CheckBox checkBox_breakfast_from_search;
    private CheckBox checkBox_lunch_from_search;
    private CheckBox checkBox_dinner_from_search;
    private CheckBox checkBox_dessert_from_search;
    private RecyclerView recyclerView_ingredient_exist;
    private RecyclerView recyclerView_ingredient_not_exist;

    private String dishType;

    private String dish_name;
    private String dish_image_url;
    private ArrayList<String> ingredients;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish_from_search);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        Intent intent = getIntent();
        dish_name = intent.getStringExtra("dish_name");
        dish_image_url = intent.getStringExtra("dish_image_url");
        ingredients = intent.getStringArrayListExtra("dish_ingredients");

        editText_dish_name_from_search = findViewById(R.id.editText_dish_name_from_search);
        editText_dish_name_from_search.setText(dish_name);
        spinner_dish_type_from_search = findViewById(R.id.spinner_dish_type_from_search);
        button_add_dish_from_search = findViewById(R.id.button_add_dish_from_search);
        checkBox_breakfast_from_search = findViewById(R.id.checkBox_breakfast_from_search);
        checkBox_lunch_from_search = findViewById(R.id.checkBox_lunch_from_search);
        checkBox_dinner_from_search = findViewById(R.id.checkBox_dinner_from_search);
        checkBox_dessert_from_search = findViewById(R.id.checkBox_dessert_from_search);
        recyclerView_ingredient_exist = findViewById(R.id.recyclerView_ingredient_exist);
        recyclerView_ingredient_not_exist = findViewById(R.id.recyclerView_ingredient_not_exist);

        // declare status spinner
        SpinnerDishTypeListener spinnerDishTypeListener = new SpinnerDishTypeListener(
                getResources().getStringArray(R.array.dish_type_database));
        spinner_dish_type_from_search.setOnItemSelectedListener(spinnerDishTypeListener);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> dishTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.dish_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        dishTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_dish_type_from_search.setAdapter(dishTypeAdapter);


        // setup recycler views
        CustomizedView.setUpRecyclerView(this, recyclerView_ingredient_exist);
        CustomizedView.setUpRecyclerView(this, recyclerView_ingredient_not_exist);


        // separate ingredients into two arrays of ingredients that exist and not exist in the pantry
        ArrayList<Ingredient> ingredientExist = new ArrayList<>();
        ArrayList<Ingredient> ingredientNotExist = new ArrayList<>();
        IngredientCheckboxAdapter adapter_exist = new IngredientCheckboxAdapter(ingredientExist, true);
        recyclerView_ingredient_exist.setAdapter(adapter_exist);
        IngredientCheckboxAdapter adapter_not_exist = new IngredientCheckboxAdapter(ingredientNotExist, true);
        recyclerView_ingredient_not_exist.setAdapter(adapter_not_exist);
        for (String i : ingredients){
            appViewModel.containsIngredient(i).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) { // only get value once
                    if (integer != null){
                        boolean exist = (integer == 1);
                        if (exist){
                            ingredientExist.add(new Ingredient(i, null));
                            adapter_exist.updateIngredients(ingredientExist);
                        } else {
                            ingredientNotExist.add(new Ingredient(i, null));
                            adapter_not_exist.updateIngredients(ingredientNotExist);
                        }
                        appViewModel.containsIngredient(i).removeObserver(this);
                    }
                }
            });
        }



        button_add_dish_from_search.setOnClickListener(view -> { //TODO if ingredient already exist, toast
            String dishName = editText_dish_name_from_search.getText().toString();
            ArrayList<DishIngredient> dishIngredients = new ArrayList<>();
            for(Ingredient ingredient : adapter_exist.getChecked()){
                dishIngredients.add(new DishIngredient(dishName, ingredient.ingredient));
            }
            for(Ingredient ingredient : adapter_not_exist.getChecked()){
                dishIngredients.add(new DishIngredient(dishName, ingredient.ingredient));
            }

            boolean breakfast = checkBox_breakfast_from_search.isChecked();
            boolean lunch = checkBox_lunch_from_search.isChecked();
            boolean dinner = checkBox_dinner_from_search.isChecked();
            boolean dessert = checkBox_dessert_from_search.isChecked();
            if (dishName.isEmpty()){
                toastError(getString(R.string.no_dish_name));
            } else if(!breakfast && !lunch && !dinner && !dessert){
                toastError(getString(R.string.no_dish_type));
            }else{
                appViewModel.insertDishWithDishIngredient(new Dish(dishName, dishType, breakfast, lunch, dinner, dessert, dish_image_url),
                        dishIngredients);
                finish();
            }
        });

    }

    public void toastError(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    class SpinnerDishTypeListener implements AdapterView.OnItemSelectedListener {

        String[] itemValue;
        // the English value that will get stored into the database when display is not in English
        public SpinnerDishTypeListener(String[] itemValue){
            this.itemValue = itemValue;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            dishType = itemValue[i];
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
