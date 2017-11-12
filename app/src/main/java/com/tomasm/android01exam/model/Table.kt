package com.tomasm.android01exam.model

import java.io.Serializable

/**
 * Created by TomasM on 11/12/17.
 */
data class Table (val number: Int, var dishes: List<Dish>?) : Serializable {

    //Convinience init
    constructor(number: Int) : this(number, null)

    override fun toString(): String {
        val toString = number.toString()
        return toString
    }
}