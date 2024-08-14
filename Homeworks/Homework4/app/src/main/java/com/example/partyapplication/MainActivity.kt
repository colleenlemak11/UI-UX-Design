package com.example.partyapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.w3c.dom.Text

const val EXTRA_PIZZA = "numPizzas"
const val EXTRA_HUNGER = "hungerLevel"
const val EXTRA_WINS = "numWins"
const val EXTRA_MSG = "sendMsg"
const val EXTRA_SENDTO = "phoneNum"
const val EXTRA_PEOPLE = "numPeople"
var launchedPizza : Boolean = false
var launchedLights : Boolean = false

class MainActivity : AppCompatActivity() {
    private lateinit var pizzaParty: Button
    private lateinit var lightsOut: Button
    private lateinit var sendSMS : Button
    private lateinit var activityMain : RelativeLayout
    private var numPizzas : Int = 0
    private var numPeople : Int = 0
    private var hungerLevel : String = ""
    private var numWins : Int = 0
    private var message : String = ""
    private var phoneNum : String = ""
    private lateinit var smsManager: SmsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pizzaParty = findViewById(R.id.pizza_party)
        lightsOut = findViewById(R.id.lights_out)
        sendSMS = findViewById(R.id.send_sms)
        activityMain = findViewById(R.id.activity_main)
        registerForContextMenu(activityMain)
        main()
    }

    override fun onCreateContextMenu(menu: ContextMenu?,
        v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.navigation_menu, menu)
        val darkTheme : MenuItem? = menu?.findItem(R.id.darkTheme)
        val lightTheme : MenuItem? = menu?.findItem(R.id.lightTheme)
        
        darkTheme?.isVisible = false // disable theme switching on landing page
        lightTheme?.isVisible = false
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.party_application) {
            Toast.makeText(applicationContext,"Already in Party App", Toast.LENGTH_SHORT).show()
        }
        else if (id == R.id.pizza_party) {
            launchedPizza = true
            pizzaResults()
        }
        else if (id == R.id.lights_out) {
            launchedLights = true
            lightsResults()
        }
        return true
    }

    private fun pizzaResults() {
        val pizzaPartyIntent = Intent(this, PizzaPartyController::class.java)
        pizzaPartyIntent.putExtra(EXTRA_PIZZA, numPizzas)
        pizzaPartyIntent.putExtra(EXTRA_PEOPLE, numPeople)
        pizzaPartyIntent.putExtra(EXTRA_HUNGER, hungerLevel)
        pizzaLauncher.launch(pizzaPartyIntent)
    }
    private val pizzaLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Grab pizza app inputs
            numPizzas = result.data!!.getIntExtra(EXTRA_PIZZA, numPizzas)
            numPeople = result.data!!.getIntExtra(EXTRA_PEOPLE, numPeople)
            hungerLevel = result.data!!.getStringExtra(EXTRA_HUNGER).toString()
        }
    }

    private fun lightsResults() {
        val lightsOutIntent = Intent(this, LightsOutController::class.java)
        lightsOutIntent.putExtra(EXTRA_WINS, 0)
        lightsLauncher.launch(lightsOutIntent)
    }
    private val lightsLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Grab number of games won result
            numWins = result.data!!.getIntExtra(EXTRA_WINS, 0)
        }
    }

    private fun sendSMSResults() {
        val sendSMSIntent = Intent(this, SendTextController::class.java)
        sendSMSIntent.putExtra(EXTRA_MSG, message)
        sendSMSIntent.putExtra(EXTRA_SENDTO, phoneNum)
        sendSMSLauncher.launch(sendSMSIntent)
    }
    private var sendSMSLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Grab message from user
            message = result.data!!.getStringExtra(EXTRA_MSG).toString()
            phoneNum = result.data!!.getStringExtra(EXTRA_SENDTO).toString()
            Toast.makeText(applicationContext, "Pizza: $launchedPizza Lights: $launchedLights", Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "People:$numPeople Hunger:$hungerLevel Pizzas:$numPizzas Wins:$numWins", Toast.LENGTH_LONG).show()

            if (launchedPizza and launchedLights) {
                message += "Thanks for interacting with the PizzaParty and LightsOut games!\nHere are your stats:\nPizzaParty:\nGroup of $numPeople\nHunger Level: $hungerLevel\nNumber of Pizzas: $numPizzas\n" +
                        "\n\nLightsOut:\nNumber of Wins: ${numWins}\n"
            }
            else if (launchedPizza and !launchedLights) {
                message += "\n\nThanks for interacting with the PizzaParty game!\n\nHere are your stats:\nPizzaParty:\nGroup of $numPeople\nHunger Level: $hungerLevel\nNumber of Pizzas: $numPizzas\n"
            }
            else if (!launchedPizza and launchedLights) {
                message += "\n\nThanks for interacting with the LightsOut game!\n\nHere are your stats:\nLightsOut:\nNumber of Wins: ${numWins}\n"
            }
            else {
                message += "\n\nWe're glad you launched the Party App!\nPlay PizzaParty or LightsOut to receive statistics :)"
            }
            try {
                smsManager = this.getSystemService(SmsManager::class.java)
               /* smsManager = SmsManager.getDefault()*/
                smsManager.sendTextMessage(phoneNum, null, message, null, null)
                Toast.makeText(applicationContext, "Sent msg: $message to $phoneNum", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Error sending message", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun main() {
        pizzaParty.setOnClickListener() {
            Toast.makeText(this, "Launching Pizza Party!", Toast.LENGTH_SHORT).show()
            launchedPizza = true
            pizzaResults()
        }
        lightsOut.setOnClickListener() {
            Toast.makeText(this, "Launching Lights Out!", Toast.LENGTH_SHORT).show()
            launchedLights = true
            lightsResults()
        }
        sendSMS.setOnClickListener() {
            sendSMSResults()
        }
    }
}