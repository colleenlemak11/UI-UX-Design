package com.zybook.multipleactivities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_COLOR = "com.zybook.multipleactivities.color"

class ColorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)
    }

    fun onColorSelected(view: View) {
        val colorId = when (view.id) {
            R.id.radio_red -> R.color.red
            R.id.radio_orange -> R.color.orange
            R.id.radio_green -> R.color.green
            else -> R.color.yellow
        }

        val dataIntent = Intent()
        dataIntent.putExtra(EXTRA_COLOR, colorId)
        setResult(RESULT_OK, dataIntent)
        finish()
    }


}