package com.example.lab1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout recipe;
    private ImageView pancakeImage;
    private Button viewBtn;

    public void viewRecipe(View view) {
        if (recipe.getVisibility()==View.VISIBLE){
            recipe.setVisibility(View.INVISIBLE);
            pancakeImage.setImageResource(R.drawable.pancakes);
            viewBtn.setText(R.string.button_show_recipe);
        }
        else{
            recipe.setVisibility(View.VISIBLE);
            pancakeImage.setImageResource(R.drawable.pancakes2);
            viewBtn.setText(R.string.button_hide_recipe);
        }
    }

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");

        recipe = findViewById(R.id.layout_recipe);
        recipe.setVisibility(View.INVISIBLE);

        pancakeImage = findViewById(R.id.imageView_Pancakes);
        pancakeImage.setImageResource(R.drawable.pancakes);

        viewBtn = findViewById(R.id.button_showRecipe);
        viewBtn.setText(R.string.button_show_recipe);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}