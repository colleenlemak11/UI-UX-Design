package com.example.partyapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

class HelpActivity : AppCompatActivity() {
    private lateinit var activityHelp : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        activityHelp = findViewById(R.id.activity_help)
        registerForContextMenu(activityHelp)
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
}