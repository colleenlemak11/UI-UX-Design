package com.cpsc333f23.activitygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val button = findViewById<Button>(R.id.play_game_button)

        button.setOnClickListener {view: View ->
            val intent = Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
    }
}