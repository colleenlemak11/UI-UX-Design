package com.cpsc333f23.gc3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

//needed to use Memory package
import com.cpsc333f23.memory.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //placeholder for Memory game object
        //filter logcat by System.out for println() statements
        var game = Memory(6, this)

        // The following would "do something" (output "Card click") when
        // the chosen TextView (based on the id) is clicked.

        var card = findViewById<TextView>(R.id.card1) //what should ??? be?
        card.setOnClickListener { //looking ahead a bit... see zyBook 3.4 "Creating callbacks"
            doSomething(it) //see zyBook 1.7 "it parameter"
        }
    }

    private fun doSomething(view: View) {
        //method 1 - intermediate variable
        //val viewTextView = findViewById<TextView>(view.id)
        //method 2 - intermediate variable
        val viewTextView = view as TextView

        //method 1 or 2 - intermediate variable
        Toast.makeText(this, "Clicked! (contents was ${viewTextView.text.toString()})", Toast.LENGTH_LONG).show()

        //method 3 - without intermediate variable
        //Toast.makeText(this, "Clicked! (contents was ${(view as TextView).text.toString()})", Toast.LENGTH_LONG).show()
    }
}