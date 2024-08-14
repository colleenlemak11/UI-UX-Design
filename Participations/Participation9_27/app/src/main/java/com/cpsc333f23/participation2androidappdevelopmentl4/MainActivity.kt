package com.cpsc333f23.participation2androidappdevelopmentl4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.util.Log

const val TAG = "MainActivity"
const val WORLD = ", World!"

class MainActivity : AppCompatActivity() {

    //lateinit: allow the properties to be initialized in onCreate()
    //creating a TextView object to hold our TextView from our activity_main.xml
    private lateinit var helloWorldTextView: TextView

    //The Android system calls onCreate() when MainActivity first starts
    //We'll talk about savedInstanceState later!
    override fun onCreate(savedInstanceState: Bundle?) {
        println("onCreate called from println() stmt")
        Log.d(TAG, "onCreate was called (log via Log.d)")
        super.onCreate(savedInstanceState)
        //setContentView() sets the MainActivity's content to the layout in activity_main.xml
        setContentView(R.layout.activity_main)

        //returns an object representing the widget from activity_main.xml that matches the given ID
        //see android:id="@+id/hello_world_text_view" in our text view
        helloWorldTextView = findViewById(R.id.hello_world_text_view)

        //grab the text from our TextView and concatenate it with our const string
        val helloWorld = "${helloWorldTextView.text.toString()}$WORLD"
        //Modify our object!
        helloWorldTextView.text = helloWorld

        informationFunction()

        /*
            val anInt = helloWorld.toInt()
        */

        val someInt = resources.getInteger(R.integer.the_answer)
        println(someInt)

    }

    fun informationFunction() {
        Log.i(TAG,"Demonstrating logging an information " +
                "information message from " + "$TAG")
    }
}