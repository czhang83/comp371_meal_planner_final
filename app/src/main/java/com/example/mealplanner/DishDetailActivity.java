package com.example.mealplanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplanner.database.AppViewModel;
import com.example.mealplanner.database.Dish;
import com.example.mealplanner.database.Ingredient;
import com.example.mealplanner.recyclerview.IngredientAdapter;
import com.example.mealplanner.utilities.CustomizedView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// camera
// https://developer.android.com/training/camera/photobasics#TaskPath
public class DishDetailActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final float IMAGE_SIZE = 150;

    private AppViewModel appViewModel;
    TextView textView_detail_dish_name;
    TextView textView_detail_dish_type;
    TextView textView_detail_meal_type;
    ImageView imageView_detail_dish;
    Button button_delete;
    Button button_edit_image;

    String dish_name;
    String newImagePath;

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
        button_edit_image = findViewById(R.id.button_edit_dish);
        recyclerView_detail_ingredient = findViewById(R.id.recyclerView_detail_ingredient);

        dish_name = getIntent().getStringExtra("dish_name");
        LiveData<Dish> dish = appViewModel.getDishByName(dish_name);
        dish.observe(this, d ->{
            Log.d("update", "t");
            if (d != null){
                textView_detail_dish_name.setText(d.getDish_name());
                textView_detail_meal_type.setText(getString(R.string.meal) + "\n" + d.getMealTypes());
                textView_detail_dish_type.setText(getString(R.string.type) + " " + d.getDish_type());
                Picasso.get().load(d.getImagePath())
                        .resize(Dp2Px(IMAGE_SIZE), Dp2Px(IMAGE_SIZE))
                        .into(imageView_detail_dish);
            }
        });

        LiveData<List<Ingredient>> ingredients = appViewModel.getDishIngredients(dish_name);
        // create recycler adapter
        IngredientAdapter adapter = new IngredientAdapter(ingredients.getValue());
        recyclerView_detail_ingredient.setAdapter(adapter);
        CustomizedView.setUpRecyclerView(this, recyclerView_detail_ingredient);

        ingredients.observe(this, adapter::updateIngredients);

        button_delete.setOnClickListener(view -> {
            appViewModel.deleteDish(dish_name);
            finish();
        });

        // click the image to edit or change image
        button_edit_image.setOnClickListener(view -> dispatchTakePictureIntent());

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        newImagePath = image.getAbsolutePath();
        return image;
    }

    // open camera, take a picture, store the image path to the dish table
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                toastError(getString(R.string.camera_fail));
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                appViewModel.updateImagePath(dish_name, "file://" + newImagePath);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Picasso.get().load("file://" + newImagePath)
                    .resize(Dp2Px(IMAGE_SIZE), Dp2Px(IMAGE_SIZE))
                    .into(imageView_detail_dish);
        }
    }

    public int Dp2Px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void toastError(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
