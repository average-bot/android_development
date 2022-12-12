/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tensorflow.lite.examples.textclassification

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.textclassification.databinding.ActivityMainBinding
import org.tensorflow.lite.support.label.Category

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val activityMainBinding get() = _activityMainBinding!!
    private lateinit var classifierHelper: TextClassificationHelper
    private val adapter by lazy {
        ResultsAdapter()
    }

    private val listener = object : TextClassificationHelper.TextResultsListener {
        @SuppressLint("NotifyDataSetChanged")
        override fun onResult(results: List<Category>) {
            runOnUiThread {
                adapter.resultsList = results.sortedByDescending {
                    it.score
                }
                adapter.notifyDataSetChanged()
            }
        }

        override fun onError(error: String) {
            Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Create the classification helper that will do the heavy lifting
        classifierHelper = TextClassificationHelper(
            context = this@MainActivity,
            listener = listener)

        // Classify the text in the TextEdit box (or the default if nothing is added)
        // on button click.
        activityMainBinding.classifyBtn.setOnClickListener {
            if (activityMainBinding.inputText.text.isNullOrEmpty()) {
                Log.d("MainActivity", "Nothing to classify")
                classifierHelper.classify(getString(R.string.default_edit_text))
            }
            else {
                classifierHelper.classify(activityMainBinding.inputText.text.toString())
            }
        }
        activityMainBinding.results.adapter = adapter
    }
}