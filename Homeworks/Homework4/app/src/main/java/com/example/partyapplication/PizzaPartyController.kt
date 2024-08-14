package com.example.partyapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

const val SLICES_PER_PIZZA = 8
private var selectedTheme : Int = R.style.Theme_PizzaParty_Light
private var isDark : Boolean = false

class PizzaPartyController : AppCompatActivity() {
    private lateinit var numAttendEditText: EditText
    private lateinit var numPizzasTextView: TextView
    private lateinit var spinner : Spinner
    private lateinit var calcButton : Button
    private lateinit var activityPizza : LinearLayout
    private lateinit var hungerLevel : PizzaParty.HungerLevel
    private var totalPizzas : Int = 0
    private var numPeople : Int = 0
    var item : String = "Light"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(selectedTheme)
        setContentView(R.layout.activity_pizza_party)
        numAttendEditText = findViewById(R.id.num_attend_edit_text)
        numPizzasTextView = findViewById(R.id.num_pizzas_text_view)

        spinner = findViewById(R.id.spinner_size)
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.sizes_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                item = parent?.getItemAtPosition(position) as String
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        calcButton = findViewById(R.id.calc_button)
        activityPizza = findViewById(R.id.activity_pizza_party)
        registerForContextMenu(activityPizza)

        calcButton.setOnClickListener() {
            calculateClick(calcButton)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?,
            v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.navigation_menu, menu)
        val darkTheme : MenuItem? = menu?.findItem(R.id.darkTheme)
        val lightTheme : MenuItem? = menu?.findItem(R.id.lightTheme)

        if (!isDark) {
            darkTheme?.isVisible = true // if light, show dark theme
            lightTheme?.isVisible = false
        }
        else {
            darkTheme?.isVisible = false
            lightTheme?.isVisible = true // if dark, show light theme
        }
    }

    private fun calculateClick(view: View) {
        val numAttendStr = numAttendEditText.text.toString()
        numPeople = numAttendStr.toInt()
        val numAttend = numAttendStr.toIntOrNull() ?: 0
        hungerLevel = when(item) {
            "Light" -> PizzaParty.HungerLevel.LIGHT
            "Medium" -> PizzaParty.HungerLevel.MEDIUM
            else -> PizzaParty.HungerLevel.RAVENOUS
        }
        val slicesPerPerson = when (hungerLevel) {
            PizzaParty.HungerLevel.LIGHT -> 2
            PizzaParty.HungerLevel.MEDIUM -> 3
            else -> 4
        }

        // Get the number of pizzas needed
        val calc = PizzaParty(numAttend, hungerLevel)
        totalPizzas = calc.totalPizzas

        // Place totalPizzas into the string resource and display
        val totalText = getString(R.string.total_pizzas_num, totalPizzas)
        numPizzasTextView.text = totalText

        // Send data to MainActivity to be sent in a text
        val dataIntent = Intent()
        /*Toast.makeText(this@PizzaPartyController, "ppl:$numPeople, pzza:$totalPizzas, HL:${hungerLevel.toString()}",Toast.LENGTH_LONG).show()*/
        dataIntent.putExtra(EXTRA_PEOPLE, numPeople)
        dataIntent.putExtra(EXTRA_PIZZA, totalPizzas)
        dataIntent.putExtra(EXTRA_HUNGER, hungerLevel.toString())
        setResult(RESULT_OK, dataIntent)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.party_application) {
            // Send data to MainActivity to be sent in a text
            try {
                val dataIntent = Intent()
                dataIntent.putExtra(EXTRA_PEOPLE, numPeople)
                dataIntent.putExtra(EXTRA_PIZZA, totalPizzas)
                dataIntent.putExtra(EXTRA_HUNGER, hungerLevel.toString())
                setResult(RESULT_OK, dataIntent)
                finish()
            } catch (e:Exception) {
                Toast.makeText(this, "Launching Lights Out! (without Pizza Stats)", Toast.LENGTH_SHORT).show()
            }
        }
        else if (id == R.id.pizza_party) {
            // Send data to MainActivity to be sent in a text
            Toast.makeText(this, "Already in PizzaParty!", Toast.LENGTH_SHORT).show()
            launchedPizza = true
        }
        else if (id == R.id.lights_out) {
            // Send data to MainActivity to be sent in a text
            try {
                val dataIntent = Intent()
                dataIntent.putExtra(EXTRA_PEOPLE, numPeople)
                dataIntent.putExtra(EXTRA_PIZZA, totalPizzas)
                dataIntent.putExtra(EXTRA_HUNGER, hungerLevel.toString())
                setResult(RESULT_OK, dataIntent)
                finish()
            } catch (e:Exception) {
                Toast.makeText(this, "Could not send Pizza Stats to Lights Out", Toast.LENGTH_SHORT).show()
            }
            val lightsOut = Intent(this, LightsOutController::class.java)
            startActivity(lightsOut)
            launchedLights = true
        }
        else if (id == R.id.lightTheme) {
            selectedTheme = R.style.Theme_PizzaParty_Light
            setTheme(selectedTheme)
            recreate()
            isDark = false
        }
        else if (id == R.id.darkTheme) {
            selectedTheme = R.style.Theme_PizzaParty_Dark
            setTheme(selectedTheme)
            recreate()
            isDark = true
        }
        return true
    }
}