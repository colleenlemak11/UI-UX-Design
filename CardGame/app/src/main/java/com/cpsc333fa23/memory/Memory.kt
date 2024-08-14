package com.cpsc333fa23.memory

import kotlin.random.Random
import androidx.appcompat.app.AppCompatActivity
import com.cpsc333fa23.gc7.R

/* The `Memory` class represents a memory game board with a specified number of tiles and provides
methods for populating the board and printing its current state. */
class Memory (tiles: Int = 6, context: AppCompatActivity) {

    private val defaultBoardValue = context.getString(R.string.default_card_icon)

    var totalNumberOfMatches = tiles / 2

    var matches: MutableMap<Int, Boolean> = mutableMapOf()

    var currentPair: Pair<Int, Int> = Pair(-1, -1)

    var board = MutableList(totalNumberOfMatches * 2) { defaultBoardValue }

    /* The `init` block is a special block in Kotlin that is executed when an instance of the class is
    created. In this case, the `init` block is initializing the game board. */
    init {
        if (tiles % 2 == 1){
            println("Uneven number of tiles, board adjusted to ${totalNumberOfMatches * 2}")
        }
        println("Board initialized for $totalNumberOfMatches matches")

        //fill up and initialize match map
        //scheme: key matches index, value asserts the card at this index has been matched
        //e.g., matches[0] aligns with TextView r1c1 and the value by default is false
        for (i in 0 until (totalNumberOfMatches * 2)){
            matches[i] = false
        }

        var currChar = 'a'

        for (match in 1 .. totalNumberOfMatches){
            println("Populating match $match card \"[$currChar]\"")
            populatePair("[$currChar]", board)
            currChar++
        }
        printBoard(board)
    }

    /**
     * The function populates a pair of cards randomly on a game board.
     *
     * @param card The `card` parameter is a string representing the card that needs to be placed on
     * the board.
     * @param board The `board` parameter is a mutable list of strings that represents the game board.
     * Each element in the list represents a position on the board. The `card` parameter is a string
     * that represents the card to be placed on the board.
     */
    private fun populatePair(card: String, board: MutableList<String>){
        var placementsLeft = 2
        while (placementsLeft > 0){
            val index = Random.nextInt(0, board.size)

            if (board[index] != defaultBoardValue){ //try again
                continue
            }
            else {
                board[index] = card
                placementsLeft--
            }
        }
    }

    /**
     * The function `printBoard` prints the current state of the board, with each card on a new line.
     *
     * @param board A MutableList of Strings representing the current state of the game board. Each
     * element in the list represents a card on the board.
     */
    private fun printBoard(board: MutableList<String>){
        println("Current Board (uncovered)")
        for (card in board){
            println(card)
        }
    }
}
