package com.tomasm.android01exam.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tomasm.android01exam.R
import com.tomasm.android01exam.fragment.AvailableDishesFragment

class AddDishActivity : AppCompatActivity() {

    companion object {

        fun intent(context: Context) : Intent {
            val intent = Intent(context, AddDishActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dish)

        title = getString(R.string.add_dish_title)

        //Add TableOrdersFragment
        //Comprobamos que en la interfaz tenemos un Framelayout llamado table_orders
        if (findViewById<View>(R.id.available_dishes_fragment) != null) {
            //Comprobar primero que no fue añadido previamente porque sino se va a añadir cada vez que la actividad se recargue
            if (fragmentManager.findFragmentById(R.id.available_dishes_fragment) == null) {
                //Añadir fragment en Activity
                val fragment = AvailableDishesFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.available_dishes_fragment, fragment)
                        .commit()
            }
        }
    }
}
