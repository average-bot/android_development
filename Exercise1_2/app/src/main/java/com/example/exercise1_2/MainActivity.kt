package com.example.exercise1_2

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var mCount = 0
    private var mShowCount: TextView? = null
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mShowCount = findViewById(R.id.show_count);
    }

    fun showToast(view: View) {
        var toast = Toast.makeText (this, R.string.toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }
    fun countUp(view: View) {
        mCount++
        mShowCount?.setText(Integer.toString(mCount))
    }
}