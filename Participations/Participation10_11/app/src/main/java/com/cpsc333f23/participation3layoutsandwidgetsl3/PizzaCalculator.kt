package com.cpsc333f23.participation3layoutsandwidgetsl3

import kotlin.math.ceil

const val SLICES_PER_PIZZA = 8

class PizzaCalculator (partySize: Int, private var hungerLevel: HungerLevel) {
    private var partySize = 0
        set(value) {
            field = if (value >= 0) value else 0
        }

    enum class HungerLevel(var numSlices: Int) {
        LIGHT(2), MEDIUM(3), RAVENOUS(4)
    }

    val totalPizzas: Int
        get() {
            return ceil(partySize * hungerLevel.numSlices / SLICES_PER_PIZZA.toDouble()).toInt()
        }

    init {
        this.partySize = partySize
    }
}