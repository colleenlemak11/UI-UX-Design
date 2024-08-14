package com.example.participation5menusdialogsandtouchl1starter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

/* Got to step 6 today */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}