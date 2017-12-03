package com.tomasm.android01exam.model

import java.io.Serializable

/**
 * Created by TomasM on 11/12/17.
 */
object Tables : Serializable {

    private var tables: List<Table> = listOf(
            Table(1),
            Table(2),
            Table(3),
            Table(4),
            Table(5),
            Table(6),
            Table(7),
            Table(8),
            Table(9),
            Table(10)

    )
    val count
        get() = tables.size

    operator fun get(i: Int) = tables[i]
    fun toArray() = tables.toTypedArray()

}