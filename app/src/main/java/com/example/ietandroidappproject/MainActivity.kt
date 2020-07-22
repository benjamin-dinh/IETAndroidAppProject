package com.example.ietandroidappproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.*
import java.io.IOException



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        listView.adapter = MainAdapter(this) // custom adapter telling list what to render
        fetchJson() // fetch data from API
    }

    fun fetchJson() {
        println("Attempting to Fetch JSON")
        val url = "https://aggiefeed.ucdavis.edu/api/v1/activity/public?s=0?l=25"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string() // get body of response
                println(body)
                val gson = GsonBuilder().create() // create GSON object
                val aggieFeed = gson.fromJson(body, Array<Info>::class.java) // store object data in array of information
                val listView = findViewById<ListView>(R.id.main_listview)
                runOnUiThread { // render Main Activity with information from aggieFeed
                    listView.adapter = MainAdapter(this@MainActivity ,aggieFeed)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }

    // information stored in aggieFeed array, extracted from JSON
    class Info(val _id: String, val title: String, val actor: Actor, @SerializedName("object") val objectTranslated: ObjectType, val published: String)
    class Actor(val displayName: String)
    class ObjectType(val objectType: String)

    // render Main View
    private class MainAdapter(context: Context, val aggieFeed: Array<Info>) : BaseAdapter() {
        private val mContext: Context
        init {
            mContext = context
        }
        // responsible for how many rows in list
        override fun getCount(): Int {
            return aggieFeed.count()
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
            // get views by id
            val positionTextView = rowMain.findViewById<TextView>(R.id.position_textView)
            val nameTextView = rowMain.findViewById<TextView>(R.id.name_textView)
            // fill in ListView information
            nameTextView.text = aggieFeed[position].title
            positionTextView.text = aggieFeed[position].actor.displayName
            // transfer information to Second Activity when user clicks on a row in ListView
            rowMain.setOnClickListener {
                val intent = Intent(rowMain.context, SecondActivity::class.java)
                intent.putExtra("title", aggieFeed[position].title)
                intent.putExtra("displayName", aggieFeed[position].actor.displayName)
                intent.putExtra("objectType", aggieFeed[position].objectTranslated.objectType)
                intent.putExtra("published", aggieFeed[position].published)
                rowMain.context.startActivity(intent)
            }
            return rowMain
        }
    }
}
