package com.example.myapplication.questions

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

internal class QuestionsAdapter(private var rowsList: ArrayList<String>) :

    RecyclerView.Adapter<QuestionsAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var startTextView: TextView = view.findViewById(R.id.textView_QuestionTopic)
        var questionCard: CardView = view.findViewById(R.id.card_Question)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_questions, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.startTextView.text = rowsList[position]
        // onclick on the box
        holder.questionCard.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(view.context, BertActivity::class.java)
            intent.putExtra("topic", rowsList[position])
            view.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return rowsList.size
    }
}