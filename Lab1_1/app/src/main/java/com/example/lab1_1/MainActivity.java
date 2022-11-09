package com.example.lab1_1;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public final String[] facts = {
            "My name is Sandra.",
            "My last name starts with the letter K.",
            "I am 22 years old.",
            "I live in Sweden.",
            "My favorite color is yellow.",
            "I like programming.",
            "I like to eat and cook good food.",
            "My favorite food is lasagna.",
            "I am happy.",
            "I like to work out.",
            "I like watching reality tv."
        };

    private TextView sentenceBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");
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

    public void showRandomSentence(View view) {
        String sentence = getRandomFact(facts);
        sentenceBox = (TextView)findViewById(R.id.textView_random);
        sentenceBox.setText(sentence);
    }

    public static String getRandomFact(String[] facts) {
        int index = new Random().nextInt(facts.length);
        return facts[index];
    }
}