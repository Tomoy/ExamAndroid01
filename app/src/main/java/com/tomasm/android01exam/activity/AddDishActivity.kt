package com.tomasm.android01exam.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.tomasm.android01exam.R
import com.tomasm.android01exam.fragment.AvailableDishesFragment
import com.tomasm.android01exam.model.Dish

class AddDishActivity : AppCompatActivity(), AvailableDishesFragment.OnDishSelectedListener {


    companion object {
        val EXTRA_DISH_RESULT = "ExtraDishResult"

        fun intent(context: Context) : Intent {
            val intent = Intent(context, AddDishActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dish)

        title = getString(R.string.add_dish_title)

        //Botón Volver
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        //Button back was pressed so we terminate the activity to go back to TableDetailActivity
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        return false
    }

    //Método de la interfaz de AvailableDishesFragment para comunicarle a la actividad que un plato fue seleccionado
    override fun onDishSelected(dish: Dish) {

        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_DISH_RESULT, dish)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
