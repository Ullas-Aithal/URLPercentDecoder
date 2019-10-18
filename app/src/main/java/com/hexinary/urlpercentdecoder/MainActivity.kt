package com.hexinary.urlpercentdecoder

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.webkit.URLUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hexinary.urlpercentdecoder.controllers.URLadapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var decodedValidURLs: ArrayList<String> = arrayListOf()
    private var decodedInvalidURLs: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        viewManager = LinearLayoutManager(this)
        viewAdapter = URLadapter(decodedValidURLs)

        //Initialize recycler view with no data
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_urllist).apply {
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        //Decode urls on button click
        button_urlDecode.setOnClickListener {
            decodeUrl(editText_inputUrl.text.toString())
            viewAdapter.notifyDataSetChanged()
        }


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

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
    private fun decodeUrl(url: String) {
        //Add ^ as a delimiter before http keyword
        val delimitedStrings = url.replace("http","^http")
        //Split strings on delimiter ^
        val splitStrings = delimitedStrings.split("^")
        //For each string, decode the url, check if the url is valid and add it to appropriate list
        splitStrings.forEach {
            val tempString = it.split(" ")
            tempString.forEach {
                val decodedUrl = URLDecoder.decode(it, StandardCharsets.UTF_8.name()).toString()
                if(URLUtil.isValidUrl(decodedUrl)){
                    decodedValidURLs.add(decodedUrl)
                } else {
                    decodedInvalidURLs.add(decodedUrl)
                }
            }
        }
    }
}
