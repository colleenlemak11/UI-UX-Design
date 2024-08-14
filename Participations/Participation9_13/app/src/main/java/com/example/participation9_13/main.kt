package com.example.participation9_13

// Kotlin objects are references to objects like Java, so we pass a reference of the object to modify values
class PortableInt (intValue : Int = -1) {
    var int : Int = intValue
}

class Bottles(bottlesLeft : Int) {
    var bottles : Int = bottlesLeft
    fun printBottles() {
        if (bottles > 0) {
            println("Number of bottles is $bottles.")
        }
    }
}

fun passByReference(anInt : Int, myMutableList : MutableList<Int>, myInt : PortableInt) {
    myMutableList.add(anInt)
    println("This is a function call and a value $anInt")
    println("My int is ${myInt.int}")
    myInt.int++
    println("My int is ${myInt.int}")
}

fun main() {
    var myInt = 42
    var myMutableList : MutableList<Int> = arrayListOf()
    var myPortableInt = PortableInt(100)

    println("List size is ${myMutableList.size}\n")
    passByReference(7, myMutableList, PortableInt(900))
    println("\nList size is ${myMutableList.size}")
}