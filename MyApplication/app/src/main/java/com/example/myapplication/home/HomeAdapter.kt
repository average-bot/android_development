package com.example.myapplication.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R


internal class HomeAdapter(private var itemsList: ArrayList<Triple<String, String, Int>>) :
    // For making recyclerView
    // https://www.tutorialkart.com/kotlin-android/kotlin-android-recyclerview/
    // https://medium.com/inside-ppl-b7/recyclerview-inside-fragment-with-android-studio-680cbed59d84

    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTextView: TextView = view.findViewById(R.id.textView_name)
        var thumbnailImageView: ImageView = view.findViewById(R.id.imageView_thumbnail)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        // title
        holder.nameTextView.text = item.first

        // onclick on the image
        holder.thumbnailImageView.setOnClickListener(View.OnClickListener { view->
            val uri = Uri.parse(item.second) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            view.context.startActivity(intent)
        })

        // image
        holder.thumbnailImageView.setImageResource(item.third)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }



}