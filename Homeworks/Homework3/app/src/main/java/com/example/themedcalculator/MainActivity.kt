package com.example.themedcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.Exception

/*
* Colleen Lemak
* CPSC 333
* Homework 3 - Themed Calculator
* Operations: +,-,*,/,=,x^2,%
* All operations take two numeric arguments except for x^2 which squares one argument after '='.
* Implements multiple .xml files to design UI and functional calculator.
* Uses try-catch blocks for exception-handling.
*/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }

    fun operation(num1: Int, num2: Int, op: String): Int {
        val opResult = when (op) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            "square" -> num1 * num1
            "mod" -> num1 % num2
            else -> throw Exception("Operation is not within scope bounds.")
        }
        return opResult
    }

    private fun main() {
        // initialize all views in main
        val button0 = findViewById<Button>(R.id.zero)
        val button1 = findViewById<Button>(R.id.one)
        val button2 = findViewById<Button>(R.id.two)
        val button3 = findViewById<Button>(R.id.three)
        val button4 = findViewById<Button>(R.id.four)
        val button5 = findViewById<Button>(R.id.five)
        val button6 = findViewById<Button>(R.id.six)
        val button7 = findViewById<Button>(R.id.seven)
        val button8 = findViewById<Button>(R.id.eight)
        val button9 = findViewById<Button>(R.id.nine)
        val buttonAdd = findViewById<Button>(R.id.add)
        val buttonMinus = findViewById<Button>(R.id.minus)
        val buttonDivide = findViewById<Button>(R.id.divide)
        val buttonMultiply = findViewById<Button>(R.id.multiply)
        val buttonDelete = findViewById<Button>(R.id.delete)
        val buttonEquals = findViewById<Button>(R.id.equals)
        val buttonTilde1 = findViewById<Button>(R.id.tilde1)
        val buttonTilde2 = findViewById<Button>(R.id.tilde2)
        val editBox = findViewById<TextView>(R.id.screen) as TextView
        var inputStr = ""
        var firstNum = 0
        var secondNum = 0
        var op = ""

        /* Create listeners for all buttons to detect inputs/outputs */
        button0.setOnClickListener() {
            if (editBox.text.length == 1 && inputStr == "0") {
                inputStr = "0"
            } else {
                inputStr += "0"
                editBox.text = inputStr
            }
        }
        button1.setOnClickListener() {
            inputStr += "1"
            editBox.text = inputStr
        }
        button2.setOnClickListener() {
            inputStr += "2"
            editBox.text = inputStr
        }
        button3.setOnClickListener() {
            inputStr += "3"
            editBox.text = inputStr
        }
        button4.setOnClickListener() {
            inputStr += "4"
            editBox.text = inputStr
        }
        button5.setOnClickListener() {
            inputStr += "5"
            editBox.text = inputStr
        }
        button6.setOnClickListener() {
            inputStr += "6"
            editBox.text = inputStr
        }
        button7.setOnClickListener() {
            inputStr += "7"
            editBox.text = inputStr
        }
        button8.setOnClickListener() {
            inputStr += "8"
            editBox.text = inputStr
        }
        button9.setOnClickListener() {
            inputStr += "9"
            editBox.text = inputStr
        }
        buttonAdd.setOnClickListener() {
            try {
                firstNum = inputStr.toInt()
            } catch (e: Exception) {
                throw Exception("First numerical argument is not an integer.")
            }
            inputStr = ""
            op = "+"
        }
        buttonMinus.setOnClickListener() {
            try {
                firstNum = inputStr.toInt()
            } catch (e: Exception) {
                throw Exception("First numerical argument is not an integer.")
            }
            inputStr = ""
            op = "-"
        }
        buttonDivide.setOnClickListener() {
            try {
                firstNum = inputStr.toInt()
            } catch (e: Exception) {
                throw Exception("First numerical argument is not an integer.")
            }
            inputStr = ""
            op = "/"
        }
        buttonMultiply.setOnClickListener() {
            try {
                firstNum = inputStr.toInt()
            } catch (e: Exception) {
                throw Exception("First numerical argument is not an integer.")
            }
            inputStr = ""
            op = "*"
        }
        buttonDelete.setOnClickListener() {
            if (inputStr.isNotBlank() && inputStr != "0") {
                if (editBox.text.length == 1) {
                    editBox.text = "0"
                    inputStr = ""
                } else {
                    editBox.text = editBox.text.substring(0, (editBox.text.length) - 1)
                    inputStr = editBox.text.toString()
                }
            }
        }
        buttonTilde1.setOnClickListener() {
            try {
                firstNum = inputStr.toInt()
            } catch (e: Exception) {
                throw Exception("First numerical argument is not an integer.")
            }
            inputStr = ""
            op = "square"
        }
        buttonTilde2.setOnClickListener() {
            try {
                firstNum = inputStr.toInt()
            } catch (e: Exception) {
                throw Exception("First numerical argument is not an integer.")
            }
            inputStr = ""
            op = "mod"
        }
        buttonEquals.setOnClickListener() {
            if (op != "square") {
                secondNum = inputStr.toInt()
            }
            val result = operation(firstNum, secondNum, op)
            try {
                inputStr = result.toString()
            } catch (e: Exception) {
                throw Exception("Calculation does not yield proper output.")
            }
            editBox.text = inputStr
        }
    }
}