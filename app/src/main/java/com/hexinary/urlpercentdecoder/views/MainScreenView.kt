package com.hexinary.urlpercentdecoder.views

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hexinary.urlpercentdecoder.R
import com.hexinary.urlpercentdecoder.controllers.URLadapter

class MainScreenView(private val mContext: Context, private val mainLayout: ConstraintLayout) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerViewLayout: ConstraintLayout
    var inputUrlText: String? = null
    private val textViewInputUrl = mainLayout.findViewById<TextView>(R.id.textView_inputUrl)
    private val buttonUrlDecode = mainLayout.findViewById<Button>(R.id.button_urlDecode)

    fun initializeRecyclerView(decodedValidURLs: ArrayList<String>, recyclerViewLayout: ConstraintLayout){
        this.recyclerViewLayout = recyclerViewLayout
        viewManager = LinearLayoutManager(mContext)
        viewAdapter = URLadapter(decodedValidURLs,mContext)
        //Initialize recycler view with no data
        recyclerView = recyclerViewLayout.findViewById<RecyclerView>(R.id.recyclerview_urllist).apply {

            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        val decoration = DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)

    }
    fun notifyDataSetChange(){
        viewAdapter.notifyDataSetChanged()
    }
    fun initalizeAlertDialog()  {
        val alertDialogLayout = LayoutInflater.from(mContext).inflate(R.layout.alert_dialog_input_url, null)
        val alertDialogEditText = alertDialogLayout.findViewById<EditText>(R.id.alertDialog_editText_inputUrl)
        alertDialogEditText.setText(textViewInputUrl.text.toString())

       AlertDialog.Builder(mContext)
            .setView(alertDialogLayout)
             .setTitle(mContext.getString(R.string.add_edit_url))
             .setPositiveButton("Done", DialogInterface.OnClickListener {
                    dialogInterface, i ->
                if(TextUtils.isEmpty(alertDialogEditText.text.toString())){
                    Toast.makeText(mContext,mContext.getString(R.string.cannot_be_empty),Toast.LENGTH_SHORT).show()
                } else {
                    inputUrlText = alertDialogEditText.text.toString()
                    textViewInputUrl.visibility = VISIBLE
                    textViewInputUrl.text = inputUrlText
                    buttonUrlDecode.isEnabled = true
                    Toast.makeText(mContext,R.string.url_loaded,Toast.LENGTH_SHORT).show()
                }
            }).setNeutralButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->

            })

            .setNegativeButton("Clear All",DialogInterface.OnClickListener {
                    dialogInterface, i ->
                alertDialogEditText.text.clear()
                textViewInputUrl.text = null
                textViewInputUrl.visibility = GONE
                buttonUrlDecode.isEnabled = false
                Toast.makeText(mContext,R.string.url_cleared,Toast.LENGTH_SHORT).show()
            })

            .create()
            .show()
//        val alertDialogBuilder = AlertDialog.Builder(mContext)
//        alertDialogBuilder.setView(alertDialogLayout)
//        alertDialogBuilder.setTitle("Test")
//        alertDialogBuilder.setPositiveButton( "OK", null)
//        val alertDialog = alertDialogBuilder.create()
//        alertDialog.setOnShowListener(DialogInterface.OnShowListener(){
//            fun onShow(dialog: DialogInterface){
//                (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
//                    Toast.makeText(mContext,"Works",Toast.LENGTH_SHORT).show()
//                }
//            }
//        } )
//        alertDialog.show()

    }


}