package com.example.mealplanner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mealplanner.database.AppViewModel;
import com.example.mealplanner.database.Ingredient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddIngredientActivity extends AppCompatActivity {
    private AppViewModel appViewModel;

    private EditText editText_ingredient_name;
    private Spinner spinner_status;
    private Button button_add;

    private String ingredientStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        editText_ingredient_name = findViewById(R.id.editText_ingredient_name);
        spinner_status = findViewById(R.id.spinner_ingredient_status);
        button_add = findViewById(R.id.button_add_ingredient);

        // declare status spinner
        SpinnerStatusListener spinnerStatusListener = new SpinnerStatusListener();
        spinner_status.setOnItemSelectedListener(spinnerStatusListener);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_status.setAdapter(statusAdapter);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //TODO if ingredient already exist, toast
                String ingredientName = editText_ingredient_name.getText().toString();
                if (ingredientName.isEmpty()){
                    toastError(getString(R.string.no_ingredient_name));
                } else{
                    appViewModel.insertIngredient(new Ingredient(ingredientName, ingredientStatus));
                    finish();
                }
            }
        });


    }

    public void toastError(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    class SpinnerStatusListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            ingredientStatus = adapterView.getItemAtPosition(i).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
