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

import android.content.Context
import android.os.SystemClock
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier
import java.util.concurrent.ScheduledThreadPoolExecutor

class TextClassificationHelper(
    val context: Context,
    val listener: TextResultsListener,
) {
    // MobileBERT model (BertNLClassifier)
    private lateinit var bertClassifier: BertNLClassifier
    private lateinit var executor: ScheduledThreadPoolExecutor

    init {initClassifier()}

    fun initClassifier() {
        val baseOptionsBuilder = BaseOptions.builder()
        val baseOptions = baseOptionsBuilder.build()

        // Directions for generating models can be found at
        // https://www.tensorflow.org/lite/models/modify/model_maker/text_classification

        val options = BertNLClassifier.BertNLClassifierOptions
            .builder()
            .setBaseOptions(baseOptions)
            .build()

        bertClassifier = BertNLClassifier.createFromFileAndOptions(
            context,
            MOBILEBERT,
            options)
    }

    fun classify(text: String) {
        executor = ScheduledThreadPoolExecutor(1)
        executor.execute {
            // Classify the text
            val results: List<Category> = bertClassifier.classify(text)
            listener.onResult(results)
        }
    }

    interface TextResultsListener {
        fun onError(error: String)
        fun onResult(results: List<Category>)
    }

    companion object {
        const val MOBILEBERT = "mobilebert.tflite"
    }
}