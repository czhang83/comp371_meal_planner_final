package com.example.mealplanner.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.database.AppApplication;
import com.example.mealplanner.database.AppDatabase;
import com.example.mealplanner.database.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientEditAdapter extends RecyclerView.Adapter<IngredientEditAdapter.ViewHolder>{
    private List<Ingredient> ingredients;
    private final HashMap<String, String> updatedIngredients; // keep track of the ingredient and ingredient's status that got changed
    private final ArrayList<String> deletedIngredients;

    //pass this list into the constructor of the adapter
    public IngredientEditAdapter(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        updatedIngredients = new HashMap<>();
        deletedIngredients = new ArrayList<>();
    }

    @NonNull
    @Override
    public IngredientEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // inflate the customer layout
        View ingredientView = inflater.inflate(R.layout.item_ingredient_edit, parent, false);

        return new IngredientEditAdapter.ViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientEditAdapter.ViewHolder holder, int position) {
        // populate data into the item through holder
        Ingredient ingredient = ingredients.get(position);

        holder.textView_edit_ingredient_name.setText(ingredient.getIngredient());


        // declare status spinner
        SpinnerStatusListener spinnerStatusListener = new SpinnerStatusListener(ingredient.getIngredient(), ingredient.getStatus(),
                holder.itemView.getResources().getStringArray(R.array.status_database), position);
        holder.spinner_edit_ingredient_status.setOnItemSelectedListener(spinnerStatusListener);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(),
                R.array.status, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        holder.spinner_edit_ingredient_status.setAdapter(statusAdapter);

        Log.d("?", String.valueOf(updatedIngredients));
        holder.spinner_edit_ingredient_status.setSelection(statusAdapter.getPosition(ingredient.getStatus()));
        spinnerStatusListener.startSelect();

        // delete button
        holder.imageButton_delete_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletedIngredients.add(ingredient.ingredient);
                ingredients.remove(position);
                notifyDataSetChanged();
            }
        });

    }


    // Couldn't find a better way to update the database
    public void updateDatabase(){
        for (Map.Entry<String, String> entry : this.updatedIngredients.entrySet()) {
            AppDatabase.databaseWriteExecutor.execute(() -> AppApplication.getDatabase().ingredientDAO().updateStatusByIngredient(entry.getKey(), entry.getValue()));
        }
        for (String ingredient: deletedIngredients){
            AppDatabase.databaseWriteExecutor.execute(() -> {
                // Couldn't find a better way to update the database
                AppApplication.getDatabase().ingredientDAO().deleteIngredient(ingredient);
            });
        }
    }

    @Override
    public int getItemCount() {
        // could be null initially
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    // this should be only triggered by delete after getting info from database
    public void updateIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_edit_ingredient_name;
        Spinner spinner_edit_ingredient_status;
        ImageButton imageButton_delete_ingredient;
        View itemView;

        public ViewHolder(View itemView){
            super(itemView);
            this.itemView = itemView;
            textView_edit_ingredient_name = itemView.findViewById(R.id.textView_edit_ingredient_name);
            spinner_edit_ingredient_status = itemView.findViewById(R.id.spinner_edit_ingredient_status);
            imageButton_delete_ingredient = itemView.findViewById(R.id.imageButton_delete_ingredient);
        }

    }

    class SpinnerStatusListener implements AdapterView.OnItemSelectedListener {

        String ingredient;
        String initialStatus;
        String[] itemValue;
        boolean startSelect = false;
        int position;
        // the English value that will get stored into the database when display is not in English
        public SpinnerStatusListener(String ingredient, String initialStatus, String[] itemValue, int position){
            this.ingredient = ingredient;
            this.initialStatus = initialStatus;
            this.itemValue = itemValue;
            this.position = position;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (startSelect){ // avoid default select trigger this
                ingredients.get(position).status = itemValue[i];
                if (itemValue[i].equals(initialStatus)){
                    updatedIngredients.remove(ingredient);
                } else {
                    updatedIngredients.put(ingredient, itemValue[i]);
                }
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

        public void startSelect(){
            this.startSelect = true;
        }

    }
}
