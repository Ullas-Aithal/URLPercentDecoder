package com.hexinary.urlpercentdecoder.views

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
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
import com.hexinary.urlpercentdecoder.MainActivity
import com.hexinary.urlpercentdecoder.R
import com.hexinary.urlpercentdecoder.controllers.URLadapter
import com.hexinary.urlpercentdecoder.model.Constants
import com.hexinary.urlpercentdecoder.model.URLitem

class MainScreenView(private val mContext: Context, private val mainLayout: ConstraintLayout) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerViewLayout: ConstraintLayout
    var inputUrlText: String? = null
    private val textViewInputUrl = mainLayout.findViewById<TextView>(R.id.textView_inputUrl)
    private val buttonUrlDecode = mainLayout.findViewById<Button>(R.id.button_urlDecode)
    private var restoreInstanceData: Parcelable? = null
    private lateinit var decodedValidURLs: ArrayList<URLitem>

    fun initializeRecyclerView(decodedValidURLs: ArrayList<URLitem>, recyclerViewLayout: ConstraintLayout){
        this.recyclerViewLayout = recyclerViewLayout
        this.decodedValidURLs = decodedValidURLs
        viewManager = LinearLayoutManager(mContext)
        viewAdapter = URLadapter(this.decodedValidURLs,mContext)
        //Initialize recycler view with no data
        recyclerView = recyclerViewLayout.findViewById<RecyclerView>(R.id.recyclerview_urllist).apply {

            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

            //Restore data
            if(restoreInstanceData != null) {
                layoutManager?.onRestoreInstanceState(restoreInstanceData)
            }
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
             .setPositiveButton(mContext.resources.getString(R.string.alert_dialog_done), DialogInterface.OnClickListener {
                    dialogInterface, i ->
                 //Load the url. Ready to decode
                if(TextUtils.isEmpty(alertDialogEditText.text.toString())){
                    Toast.makeText(mContext,mContext.getString(R.string.cannot_be_empty),Toast.LENGTH_SHORT).show()
                } else {
                    loadUrl(alertDialogEditText.text.toString())
                }
            }).setNeutralButton(mContext.resources.getString(R.string.alert_dialog_cancel), DialogInterface.OnClickListener { dialogInterface, i ->
                //Don't do anything. Dismiss
            })

            .setNegativeButton(mContext.resources.getString(R.string.alert_dialog_clear_all),DialogInterface.OnClickListener {
                    dialogInterface, i ->

                //Clear all data
                alertDialogEditText.text.clear()
                textViewInputUrl.text = null
                textViewInputUrl.visibility = GONE
                buttonUrlDecode.isEnabled = false
                clearRecyclerViewData()
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
    fun loadUrl(text: String){
        inputUrlText = text
        textViewInputUrl.visibility = VISIBLE
        textViewInputUrl.text = inputUrlText
        buttonUrlDecode.isEnabled = true
    }
    fun saveInstanceData(bundle: Bundle) {
        var parceableData: Parcelable? = null
        recyclerView.layoutManager?.apply {
            parceableData = onSaveInstanceState()
        }
        bundle.putParcelable(Constants.URL_LIST_RECYCLER_STATE,parceableData)
        bundle.putString(Constants.URL_LIST_STRING,inputUrlText)
    }
    fun  restoreInstanceData(bundle: Bundle) {
        restoreInstanceData = bundle.getParcelable(Constants.URL_LIST_RECYCLER_STATE)
        loadUrl(bundle.getString(Constants.URL_LIST_STRING,""))
    }
    fun clearRecyclerViewData(){
        decodedValidURLs.clear()
        //decodedInvalidURLs.clear()
        notifyDataSetChange()
    }

    fun reDecodeURL(url: String){
        AlertDialog.Builder(mContext)
            .setMessage("Do you want to decode this url?")
            .setPositiveButton("Done", DialogInterface.OnClickListener {
                    dialogInterface, i ->
                reInitialize(url)

            })

            .setNegativeButton("Cancel",DialogInterface.OnClickListener {
                    dialogInterface, i ->

            })

            .create()
            .show()
    }

    fun reInitialize(text: String){
        clearRecyclerViewData()
        loadUrl(text)

    }


}