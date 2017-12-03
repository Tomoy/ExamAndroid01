package com.tomasm.android01exam

/**
 * Created by TomasM on 12/3/17.
 */
class HelperClass {

    companion object {

        fun getAllergenThumbId(allergen:String) :Int{

            val allergenThumbId = when(allergen) {
                "eggs" -> R.drawable.allergen01
                "fish" -> R.drawable.allergen02
                "milk" -> R.drawable.allergen03
                "crustaceans" -> R.drawable.allergen04
                "wheat" -> R.drawable.allergen05
                else -> R.drawable.allergen01
            }

            return allergenThumbId
        }
    }
}