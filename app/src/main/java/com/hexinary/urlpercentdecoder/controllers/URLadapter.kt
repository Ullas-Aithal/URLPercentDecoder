package com.hexinary.urlpercentdecoder.controllers

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hexinary.urlpercentdecoder.MainActivity
import com.hexinary.urlpercentdecoder.R
import com.hexinary.urlpercentdecoder.model.URLitem

class URLadapter(private val dataSet: ArrayList<URLitem>, private val context: Context) :
    RecyclerView.Adapter<URLadapter.CustomViewHolder>() {
    private val mContext = context

    class CustomViewHolder(val layoutUrlView: ConstraintLayout) : RecyclerView.ViewHolder(layoutUrlView)


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): URLadapter.CustomViewHolder {
        // create a new view
        val layoutUrlView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_url_item, parent, false) as ConstraintLayout

        return CustomViewHolder(layoutUrlView)
    }



    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val textView = holder.layoutUrlView.findViewById<TextView>(R.id.textView_url)
        val expandCollapseImageView = holder.layoutUrlView.findViewById<ImageView>(R.id.imageView_expandCollapse)

        textView.text = dataSet[position].text

        fun expand(){
            expandCollapseImageView.rotation = 90.0F
            textView.setSingleLine(false)
            holder.layoutUrlView.findViewById<LinearLayout>(R.id.linearLayout_urlOptions).visibility = VISIBLE
            dataSet[position].isExpanded = true
        }
        fun collapse(){
            expandCollapseImageView.rotation = 0F
            textView.setSingleLine(true)
            holder.layoutUrlView.findViewById<LinearLayout>(R.id.linearLayout_urlOptions).visibility = GONE
            dataSet[position].isExpanded = false
        }
        fun expandOrCollapse(){
            if(dataSet[position].isExpanded){
                expand()
            } else {
                collapse()
            }
        }
        expandOrCollapse()
        //Expands/Collapses item on click
        val decodedUrlClickListener = View.OnClickListener {
            if (textView.lineCount == 1) {
                expand()
            } else {
                collapse()
            }
        }
        //When clicked on expand/collapse image, make textview multiline, show/hide other url control options
        expandCollapseImageView.setOnClickListener (decodedUrlClickListener)
        textView.setOnClickListener(decodedUrlClickListener)



        //Copy the url to clipboard
        holder.layoutUrlView.findViewById<ImageView>(R.id.imageView_copyToClipboard).setOnClickListener{
            val clipboard = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("url", textView.text.toString()))
            Toast.makeText(mContext,mContext.getString(R.string.copied_to_clipboard),Toast.LENGTH_SHORT).show()
        }

        holder.layoutUrlView.findViewById<ImageView>(R.id.imageView_decodeThis).setOnClickListener {
            (mContext as MainActivity).mainScreenView.reDecodeURL(dataSet[position].text)

        }

        //Share url
        holder.layoutUrlView.findViewById<ImageView>(R.id.imageView_shareUrl).setOnClickListener {
            val intent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textView.text.toString())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, null)
            mContext.startActivity(shareIntent)
        }
        //Open the url in appropriate app
        holder.layoutUrlView.findViewById<ImageView>(R.id.imageView_openInBrowser).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(textView.text.toString())
            }
            mContext.startActivity(intent)
        }

    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size



}