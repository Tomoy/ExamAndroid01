package com.tomasm.android01exam

import android.os.Bundle
import com.tomasm.android01exam.model.Orders

/**
 * Created by TomasM on 12/2/17.
 */

class SavedOrders {

    companion object Factory {
        val info = "This is factory"
        fun getMoreInfo():String { return "This is factory fun" }

        val tableOrders = Orders
    }
}