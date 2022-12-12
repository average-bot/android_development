package com.example.myapplication.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import androidx.recyclerview.widget.LinearLayoutManager

class HomeFragment : Fragment() {
    private val itemsList = ArrayList<Triple<String, String, Int>>()
    private lateinit var customAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView_Home)
        customAdapter = HomeAdapter(itemsList)
        val layoutManager = LinearLayoutManager(context) // applicationContext
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter
        prepareItems()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun prepareItems() {
        itemsList.add(Triple("Introduction to mental health", "https://www.youtube.com/watch?v=-OAjfrhuwRk&t=5s", R.drawable.film1a))
        itemsList.add(Triple("Sleep and mental health", "https://www.youtube.com/watch?v=98V1q5k8x5E&t=1s", R.drawable.film2a))
        itemsList.add(Triple("Nutrition and mental health", "https://www.youtube.com/watch?v=xyQY8a-ng6g", R.drawable.film3a))
        itemsList.add(Triple("Stress and mental health", "https://www.youtube.com/watch?v=DxIDKZHW3-E", R.drawable.film4a))
        itemsList.add(Triple("Alcohol and mental health", "https://www.youtube.com/watch?v=hzcZd08PqSQ", R.drawable.film5a))
        customAdapter.notifyDataSetChanged()
    }
}