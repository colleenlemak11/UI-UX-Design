package com.example.participation9_11

/*
   Android Studio provides easy UI for files synced to Github and
   Kotlin provides built-in functions to loop up/down
*/

fun myFun(lengthOfArray : Int, someWeirdString : String) : String {
    println("Cool: $lengthOfArray" +
            " Weird: $someWeirdString")
    return "\ngoodbye.\n"
}

fun numberGame() {
    var favNum : String = ""
    val maxScore = 7000
    var myString = myFun(7001, "word")

    println("Enter your favorite number:")
    favNum = readln()

    var someResult = when (favNum) {
        "42" -> {"The best number, 42"}
        else -> {"Nice number."}
    }
    println(someResult)
}

fun bottleGame() {
    println("\nWelcome to Bottle Counter!\nHow many bottles would you like to start with?")
    val strBottles = readln()
    var userBottles = strBottles?.toInt()

    println("Okay, your bottle limit is $userBottles.")
    println("Now, let's count them. Select method to count:\n1. While loop\n2. For loop\n3. Do-while\n4. Count upwards")

    val userChoice = readln().toInt()

    println("Enter the number by which you would like to step by (default is 1).")
    var numSteps = readln().toInt()

    if (numSteps == 0 || numSteps >= userBottles!!) {
        println("Invalid number, step count set to 1.")
        numSteps = 1
    }

    var message = when (userChoice) {
        1 -> {
            while (userBottles!! > 0) {
                println("$userBottles bottles of water on the wall, $userBottles bottles of water.\nTake $numSteps down, pass it around,")
                userBottles -= numSteps
                if (userBottles <= 0) {
                    println("0 bottles of water on the wall.")
                }
                else if (userBottles > 0) {
                    println("$userBottles bottles of water on the wall.")
                }
            }
        }
        2 -> {
            for (item in userBottles!! downTo 1 step(numSteps)) {
                println("$userBottles bottles of water on the wall, $userBottles bottles of water.\nTake $numSteps down, pass it around,")
                userBottles -= numSteps
                if (userBottles <= 0) {
                    println("0 bottles of water on the wall.")
                }
                else if (userBottles > 0) {
                    println("$userBottles bottles of water on the wall.")
                }
            }
        }
        3 -> {
            do {
                println("$userBottles bottles of water on the wall, $userBottles bottles of water.\nTake $numSteps down, pass it around,")
                userBottles = userBottles?.minus(numSteps!!)
                if (userBottles!! <= 0) {
                    println("0 bottles of water on the wall.")
                }
                else if (userBottles!! > 0) {
                    println("$userBottles bottles of water on the wall.")
                }
            } while (userBottles!! > 0)
        }
        4 -> {
            for (item in (0..userBottles!!) step(numSteps)) {
                println("$item bottles of water on the wall, $item bottles of water.\nPut $numSteps up, place it round,")
                if (item!! >= userBottles!!) {
                    println("$userBottles bottles of water on the wall.")
                }
                else if (item!! < userBottles) {
                    println("$item bottles of water on the wall.")
                }
            }
        }
        else -> { println("$userChoice is not a menu option.") }
    }
    println("\nHope you enjoyed the Bottle Counter!\n")
}

fun main() {
    bottleGame()
}