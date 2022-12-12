package com.example.myapplication.questions

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class QuestionsFragment : Fragment() {
    private val rowsList = ArrayList<String>()
    private lateinit var customAdapter: QuestionsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView_Questions)
        customAdapter = QuestionsAdapter(rowsList)
        val layoutManager = LinearLayoutManager(context) // applicationContext
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter
        prepareItems()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun prepareItems() {
        rowsList.add("Sleep")
        rowsList.add("Nutrition")
        rowsList.add("Stress")
        rowsList.add("Alcohol")
        customAdapter.notifyDataSetChanged()
    }
}