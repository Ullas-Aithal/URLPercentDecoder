package com.hexinary.urlpercentdecoder.controllers

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hexinary.urlpercentdecoder.R

class URLadapter(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<URLadapter.CustomViewHolder>() {

    class CustomViewHolder(val layoutUrlView: ConstraintLayout) : RecyclerView.ViewHolder(layoutUrlView)


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): URLadapter.CustomViewHolder {
        // create a new view
        val layoutUrlView = LayoutInflater.from(parent.context)
            .inflate(R.layout.url_view, parent, false) as ConstraintLayout

        return CustomViewHolder(layoutUrlView)
    }



    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val textView = holder.layoutUrlView.findViewById<TextView>(R.id.textView_url)
        textView.text = dataSet[position]

        //Expand/Collapse text to single or multi line
        textView.setOnClickListener {
            if (textView.lineCount == 1) {
                textView.setSingleLine(false)
            } else {
                textView.setSingleLine(true)
            }
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}