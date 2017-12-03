package com.tomasm.android01exam.model

import java.io.Serializable

/**
 * Created by TomasM on 11/12/17.
 */
object Orders : Serializable {

    private var orders = arrayListOf<MutableList<Dish>>(
            mutableListOf<Dish>(),
            mutableListOf<Dish>(),
            mutableListOf<Dish>(),
            mutableListOf<Dish>(),
            mutableListOf<Dish>(),
            mutableListOf<Dish>(),
            mutableListOf<Dish>(),
            mutableListOf<Dish>(),
            mutableListOf<Dish>(),
            mutableListOf<Dish>()
            )
    val count
        get() = orders.size

    operator fun get(i: Int) = orders[i]
    fun toArray() = orders.toTypedArray()

    fun addOrderToTable(dish:Dish, tableNum:Int) {
        val tableOrders = orders.get(tableNum)
        tableOrders.add(dish)
    }

    fun getOrdersForTable(tableNum: Int) : List<Dish> {
        return orders.get(tableNum)
    }

}