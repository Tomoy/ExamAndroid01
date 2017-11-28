package com.tomasm.android01exam.model

import java.io.Serializable

/**
 * Created by TomasM on 11/12/17.
 */
data class Dish (val name: String, val allergens: List<String>?, val price: Float, val description: String, val origin: String, val thumbId: Int ) : Serializable {

}