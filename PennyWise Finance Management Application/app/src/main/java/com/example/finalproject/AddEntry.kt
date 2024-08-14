package com.example.finalproject

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class AddEntry : AppCompatActivity() {
    private lateinit var addButton: Button
    private lateinit var amountText: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var friendText: EditText
    private lateinit var entertainment: RadioButton
    private lateinit var food: RadioButton
    private lateinit var shopping: RadioButton
    private lateinit var yesRadio: RadioButton
    private lateinit var noRadio: RadioButton

    // New variables to store preloaded data
    private var preloadedDate: String = ""
    private var preloadedMoney: String = ""
    private var preloadedCategory: String = ""
    private var preloadedPerson: String = ""
    private var preloadedPaid: String = ""

    private var monthSelected: Int = 0
    private var daySelected: Int = 0
    private var yearSelected: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_entry_layout)

        addButton = findViewById(R.id.addButton)
        amountText = findViewById(R.id.amountEditText)
        datePicker = findViewById(R.id.datePicker)
        friendText = findViewById(R.id.friendEditText)
        entertainment = findViewById(R.id.radioEntertainment)
        food = findViewById(R.id.radioFood)
        shopping = findViewById(R.id.radioShopping)
        yesRadio = findViewById(R.id.yes)
        noRadio = findViewById(R.id.no)

        // Retrieve preloaded data from the Intent
        val intent = intent
        preloadedDate = intent.getStringExtra(EXTRA_DATE).toString()
        preloadedMoney = intent.getStringExtra(EXTRA_MONEY).toString()
        preloadedCategory = intent.getStringExtra(EXTRA_CATEGORY).toString()
        preloadedPerson = intent.getStringExtra(EXTRA_PERSON).toString()
        preloadedPaid = intent.getStringExtra(EXTRA_PAID).toString()

        // Prepopulate the UI elements with preloaded data
        amountText.setText(preloadedMoney)

        // Parse the date and set it in the DatePicker
        val dateComponents = preloadedDate.split("/")
        if (dateComponents.size == 3) {
            yearSelected = dateComponents[2].toInt()
            monthSelected = dateComponents[0].toInt() - 1
            daySelected = dateComponents[1].toInt()
            datePicker.updateDate(yearSelected, monthSelected, daySelected)
        }

        friendText.setText(preloadedPerson)

        when (preloadedCategory) {
            "Entertainment" -> entertainment.isChecked = true
            "Food" -> food.isChecked = true
            "Shopping" -> shopping.isChecked = true
        }

        when (preloadedPaid) {
            "Y" -> yesRadio.isChecked = true
            else -> noRadio.isChecked = true
        }

        // Handle the button click event
        addButton.setOnClickListener {
            // Retrieve data from UI elements
            val money = amountText.text.toString()
            val category = when {
                entertainment.isChecked -> "Entertainment"
                food.isChecked -> "Food"
                shopping.isChecked -> "Shopping"
                else -> "Other"
            }
            val person = friendText.text.toString()
            val paid = when {
                yesRadio.isChecked -> "Y"
                else -> "N"
            }

            // Set the result and finish the activity
            val data = Intent()
            data.putExtra(EXTRA_DATE, preloadedDate)
            data.putExtra(EXTRA_MONEY, money)
            data.putExtra(EXTRA_CATEGORY, category)
            data.putExtra(EXTRA_PERSON, person)
            data.putExtra(EXTRA_PAID, paid)

            // Send the result back to the calling activity
            setResult(RESULT_OK, data)
            finish()
        }
    }
}
