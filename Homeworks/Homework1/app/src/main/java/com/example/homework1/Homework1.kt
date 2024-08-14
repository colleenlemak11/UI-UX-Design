/*
Colleen Lemak
Professor Olivares
CPSC 333
Homework1: Random Number Ranking Game
This program uses a mutable map to store and rank user guesses
 */

// Attempting extra credit for:
// 1. Asking user if they want to play again (after inputting 'q')
// 2. Customizing new range of numeric inputs (prompts user for values)
// 3. Detect loss condition using ranked numbers

package com.example.homework1
import kotlin.system.exitProcess

fun rank(rankInfo : MutableMap<Int, String>, randomNums : List<Int>, curIndex : Int) {
    var spotAvailable : String
    var userRank : String = ""
    var playerTurn : Boolean = true
    var playAgain : String = ""

    while (playerTurn) {
        println("Choose a ranking (1 to 10) or 'q' to quit:")
        userRank = readln()
        if (userRank == "q") {
            println("Sad to see you go, would you like to play again? (y/n)")
            playAgain = readln()
            if (playAgain == "y" || playAgain == "Y") {
                main()
            }
            else {
                println("Cool, you will be exited out of the game...")
                exitProcess(0)
            }
        }
        else {
            var userNum = userRank.toIntOrNull()

            if (userNum != null) {
                if (userNum in 1..10) {
                    spotAvailable = ""
                    spotAvailable = rankInfo[userNum].toString()

                    if (spotAvailable == "-") {
                        rankInfo[userNum] = randomNums[curIndex].toString()
                        println("Great. ${randomNums[curIndex]} is placed in rank $userRank.\n")
                        playerTurn = false
                    } else {
                        println("Sorry, $userNum is an occupied rank index.")
                        playerTurn = true
                    }
                }
                else {
                    println("Try again, $userNum is an invalid rank index.")
                    playerTurn = true
                }
            }
        }
    }
}

fun printRanks(rankInfo : MutableMap<Int, String>, numPlacements : Int, randomNums : List<Int>, curIndex : Int) {
    println("Remaining placements: $numPlacements")
    println("Generated number: ${randomNums[curIndex]}")
    for (index in 1..10) {
        println("Rank ${index}:  ${rankInfo[index]}")
    }
}

fun checkGameOver(rankInfo: MutableMap<Int, String>) : Boolean {
    var emptySpot = 0
    var curNum : String = ""
    var lastNum : Int = -1

    for (numRank in 1..10) {
        curNum = rankInfo[numRank].toString()

        if (curNum == "-") {
            emptySpot += 1
        }
        if (emptySpot == 9) {
            return false
        }
        if (curNum != "-") {
            if (lastNum == -1) { // 1st number encountered
                lastNum = curNum.toInt()
            }
            else {
                // 2nd number encountered
                if (lastNum > curNum.toInt()) {
                    // out of order
                    return true
                }
                lastNum = curNum.toInt()
            }
        }

    }
    return false
}

fun main() {
    // initialize rank information map
    val rankInfo = mutableMapOf(1 to "-",
                                2 to "-",
                                3 to "-",
                                4 to "-",
                                5 to "-",
                                6 to "-",
                                7 to "-",
                                8 to "-",
                                9 to "-",
                                10 to "-")
    var randomNums : List<Int>
    var numPlacements : Int
    var result : Boolean
    var lowerLimit : Int
    var upperLimit : Int

    println("Welcome to the Random Number Ranking Game!\nRules:\n" +
            "\t1.  Your goal is to place 10 randomly generated numbers from lowest (#1 spot) to highest number, (#10 spot).\n" +
            "\t2.  You must rank each number in the order it is generated.\n" +
            "\t3.  Numbers cannot be changed after being placed in rank.\n" +
            "\tThe game is over when either:\n\t\ta) 10 randomly generated numbers have been correctly placed\n\t\tOR\n\t\tb) there are no more valid ranks to place the last recently generated number.\n")

    println("First, enter the lower limit integer for the numbers to be generated.")
    lowerLimit = readln().toInt()
    println("Now, enter the upper limit integer for the numbers to be generated.")
    upperLimit = readln().toInt()
    println("Okay. Numbers will be generated from $lowerLimit to $upperLimit")

    randomNums = List(10) { (lowerLimit..upperLimit).random() }

    for (curIndex : Int in 0..9 step 1) {
        numPlacements = (10 - curIndex)
        printRanks(rankInfo, numPlacements, randomNums, curIndex)
        rank(rankInfo, randomNums, curIndex)

        result = checkGameOver(rankInfo)
        if (result) {
            println("Ranked numbers incorrectly, you lose.")
            exitProcess(0)
        }
        else if (!result && numPlacements == 0) {
            println("You placed all numbers correctly, you win!")
            exitProcess(0)
        }
    }
}