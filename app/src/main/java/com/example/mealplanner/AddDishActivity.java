package com.example.mealplanner;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddDishActivity extends AppCompatActivity {
    private AppViewModel appViewModel;

    private EditText editText_dish_name;
    private Spinner spinner_dish_type;
    private Button button_add;
    private CheckBox checkBox_breakfast;
    private CheckBox checkBox_lunch;
    private CheckBox checkBox_dinner;
    private CheckBox checkBox_dessert;

    private String dishType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        editText_dish_name = findViewById(R.id.editText_dish_name);
        spinner_dish_type = findViewById(R.id.spinner_dish_type);
        button_add = findViewById(R.id.button_add_dish);
        checkBox_breakfast = findViewById(R.id.checkBox_breakfast);
        checkBox_lunch = findViewById(R.id.checkBox_lunch);
        checkBox_dinner = findViewById(R.id.checkBox_dinner);
        checkBox_dessert = findViewById(R.id.checkBox_dessert);

        // declare status spinner
        SpinnerDishTypeListener spinnerDishTypeListener = new SpinnerDishTypeListener();
        spinner_dish_type.setOnItemSelectedListener(spinnerDishTypeListener);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> dishTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.dish_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        dishTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_dish_type.setAdapter(dishTypeAdapter);


        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //TODO if ingredient already exist, toast
                String dishName = editText_dish_name.getText().toString();
                boolean breakfast = checkBox_breakfast.isChecked();
                boolean lunch = checkBox_lunch.isChecked();
                boolean dinner = checkBox_dinner.isChecked();
                boolean dessert = checkBox_dessert.isChecked();
                if (dishName.isEmpty()){
                    toastError(getString(R.string.no_dish_name));
                } else if(!breakfast && !lunch && !dinner && !dessert){
                    toastError(getString(R.string.no_dish_type));
                }else{
                    appViewModel.insertDish(new Dish(dishName, dishType, breakfast, lunch, dinner, dessert));
                    finish();
                }
            }
        });

    }

    public void toastError(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    class SpinnerDishTypeListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            dishType = adapterView.getItemAtPosition(i).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


}
