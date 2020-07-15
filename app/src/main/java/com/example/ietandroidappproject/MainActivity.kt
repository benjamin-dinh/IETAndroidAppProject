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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.main_listview)

        listView.adapter = MyCustomAdapter(this) // custom adapter telling list what to render
    }

    private class MyCustomAdapter(context: Context) : BaseAdapter() {
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
            return rowMain
//            val textView = TextView(mContext)
////            textView.text = "Here is my row for my listview"
////            return textView
        }
    }
}