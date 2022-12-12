package com.example.myapplication.analysis

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.io.FileInputStream
import java.io.InputStream
import android.os.Handler

class AnalysisFragment : Fragment() {
    // https://www.geeksforgeeks.org/android-create-barchart-with-kotlin/
    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>

    data class Health(
        var sleep: Int,
        var nutrition: Int,
        var stress: Int,
        var alcohol: Int
    )

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 1000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }
    // https://www.tutorialspoint.com/how-to-run-a-method-every-10-seconds-in-android-using-kotlin
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        createChart(itemView)
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            createChart(itemView)
        }.also { runnable = it }, delay.toLong())
    }
    private fun createChart(itemView: View){
        barChart = requireView().findViewById(R.id.barChart_Analysis)
        getBarChartData(itemView)
        barDataSet = BarDataSet(barEntriesList, "Scores")
        barData = BarData(barDataSet)
        barChart.data = barData
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.setColor(resources.getColor(R.color.purple_200))
        barDataSet.valueTextSize = 16f
        barChart.description.isEnabled = false
        barChart.setScaleEnabled(false)

        val labels : Array<String> = arrayOf("", "Sleep","Nutrition","Stress","Alcohol")
        barChart.xAxis.setValueFormatter(IndexAxisValueFormatter(labels))
        barChart.xAxis.setGranularity(1f)
        barChart.xAxis.setGranularityEnabled(true)
        barChart.xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    private fun getBarChartData(itemView: View) {
        val currentHealth : TextView = itemView.findViewById(R.id.textView_currentHealth)
        barEntriesList = ArrayList()
        // on below line we are adding data
        // to our bar entries list

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
        val healthInfo = readCsv(FileInputStream((context?.filesDir?.path ?: "") +"result.csv"))[0]
        currentHealth.text = getHealthText(healthInfo)
        barEntriesList.add(BarEntry(1f, healthInfo.sleep.toFloat()))
        barEntriesList.add(BarEntry(2f, healthInfo.nutrition.toFloat()))
        barEntriesList.add(BarEntry(3f, healthInfo.stress.toFloat()))
        barEntriesList.add(BarEntry(4f, healthInfo.alcohol.toFloat()))

    }
    fun getHealthText(healthInfo: Health): String {
        var healthStory : String = ""
        healthStory += "You slept " + if (healthInfo.sleep >= 1) "pretty well." else "bad."
        healthStory += " You ate " + if (healthInfo.nutrition >= 1) "well." else "bad."
        healthStory += " Your stress levels are " + if (healthInfo.stress >= 1) "low." else "high."
        healthStory += " You consumed " + if (healthInfo.alcohol >= 1) "no or reasonable amount of alcohol" else "an unhealthy amount of alcohol"
        healthStory += " so far."
        return healthStory
    }
}