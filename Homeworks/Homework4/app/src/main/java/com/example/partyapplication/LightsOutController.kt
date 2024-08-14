package com.example.partyapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.ContextCompat
import androidx.core.view.children
private var myTheme : Int = R.style.Theme_LightsOut_Light
private var isDark : Boolean = false

class LightsOutController : AppCompatActivity() {
    private lateinit var game: LightsOutModel
    private lateinit var lightGridLayout: GridLayout
    private lateinit var newGameButton : Button
    private lateinit var helpButton : Button
    private lateinit var colorButton : Button
    private var lightOnColorId = 0
    private var lightOnColor = 0
    private var lightOffColor = 0
    private lateinit var activityLights : ConstraintLayout
    private var numWins : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(myTheme)
        setContentView(R.layout.activity_lights_out)

        lightGridLayout = findViewById(R.id.light_grid)
        newGameButton = findViewById(R.id.new_game_button)
        helpButton = findViewById(R.id.help_button)
        colorButton = findViewById(R.id.change_color_button)
        activityLights = findViewById(R.id.activity_lights_out)
        lightOnColorId = R.color.yellow
        registerForContextMenu(activityLights)

        newGameButton.setOnClickListener() {
            startGame()
        }
        helpButton.setOnClickListener() {
            onHelpClick(helpButton)
        }
        colorButton.setOnClickListener() {
            onChangeColorClick(colorButton)
        }

        // Add the same click handler to all grid buttons
        for (gridButton in lightGridLayout.children) {
            gridButton.setOnClickListener(this::onLightButtonClick)
        }

        lightOnColor = ContextCompat.getColor(this, R.color.yellow)
        lightOffColor = ContextCompat.getColor(this, R.color.black)

        game = LightsOutModel()
        startGame()
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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.party_application) {
            val dataIntent = Intent()
            dataIntent.putExtra(EXTRA_WINS, numWins)
            setResult(RESULT_OK, dataIntent)
            finish()
        }
        else if (id == R.id.pizza_party) {
            val pizzaIntent = Intent(this, PizzaPartyController::class.java)
            val dataIntent = Intent()
            dataIntent.putExtra(EXTRA_WINS, numWins)
            setResult(RESULT_OK, dataIntent)
            finish()
            launchedPizza = true
            startActivity(pizzaIntent)
        }
        else if (id == R.id.lights_out) {
            launchedLights = true
            startGame()
        }
        else if (id == R.id.lightTheme) {
            myTheme = R.style.Theme_LightsOut_Light
            setTheme(myTheme)
            recreate()
            isDark = false
        }
        else if (id == R.id.darkTheme) {
            myTheme = R.style.Theme_LightsOut_Dark
            setTheme(myTheme)
            recreate()
            isDark = true
        }
        return true
    }

    private fun startGame() {
        game.newGame()
        setButtonColors()
    }

    private fun onLightButtonClick(view: View) {
        // Find the button's row and col
        val buttonIndex = lightGridLayout.indexOfChild(view)
        val row = buttonIndex / GRID_SIZE
        val col = buttonIndex % GRID_SIZE

        game.selectLight(row, col)
        setButtonColors()

        // Congratulate the user if the game is over
        if (game.isGameOver) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show()
            numWins += 1
        }
    }

    private fun setButtonColors() {
        // Set all buttons' background color
        for (buttonIndex in 0 until lightGridLayout.childCount) {
            val gridButton = lightGridLayout.getChildAt(buttonIndex)

            // Find the button's row and col
            val row = buttonIndex / GRID_SIZE
            val col = buttonIndex % GRID_SIZE

            if (game.isLightOn(row, col)) {
                gridButton.setBackgroundColor(lightOnColor)
            } else {
                gridButton.setBackgroundColor(lightOffColor)
            }
        }
    }

    private fun onHelpClick(view: View) {
        val intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
    }

    private fun onChangeColorClick(view: View) {
        val intent = Intent(this, ColorActivity::class.java)
        intent.putExtra(EXTRA_COLOR, lightOnColorId)
        colorResultLauncher.launch(intent)
    }
    private val colorResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            lightOnColorId = result.data!!.getIntExtra(EXTRA_COLOR, R.color.yellow)
            lightOnColor = ContextCompat.getColor(this, lightOnColorId)
            setButtonColors()
        }
    }
}