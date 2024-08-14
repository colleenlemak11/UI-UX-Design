package com.example.partyapplication
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_COLOR = "colorSelected"

class ColorActivity : AppCompatActivity() {
    private lateinit var activityColor : LinearLayout
    private lateinit var radioRed : RadioButton
    private lateinit var radioOrange : RadioButton
    private lateinit var radioYellow : RadioButton
    private lateinit var radioGreen : RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)

        // Get selected color ID from MainActivity
        val colorId = intent.getIntExtra(EXTRA_COLOR, R.color.yellow)
        activityColor = findViewById(R.id.activity_color)
        radioRed = findViewById(R.id.radio_red)
        radioOrange = findViewById(R.id.radio_orange)
        radioYellow = findViewById(R.id.radio_yellow)
        radioGreen = findViewById(R.id.radio_green)
        radioRed.setOnClickListener() {
            onColorSelected(radioRed)
        }
        radioOrange.setOnClickListener() {
            onColorSelected(radioOrange)
        }
        radioYellow.setOnClickListener() {
            onColorSelected(radioYellow)
        }
        radioGreen.setOnClickListener() {
            onColorSelected(radioGreen)
        }
        registerForContextMenu(activityColor)
        // Select the radio button matching the color ID
        val radioId = when (colorId) {
            R.color.red -> R.id.radio_red
            R.color.orange -> R.id.radio_orange
            R.color.green -> R.id.radio_green
            else -> R.id.radio_yellow
        }
        val radioButton = findViewById<RadioButton>(radioId)
        radioButton.isChecked = true
    }

    override fun onCreateContextMenu(menu: ContextMenu?,
             v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.navigation_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        /* Create option for light/dark mode switch, PizzaParty, LightsOut, and PartyApplication*/
        val id = item.itemId
        if (id == R.id.party_application) {
            val partyIntent = Intent(this, MainActivity::class.java)
            startActivity(partyIntent)
        }
        else if (id == R.id.pizza_party) {
            val pizzaIntent = Intent(this, PizzaPartyController::class.java)
            launchedPizza = true
            startActivity(pizzaIntent)
        }
        else if (id == R.id.lights_out) {
            val lightsIntent = Intent(this, LightsOutController::class.java)
            launchedLights = true
            startActivity(lightsIntent)
        }
        return true
    }

    private fun onColorSelected(view: View) {
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