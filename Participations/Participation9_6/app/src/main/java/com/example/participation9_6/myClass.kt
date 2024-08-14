/*
package com.example.participation9_6

class myClass {
}*/

// Learned today:
// Variable conventions: camel case, cannot begin with num or symbol
// Val is unchangeable and var is mutable
// $ accesses variables and can print results within {} operation

fun main() {
    var someVal : Int = 40 // declare variable of type int
    val maxScore = 9000
    someVal = 42 // changeable because keyword 'var'
    // maxScore = 10 -> error because keyword 'val'
    println("Hello there!")
    println("Your value is $someVal and your max score is ${maxScore + 300}")

    var myArray : Array<Int> = arrayOf(1, 42, 312, 60)
    for (item in myArray) {
        println("Item is $item")
    }

    // Bottles on the wall activity
    println("\nWelcome to Bottle Counter!\nHow many bottles would you like to start with?")
    val strBottles = readLine()
    var numBottles = strBottles?.toInt()

    println("Perfect, let's count them. Select method to count:\n1. While loop\n2. For loop\n3. Do-while\n4. Count backwards")
    val userChoice = readLine()
    var numChoice = userChoice?.toInt()

    // options and loops to choose
    if (numChoice == 1) {
        while (numBottles!! > 0) {
            println("$numBottles of water on the wall, take one down pass it around")
            numBottles -= 1
            println("$numBottles of water on the wall")
        }
    }


}