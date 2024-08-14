package com.cpsc333f23.participation2androidappdevelopmentl3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

const val WORLD = ", World!"
class MainActivity : AppCompatActivity() {
    // lateinit: allow the properties to be initialized in onCreate()
    // creating a TextView object to hold our Textview from our activity_main.xml
    private lateinit var helloWorldTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helloWorldTextView = findViewById(R.id.hello_world_text_view)
        val helloWorld = "${helloWorldTextView.text.toString()}$WORLD"
        helloWorldTextView.text = helloWorld
    }
}