package com.tomasm.android01exam.model

import android.annotation.SuppressLint
import java.io.Serializable

/**
 * Created by TomasM on 11/12/17.
 */
data class Table (val number: Int, var dishes: List<Dish>?) : Serializable {

    //Convinience init
    constructor(number: Int) : this(number, null)

    @SuppressLint("StringFormatMatches")
    override fun toString(): String {

        val toString = "Mesa " + number.toString()
        return toString
    }
}