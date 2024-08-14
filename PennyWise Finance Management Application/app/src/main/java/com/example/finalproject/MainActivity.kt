package com.example.finalproject
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.math.BigDecimal
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import kotlin.math.round
import kotlin.math.roundToLong

const val EXTRA_DATE = "date"
const val EXTRA_MONEY = "money"
const val EXTRA_CATEGORY = "category"
const val EXTRA_PERSON = "person"
const val EXTRA_PAID = "paid"

class MainActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private var date : String = ""
    private var money : String = ""
    private var category : String = ""
    private var person : String = ""
    private var paid : String = ""
    private var limitAmt : String = ""
    private var credit : Double = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableLayout = findViewById(R.id.tableLayout)

        val showInputDialogButton: Button = findViewById(R.id.showInputDialogButton)
        val addEntry : Button = findViewById(R.id.addEntry)

        Toast.makeText(this, "Easily Manage Finances!\n- CashCows Devs <3", Toast.LENGTH_LONG).show()
        loadDatabaseEntries()
        showInputDialogButton.setOnClickListener {
            showInputDialog()
        }
        addEntry.setOnClickListener {
            addEntry()
        }


    }


    private fun showInputDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.input_dialog, null)
        val limit : EditText = dialogView.findViewById(R.id.limit)
        val curBudget : TextView = findViewById(R.id.cur_budget)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Enter Spending Budget")
            .setPositiveButton("Save Changes") { _, _ ->
                limitAmt = limit.text.toString()
                val number = limitAmt.toDoubleOrNull()
                val spendLimit = findViewById<TextView>(R.id.spending_text)

                if (number != null) {
                    val formattedNum = when {
                        number.toInt().toDouble() == number -> String.format("%.2f", number) // int case
                        String.format("%.1f", number).toDouble() == number ->String.format("%.2f", number) // replace with num to hundreth
                        else -> String.format("%.2f", number) // other decimal
                    }
                    limitAmt = formattedNum
                    spendLimit.text = "Spending Budget: $$limitAmt"

                    val updatedVal = limitAmt.toDouble() - credit.toDouble()
                    val num = when {
                        updatedVal.toInt().toDouble() == updatedVal -> String.format("%.2f", updatedVal) // int case
                        String.format("%.1f", updatedVal).toDouble() == updatedVal ->String.format("%.2f", updatedVal) // replace with num to hundreth
                        else -> String.format("%.2f", updatedVal) // other decimal
                    }
                    curBudget.text = "Current Credit: $num"
                }
                else {
                    Toast.makeText(this,"Please enter a valid numeric budget amount.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    @SuppressLint("Range")
    private fun addNewRow(date: String, money: String, category: String, person: String, paid: String) {
        val newRow = LayoutInflater.from(this).inflate(R.layout.dynamic_view, null) as TableRow
        val dateView: TextView = newRow.findViewById(R.id.dateView)
        val moneyView: TextView = newRow.findViewById(R.id.moneyView)
        val categoryView: TextView = newRow.findViewById(R.id.categoryView)
        val personView: TextView = newRow.findViewById(R.id.personView)
        val paidView: TextView = newRow.findViewById(R.id.paid)
        val deleteButton: Button = newRow.findViewById(R.id.deleteButton)
        val editButton: Button = newRow.findViewById(R.id.editButton)

        dateView.text = date
        moneyView.text = if (money.isNotEmpty()) "$$money" else money
        categoryView.text = category
        personView.text = person
        paidView.text = paid


        deleteButton.setOnClickListener {
            onDeleteButtonClick(newRow)
        }

        editButton.setOnClickListener {
            onEditButtonClick(newRow)
        }
        tableLayout.addView(newRow)
    }

    // When a user adds an entry to the expenses, this callback executes
    private fun addEntry() {
        val addIntent = Intent(this, AddEntry::class.java)
        addIntent.putExtra(EXTRA_DATE, date)
        addIntent.putExtra(EXTRA_MONEY, money)
        addIntent.putExtra(EXTRA_CATEGORY, category)
        addIntent.putExtra(EXTRA_PERSON, person)
        addIntent.putExtra(EXTRA_PAID, paid)
        addLauncher.launch(addIntent)
    }
    private var addLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val db = DBHelper(this, null)
            date = result.data!!.getStringExtra(EXTRA_DATE).toString()
            money = result.data!!.getStringExtra(EXTRA_MONEY).toString()
            category = result.data!!.getStringExtra(EXTRA_CATEGORY).toString()
            person = result.data!!.getStringExtra(EXTRA_PERSON).toString()
            paid = result.data!!.getStringExtra(EXTRA_PAID).toString()
            //Put data into database
            db.addExpense(date, money, category, person, paid)

        }
    }


    private fun chargeCredit(budget : String) {
        val curBudget : TextView = findViewById(R.id.cur_budget)
        var num = "0"
        val number = budget.toDoubleOrNull()
        if (number != null) {
            val formattedNum = when {
                number.toInt().toDouble() == number -> String.format("%.2f", number) // int case
                String.format("%.1f", number).toDouble() == number -> String.format("%.2f", number) // replace with num to hundreth
                else -> String.format("%.2f", number) // other decimal
            }
            if (limitAmt.toDoubleOrNull() != null) {
                credit += formattedNum.toDouble()

                val updatedVal = limitAmt.toDouble() - credit
                curBudget.text = "Current Credit: $$updatedVal"
            }
            else {     // not negative number
                limitAmt = "0.0"
                credit += formattedNum.toDouble()
                val updatedVal = limitAmt.toDouble() - credit
                curBudget.text = "Current Credit: $$updatedVal"
            }
        }
    }

    private fun deleteCredit(budget: String) {
        val curBudget : TextView = findViewById(R.id.cur_budget)
        var number = budget.toDoubleOrNull()
        if (number != null) {
            val formattedNum = when {
                number.toInt().toDouble() == number -> String.format("%.2f", number) // int case
                String.format("%.1f", number).toDouble() == number -> String.format("%.2f", number) // replace with num to hundreth
                else -> String.format("%.2f", number) // other decimal
            }
            credit -= formattedNum.toDouble()
            val updatedVal = limitAmt.toDouble() - credit
            curBudget.text = "Current Credit: $$updatedVal"
        }
    }

    private fun onDeleteButtonClick(row: TableRow) {
        val personView: TextView = row.findViewById(R.id.personView)
        val moneyView: TextView = row.findViewById(R.id.moneyView)

        val db = DBHelper(this, null)
        var tmp_person = personView.text
        var tmp_money = moneyView.text

        tmp_money = tmp_money.replace(Regex("[^\\d.]"), "")
        tmp_person = tmp_person.replace(Regex("[^a-zA-Z]"), "")  // remove $ sign from value
        db.deleteExpenseByPerson(tmp_person.toString())
        db.close()
        deleteCredit(tmp_money.toString())
        tableLayout.removeView(row)
    }


    private fun findRowByDate(date: String): TableRow? {
        for (i in 0 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i) as? TableRow
            val dateView = row?.findViewById<TextView>(R.id.dateView)
            if (dateView?.text == date) {
                return row
            }
        }
        return null
    }

    private fun updateRowData(row: TableRow, date: String, money: String, category: String, person: String, paid: String) {
        val dateView: TextView = row.findViewById(R.id.dateView)
        val moneyView: TextView = row.findViewById(R.id.moneyView)
        val categoryView: TextView = row.findViewById(R.id.categoryView)
        val personView: TextView = row.findViewById(R.id.personView)
        val paidView: TextView = row.findViewById(R.id.paid)

        dateView.text = date
        moneyView.text = if (money.isNotEmpty()) "$$money" else money
        categoryView.text = category
        personView.text = person
        paidView.text = paid
    }

    private var editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val db = DBHelper(this, null)
            val data = result.data
            date = data!!.getStringExtra(EXTRA_DATE).toString()
            money = data.getStringExtra(EXTRA_MONEY).toString()
            category = data.getStringExtra(EXTRA_CATEGORY).toString()
            person = data.getStringExtra(EXTRA_PERSON).toString()
            paid = data.getStringExtra(EXTRA_PAID).toString()

            //db.addExpense(date, money, category, person, paid)
            db.updateExpense(date, money, category, person, paid, mostRecent = true)
        }
    }

    private fun onEditButtonClick(row: TableRow) {
        val dateView: TextView = row.findViewById(R.id.dateView)
        val moneyView: TextView = row.findViewById(R.id.moneyView)
        val categoryView: TextView = row.findViewById(R.id.categoryView)
        val personView: TextView = row.findViewById(R.id.personView)
        val paidView: TextView = row.findViewById(R.id.paid)

        // Create an intent to start the AddEntry activity
        val editIntent = Intent(this, AddEntry::class.java)

        // Pass the existing data to the AddEntry activity
        editIntent.putExtra(EXTRA_DATE, dateView.text.toString())
        editIntent.putExtra(EXTRA_MONEY, moneyView.text.toString().replace(Regex("[^\\d.]"), ""))
        editIntent.putExtra(EXTRA_CATEGORY, categoryView.text.toString())
        editIntent.putExtra(EXTRA_PERSON, personView.text.toString())
        editIntent.putExtra(EXTRA_PAID, paidView.text.toString())

        // Start the AddEntry activity with the edited data
        editLauncher.launch(editIntent)
    }


    override fun onResume() {
        super.onResume()
        loadDatabaseEntries()
    }

    @SuppressLint("Range")
    private fun loadDatabaseEntries() {
        // Clear existing rows in the table layout
        tableLayout.removeAllViews()
        credit = 0.0
        val db = DBHelper(this, null)
        val cursor = db.getName()

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndex(DBHelper.DATE_COL))
                val money = cursor.getString(cursor.getColumnIndex(DBHelper.MONEY_COL))
                val category = cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_COL))
                val person = cursor.getString(cursor.getColumnIndex(DBHelper.PERSON_COL))
                val paid = cursor.getString(cursor.getColumnIndex(DBHelper.PAID_COL))

                addNewRow(date, money, category, person, paid)
                chargeCredit(money)

            } while (cursor.moveToNext())
        }

        cursor?.close()
        db.close()
    }

}