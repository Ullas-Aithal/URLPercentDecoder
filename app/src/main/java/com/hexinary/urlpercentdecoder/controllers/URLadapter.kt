package com.hexinary.urlpercentdecoder.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hexinary.urlpercentdecoder.R

class URLadapter(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<URLadapter.CustomViewHolder>() {

    class CustomViewHolder(val layoutUrlView: LinearLayout) : RecyclerView.ViewHolder(layoutUrlView)


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): URLadapter.CustomViewHolder {
        // create a new view
        val layoutUrlView = LayoutInflater.from(parent.context)
            .inflate(R.layout.url_view, parent, false) as LinearLayout

        return CustomViewHolder(layoutUrlView)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.layoutUrlView.findViewById<TextView>(R.id.textView_url).text = dataSet[position];
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}