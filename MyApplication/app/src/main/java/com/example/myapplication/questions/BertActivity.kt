package com.example.myapplication.questions

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.analysis.AnalysisFragment
import com.example.myapplication.databinding.ActivityBertBinding
import org.tensorflow.lite.support.label.Category
import java.io.*


class BertActivity : AppCompatActivity() {
    private var _activityBertBinding: ActivityBertBinding? = null
    private val activityBertBinding get() = _activityBertBinding!!
    private lateinit var classifierHelper: TextClassificationHelper
    private val adapter by lazy {
        BertResultsAdapter()
    }

    private lateinit var topic : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityBertBinding = ActivityBertBinding.inflate(layoutInflater)
        setContentView(activityBertBinding.root)

        // change to question in textview
        topic = intent.extras?.getString("topic").toString()
        val question = "How is everything with $topic today?"
        activityBertBinding.textViewQuestionnaireQuestion.text = question

        // Create the classification helper that will do the heavy lifting
        classifierHelper = TextClassificationHelper(
            context = this@BertActivity,
            listener = listener)

        // Classify the text in the TextEdit box (or the default if nothing is added)
        // on button click.
        activityBertBinding.buttonQuestionnaireSubmit.setOnClickListener {
            if (activityBertBinding.editTextQuestionnaireInput.text.isNullOrEmpty()) {
                Log.d("BertActivity", "Nothing to classify")
            }
            else {
                classifierHelper.classify(activityBertBinding.editTextQuestionnaireInput.text.toString())
            }
        }
        //activityBertBinding.recyclerViewResults.adapter = adapter
    }

    private val listener = object : TextClassificationHelper.TextResultsListener {
        @SuppressLint("NotifyDataSetChanged")
        override fun onResult(results: List<Category>) {
            runOnUiThread {
                adapter.resultsList = results
                data class Health(
                    var sleep: Int,
                    var nutrition: Int,
                    var stress: Int,
                    var alcohol: Int
                )
                // read from the CSV file
                fun readCsv(inputStream: InputStream): List<Health> {
                    val reader = inputStream.bufferedReader()
                    val header = reader.readLine()
                    return reader.lineSequence()
                        .filter { it.isNotBlank() }
                        .map {
                            val (sleep, nutrition, stress, alcohol) = it.split(',', ignoreCase = false, limit = 4)
                            Health(sleep.trim().toInt(), nutrition.trim().toInt(), stress.trim().toInt(), alcohol.trim().toInt())
                        }.toList()
                }
                val healthInfo = readCsv(FileInputStream(filesDir.path+"result.csv"))[0]

                //Attach the new information
                var score = if (results[0].score > 0.5) -1 else 1
                when (topic){
                    "Sleep" -> healthInfo.sleep += score
                    "Nutrition" -> healthInfo.nutrition += score
                    "Stress" -> healthInfo.stress += score
                    "Alcohol" -> healthInfo.alcohol += score
                    else -> Log.d("BertActivity","Failed to add score")
                }
                Log.d("BertBefore", healthInfo.toString())

                //Write to the file again with "new" information included
                fun OutputStream.writeCsv(healthInfo: Health) {
                    val writer = bufferedWriter()
                    writer.write(""""Sleep", "Nutrition", "Stress", "Alcohol"""")
                    writer.newLine()
                    writer.write("${healthInfo.sleep}, ${healthInfo.nutrition}, ${healthInfo.stress}, ${healthInfo.alcohol}")
                    writer.newLine()
                    writer.flush()
                }

                val file = File(filesDir.path+"result.csv")
                if (!file.exists()) {
                    file.createNewFile()
                }
                FileOutputStream(file).apply { writeCsv(healthInfo) }
                Log.d("BertAfter", healthInfo.toString())

                adapter.notifyDataSetChanged()
                finish()
            }
        }

        override fun onError(error: String) {
            Toast.makeText(this@BertActivity, error, Toast.LENGTH_SHORT).show()
        }
    }
}