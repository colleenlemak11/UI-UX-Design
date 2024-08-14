package com.example.rockpaperscissors.main
import com.example.rockpaperscissors.fungamesINC.*
import kotlin.random.Random
import kotlin.system.exitProcess

/*
Colleen Lemak
HW2 - Rock Paper Scissors
Attempting both extra credit implementations (package breakdown and randomized message choices)
*/

class GamePlayer(private var playerName : String) {
    // Properties: name, wins, losses, ties, totalGamesPlayed
    // initializes values and defines getter and setter functions for class properties
    var name : String = ""
        set(value) {
            field = playerName
            println("Sweet. Welcome, $playerName -- I'm glad you're here!")
        }
        get() {
            return playerName
        }
    var wins : Int = 0
        set(value) {
            if (value > 0) {
                field = value
            }
        }
        get() {
            return field
        }
    var losses : Int = 0
        set(value) {
            if (value > 0) {
                field = value
            }
        }
        get() {
            return field
        }
    var ties : Int = 0
        set(value) {
            if (value > 0) {
                field = value
            }
        }
        get() {
            return field
        }
    var totalGamesPlayed : Int = 0
        set(value) {
            if (value > 0) {
                field = value
            }
        }
        get() {
            return field
        }
    var winLossRatio : Double = 0.0
        set(value) {
            if (value > 0) {
                field = value
            }
        }
        get() {
            return field
        }

    fun chooseHand(letter : Char) : Hands {
        var isValid : Boolean = false
        var hand : Hands = Hands.ROCK
        if (letter == 'r' || letter == 'R') {
            hand = Hands.ROCK
        }
        else if (letter == 'p' || letter == 'P') {
            hand = Hands.PAPER
        }
        else if (letter == 's' || letter == 'S') {
            hand = Hands.SCISSORS
        }
        else {
            // utilize a try-catch statement to verify user input
            try {
                var answer : Char
                while (!isValid) {
                    println("Invalid hand choice, try again. Rock ('r'), Paper ('p'), or Scissors ('s')?")
                    answer = readln().single()
                    if (answer == 'r' || answer == 'R') {
                        hand = Hands.ROCK
                        isValid = true
                    }
                    else if (answer == 'p' || answer == 'P') {
                        hand = Hands.PAPER
                        isValid = true
                    }
                    else if (answer == 's' || answer == 'S') {
                        hand = Hands.SCISSORS
                        isValid = true
                    }
                    else {
                        isValid = false
                    }
                }
            } catch (e: Exception) {
                println("Could not correctly execute chooseHand() function.")
            }
        }
        return hand
    }

    class Game() {
        fun rockPaperScissors() : Hands {
            val rps = listOf(Hands.ROCK, Hands.PAPER, Hands.SCISSORS)
            val randNum = Random.nextInt(0,2)
            return rps[randNum]
        }
        fun getGameResult(choice1 : Hands, choice2 : Hands) : RoundResult {
            val result = when(choice1 to choice2) {
                Hands.ROCK to Hands.PAPER -> RoundResult.LOSS
                Hands.ROCK to Hands.SCISSORS -> RoundResult.WIN
                Hands.PAPER to Hands.SCISSORS -> RoundResult.LOSS
                Hands.PAPER to Hands.ROCK -> RoundResult.WIN
                Hands.SCISSORS to Hands.ROCK -> RoundResult.LOSS
                Hands.SCISSORS to Hands.PAPER -> RoundResult.WIN
                else -> RoundResult.TIE
            }
            return result
        }
        // functions to generate custom randomized messages based on game state
        fun generateMsgTIE() : String {
            val tieList = listOf<String>("No points, it's a tie!", "Tie game, sorry no winners :/", "Close game, it's a tie.", "Tie game, good play!", "Game is a tie!")
            val randNum = Random.nextInt(0,4) // indices
            return tieList[randNum]
        }
        fun generateMsgLOSS() : String {
            val lossList = listOf<String>("Bummer, you lost!", "Lost the game, sorry :(", "Close game, better luck next time.", "Wasn't meant to be, tough loss.", "Rats, you lost -- hope you win next time!")
            val randNum = Random.nextInt(0,4) // indices
            return lossList[randNum]
        }
        fun generateMsgWIN() : String {
            val winList = listOf<String>("Hooray, great game you won!", "Your RPS skills showed through, congrats!", "Winner winner chicken dinner!", "Well played, you beat the computer!", "You're a winner, great play!")
            val randNum = Random.nextInt(0,4) // indices
            return winList[randNum]
        }
    }
}

fun main() {
    // welcome user and initialize classes
    println("Welcome to the Rock, Paper, Scissors simulator!\nRPS Player, what is your name? ")
    val playerName = readln()
    val gamePlay = GamePlayer(playerName)
    gamePlay.name = playerName // prints custom welcome message
    val game = GamePlayer.Game()
    println("How many rounds would you like to play?")
    var numRounds = readln().toInt()
    gamePlay.totalGamesPlayed = numRounds

    // use game and gamePlay objects to begin user vs AI RPS game
    while (numRounds > 0) {
        println("$playerName, choose a hand: Rock ('r'), Paper ('p'), or Scissors ('s')?")
        val letter : Char = readln().single()
        val userHand : Hands = gamePlay.chooseHand(letter)
        val computerHand : Hands = game.rockPaperScissors() // generate computer's move
        println("$playerName throws a ${userHand.value} and the computer throws a ${computerHand.value}!")

        val result = game.getGameResult(userHand, computerHand)
        when (result) {
            RoundResult.LOSS -> gamePlay.losses += 1
            RoundResult.WIN -> gamePlay.wins += 1
            else -> gamePlay.ties += 1
        }
        val msg = when (result) {
            RoundResult.LOSS -> game.generateMsgLOSS()
            RoundResult.WIN -> game.generateMsgWIN()
            else -> game.generateMsgTIE() // tie if not loss or win
        }
        println(msg)

        println("Press 'Enter' to continue or 'q' to quit...")
        val input = readln()
        if (input == "q") {
            exitProcess(0)
        }
        else {
            numRounds -= 1
        }
    }
    // End of game message statistics
    println("****** That's game, GG $playerName! ${gamePlay.totalGamesPlayed} rounds played in the simulator! ******\nGame Statistics for $playerName:\n-------------------------------------------")
    println("Wins: ${gamePlay.wins}")
    println("Losses: ${gamePlay.losses}")
    println("Ties: ${gamePlay.ties}")
    if (gamePlay.losses == 0) {
        gamePlay.winLossRatio = gamePlay.wins * 1.0
    }
    else if (gamePlay.wins == 0) {
        gamePlay.winLossRatio = 0.0
    }
    else {
        gamePlay.winLossRatio = (gamePlay.wins.toDouble() / (gamePlay.losses))
    }
    println("Win-Loss Ratio: ${gamePlay.winLossRatio}")
}