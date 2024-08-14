package com.example.participation9_8
/*
class test {
}*/

// Kotlin uses when statements similar to case or switch statements.

fun main() {
    var favNum : String
    println("Enter your favorite number:")
    favNum = readln()
    println("Your favorite number is $favNum")
    var someResult = when (favNum) {
        "42" -> "The best number, 42"
        else -> "Nice number."
    }
    println(someResult)
}

