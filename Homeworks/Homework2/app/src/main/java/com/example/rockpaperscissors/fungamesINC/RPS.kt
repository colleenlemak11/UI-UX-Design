package com.example.rockpaperscissors.fungamesINC
import com.example.rockpaperscissors.main.*
// RPS.kt holds all enum classes necessary for GamePlayer.kt to run.

enum class Hands(val value : String) {
    // define ENUM values
    ROCK("r"),
    PAPER("p"),
    SCISSORS("s");
    // methods to print RPS value with message
    companion object {
        fun rock(msg: String) {
            println(msg + Hands.ROCK.value)
        }
        fun paper(msg: String) {
            println(msg + Hands.PAPER.value)
        }
        fun scissors(msg: String) {
            println(msg + Hands.SCISSORS.value)
        }
    }
}

enum class RoundResult(private val value : String) {
    // define ENUM values
    TIE("Tie"),
    WIN("Win"),
    LOSS("Loss");
    // methods to print game status with message
    companion object {
        fun tie(msg: String) {
            println(msg + RoundResult.TIE.value)
        }
        fun win(msg: String) {
            println(msg + RoundResult.WIN.value)
        }
        fun loss(msg: String) {
            println(msg + RoundResult.LOSS.value)
        }
    }
}