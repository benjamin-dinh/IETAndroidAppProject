package com.example.ietandroidappproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        var intent = intent
        val title = intent.getStringExtra("title")
        val displayName = intent.getStringExtra("displayName")
        val published = intent.getStringExtra("published")

        val titleView = findViewById<TextView>(R.id.title_textView)
        val displayView = findViewById<TextView>(R.id.display_textView)
        val publishedView = findViewById<TextView>(R.id.published_textView)
        titleView.text = title
        displayView.text = displayName
        publishedView.text = published

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}