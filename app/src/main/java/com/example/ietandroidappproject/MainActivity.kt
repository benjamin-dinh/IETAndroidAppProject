package com.example.ietandroidappproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.main_listview)

        listView.adapter = MainAdapter(this) // custom adapter telling list what to render
        fetchJson()
    }

    fun fetchJson() {
        println("Attempting to Fetch JSON")
        val url = "https://aggiefeed.ucdavis.edu/api/v1/activity/public?s=0?l=25"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)
                val gson = GsonBuilder().create()
                val aggieFeed = gson.fromJson(body, AggieFeed::class.java)
            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }
    class AggieFeed(val infoBlock: List<Info>)

    class Info(val _id: String, val title: String)

    private class MainAdapter(context: Context) : BaseAdapter() {
        private val mContext: Context
        init {
            mContext = context
        }
        // responsible for how many rows in list
        override fun getCount(): Int {
            return 25
        }
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }
        // responsible for rendering each row of list
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)
            val positionTextView = rowMain.findViewById<TextView>(R.id.position_textView)
            positionTextView.text = "Row number: $position"
            return rowMain
        }
    }
}
