package com.hexinary.urlpercentdecoder.controllers

import android.animation.ObjectAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.hexinary.urlpercentdecoder.R

class URLadapter(private val dataSet: ArrayList<String>, private val context: Context) :
    RecyclerView.Adapter<URLadapter.CustomViewHolder>() {
    private val mContext = context

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

        //When clicked on expand/collapse image, make textview multiline, show/hide other url control options
        holder.layoutUrlView.findViewById<ImageView>(R.id.imageView_expandCollapse).setOnClickListener {
            if (textView.lineCount == 1) {
                textView.setSingleLine(false)
                holder.layoutUrlView.findViewById<LinearLayout>(R.id.linearLayout_urlOptions).visibility = VISIBLE
            } else {
                textView.setSingleLine(true)
                holder.layoutUrlView.findViewById<LinearLayout>(R.id.linearLayout_urlOptions).visibility = GONE
            }
        }

        //Copy the url to clipboard
        holder.layoutUrlView.findViewById<ImageView>(R.id.imageView_copyToClipboard).setOnClickListener{
            val clipboard = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("url", textView.text.toString()))
            Toast.makeText(mContext,mContext.getString(R.string.copied_to_clipboard),Toast.LENGTH_SHORT).show()
        }

        //Open the url in appropriate app
        holder.layoutUrlView.findViewById<ImageView>(R.id.imageView_openInBrowser).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(textView.text.toString())
            mContext.startActivity(intent)
        }

    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}