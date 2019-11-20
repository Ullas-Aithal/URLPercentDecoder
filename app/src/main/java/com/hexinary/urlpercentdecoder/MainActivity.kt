package com.hexinary.urlpercentdecoder

import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.URLUtil
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hexinary.urlpercentdecoder.controllers.URLadapter
import com.hexinary.urlpercentdecoder.model.Constants
import com.hexinary.urlpercentdecoder.model.URLitem
import com.hexinary.urlpercentdecoder.views.MainScreenView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {


    private var decodedValidURLs: ArrayList<URLitem> = arrayListOf()
    private var decodedInvalidURLs: ArrayList<String> = arrayListOf()
    private lateinit var mainScreenView: MainScreenView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
        val recyclerViewLayout = findViewById<ConstraintLayout>(R.id.constraintLayout_recyclerViewHolder)

        mainScreenView = MainScreenView(this, mainLayout)
        mainScreenView.initializeRecyclerView(decodedValidURLs,recyclerViewLayout)
        if(savedInstanceState != null) {
            mainScreenView.restoreInstanceData(savedInstanceState)
            if(!TextUtils.isEmpty(mainScreenView.inputUrlText)){
                decode(mainScreenView.inputUrlText)
            }
        }


        //Decode urls on button click
        button_urlDecode.setOnClickListener {
            decode(mainScreenView.inputUrlText)
        }

        button_expandCollapse.setOnClickListener {
            mainScreenView.initalizeAlertDialog()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mainScreenView.saveInstanceData(outState)
        super.onSaveInstanceState(outState)
    }

    /*override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mainScreenView.restoreInstanceData(savedInstanceState)

    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    /**
     * This function takes @param url which is the input URL. This url can contain
     * multiple urls embedded in them. Split all those urls, decode the
     * corresponding percent encoding and add valid and invalid urls to two separate
     * array lists
     * */
    private fun decodeUrl(url: String?) {
        if(url != null) {

            //Add ^ as a delimiter before http keyword
            val delimitedStrings = url.replace("http", "^http")
            //Split strings on delimiter ^
            val splitStrings = delimitedStrings.split("^")
            //For each string, decode the url, check if the url is valid and add it to appropriate list
            splitStrings.forEach {
                val tempString = it.split(" ")
                tempString.forEach {
                    val decodedUrl = URLDecoder.decode(it, StandardCharsets.UTF_8.name()).toString()
                    if (URLUtil.isValidUrl(decodedUrl)) {
                        decodedValidURLs.add(URLitem(decodedUrl,true))
                    } else {
                        decodedInvalidURLs.add(decodedUrl)
                    }
                }
            }
        }
    }

    fun decode(urls:String?){
        mainScreenView.clearRecyclerViewData()
        decodeUrl(urls)
        mainScreenView.notifyDataSetChange()
    }
    fun reInitialize(text: String){
        mainScreenView.clearRecyclerViewData()
        mainScreenView.loadUrl(text)

    }



}
