package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mealplanner.recyclerview.DishSearchedAdapter;
import com.example.mealplanner.recyclerview.IngredientAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class SearchDishActivity extends AppCompatActivity {
    private Button button_search;
    private Button button_add_customize;
    private RecyclerView recyclerView_search_result;
    private EditText editText_search_dish;

    private static AsyncHttpClient client = new AsyncHttpClient();
    String api_url = "https://www.themealdb.com/api/json/v1/1/search.php?s=";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dish);
        button_search = findViewById(R.id.button_search);
        button_add_customize = findViewById(R.id.button_add_customize);
        recyclerView_search_result = findViewById(R.id.recyclerView_search_result);
        editText_search_dish = findViewById(R.id.editText_search_dish);

        // create recycler adapter
        DishSearchedAdapter adapter = new DishSearchedAdapter();
        recyclerView_search_result.setAdapter(adapter);
        recyclerView_search_result.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_search_result.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL);
        recyclerView_search_result.addItemDecoration(itemDecoration);

        button_search.setOnClickListener(this::searchDish);
        button_add_customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchDishActivity.this, AddDishActivity.class);
                startActivity(intent);
            }
        });

    }

    public void searchDish(View view){
        // get user input
        String name = editText_search_dish.getText().toString();
        if (name.isEmpty()){
            toastError(getString(R.string.no_dish_name));
        } else {
            String url = api_url + name;
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("api response", new String(responseBody));
                    try {
                        JSONArray dishesJSON = new JSONObject(new String(responseBody)).getJSONArray("meals");
                        List<JSONObject> dishes = new ArrayList<>();
                        for (int i=0; i < dishesJSON.length(); i++) {
                            dishes.add(dishesJSON.getJSONObject(i));
                        }
                        ((DishSearchedAdapter)recyclerView_search_result.getAdapter()).updateDishes(dishes);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    toastError(getString(R.string.api_fail));
                }
            });
        }
    }

    public void toastError(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
