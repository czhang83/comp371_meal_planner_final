package com.example.mealplanner.utilities;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mealplanner.R;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// set up views
// add layout, decoration, etc
public class CustomizedView {

    public static void setUpRecyclerView(Context context, RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }

}
