package com.example.mealplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.mealplanner.GenerateResultActivity;
import com.example.mealplanner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class HomeFragment extends Fragment {

    public View root;

    public TextView textView_date;
    public Button button_generate;
    public SwitchCompat switch_pantry_only;

    public Spinner spinnerMealType;

    public SpinnerMealTypeListener spinnerMealTypeListener;
    public SpinnerCountingListener spinnerCountListener;

    public String mealType;
    public String vegetableCount;
    public String meatCount;
    public String seafoodCount;
    public String stapleCount;
    public String drinkCount;
    public String dessertCount;
    public String fruitCount;

    public boolean pantry_only;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_home, container, false);
        textView_date = root.findViewById(R.id.textView_weekday);
        button_generate = root.findViewById(R.id.button_generate);
        switch_pantry_only = root.findViewById(R.id.switch_pantry_only);

        switch_pantry_only.setOnCheckedChangeListener((compoundButton, b) -> pantry_only = b);


        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        textView_date.setText(dayOfTheWeek);

        // meal type spinner
        spinnerMealType = root.findViewById(R.id.spinner_meal_type);
        spinnerMealTypeListener = new SpinnerMealTypeListener(getResources().getStringArray(R.array.meal_type_database));
        spinnerMealType.setOnItemSelectedListener(spinnerMealTypeListener);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> mealTypeAdapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.meal_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        mealTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerMealType.setAdapter(mealTypeAdapter);

        spinnerCountListener = new SpinnerCountingListener();
        declareCountSpinner(R.id.spinner_vegetable_count);
        declareCountSpinner(R.id.spinner_meat_count);
        declareCountSpinner(R.id.spinner_seafood_count);
        declareCountSpinner(R.id.spinner_staple_count);
        declareCountSpinner(R.id.spinner_drink_count);
        declareCountSpinner(R.id.spinner_dessert_count);
        declareCountSpinner(R.id.spinner_fruit_count);

        button_generate.setOnClickListener(view -> generate());

        return root;
    }


    public void declareCountSpinner(int viewId){
        Spinner spinner = root.findViewById(viewId);
        spinner.setOnItemSelectedListener(spinnerCountListener);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.count, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void generate(){
        ArrayList<String> dish_types = new ArrayList<>();
        dish_types.addAll(Collections.nCopies(Integer.parseInt(vegetableCount), getResources().getStringArray(R.array.dish_type_database)[0]));
        dish_types.addAll(Collections.nCopies(Integer.parseInt(meatCount), getResources().getStringArray(R.array.dish_type_database)[1]));
        dish_types.addAll(Collections.nCopies(Integer.parseInt(seafoodCount), getResources().getStringArray(R.array.dish_type_database)[2]));
        dish_types.addAll(Collections.nCopies(Integer.parseInt(stapleCount), getResources().getStringArray(R.array.dish_type_database)[3]));
        dish_types.addAll(Collections.nCopies(Integer.parseInt(drinkCount), getResources().getStringArray(R.array.dish_type_database)[4]));
        dish_types.addAll(Collections.nCopies(Integer.parseInt(dessertCount), getResources().getStringArray(R.array.dish_type_database)[5]));
        dish_types.addAll(Collections.nCopies(Integer.parseInt(fruitCount), getResources().getStringArray(R.array.dish_type_database)[6]));

        Intent intent = new Intent(root.getContext(), GenerateResultActivity.class);
        intent.putExtra("meal_type", mealType);
        intent.putExtra("pantry_only", pantry_only);
        intent.putStringArrayListExtra("dish_types", dish_types);
        startActivity(intent);
    }

    public void toastError(String text){
        Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }



    class SpinnerCountingListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String count = adapterView.getItemAtPosition(i).toString();
            int adapterViewId = adapterView.getId();
            if (adapterViewId == R.id.spinner_vegetable_count) {
                vegetableCount = count;
            } else if (adapterViewId == R.id.spinner_meat_count) {
                meatCount = count;
            } else if (adapterViewId == R.id.spinner_seafood_count) {
                seafoodCount = count;
            } else if (adapterViewId == R.id.spinner_staple_count) {
                stapleCount = count;
            } else if (adapterViewId == R.id.spinner_drink_count) {
                drinkCount = count;
            } else if (adapterViewId == R.id.spinner_dessert_count) {
                dessertCount = count;
            } else if (adapterViewId == R.id.spinner_fruit_count) {
                fruitCount = count;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    class SpinnerMealTypeListener implements AdapterView.OnItemSelectedListener {
        String[] itemValue;
        // the English value that will get stored into the database when display is not in English
        public SpinnerMealTypeListener(String[] itemValue){
            this.itemValue = itemValue;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            mealType = itemValue[i];
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}