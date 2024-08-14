package com.zybooks.thebanddatabase

import android.content.Context

class BandRepository private constructor(context: Context) {

    var bandList: MutableList<Band> = mutableListOf()

    companion object {
        private var instance: BandRepository? = null

        fun getInstance(context: Context): BandRepository {
            if (instance == null) {
                instance = BandRepository(context)
            }
            return instance!!
        }
    }

    init {
        val bands = context.resources.getStringArray(R.array.bands)
        val descriptions = context.resources.getStringArray(R.array.descriptions)
        for (i in bands.indices) {
            bandList.add(Band(i + 1, bands[i], descriptions[i]))
        }
    }

    fun getBand(bandId: Int): Band? {
        return bandList.find { it.id == bandId }
    }
}