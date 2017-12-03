package com.tomasm.android01exam.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.tomasm.android01exam.R
import com.tomasm.android01exam.fragment.TableOrdersFragment
import com.tomasm.android01exam.model.Dish
import kotlinx.android.synthetic.main.activity_table_detail.*

class TableDetailActivity : AppCompatActivity(), TableOrdersFragment.OnOrderSelectedListener {

    companion object {

        val TABLE_NUMBER = "TABLE_NUMBER"
        val REQUEST_CODE_ADD_DISH_ACTIVITY = 1

        fun intent(context: Context, tableNum: Int) : Intent {
            val intent = Intent(context, TableDetailActivity::class.java)
            intent.putExtra(TABLE_NUMBER, tableNum)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_detail)
        //Agregamos menu a la actividad

        // Recibimos el número de la mesa que estamos mostrando
        val tableNum = intent.getIntExtra(TABLE_NUMBER, 1)

        title = getString(R.string.dish_title_format, tableNum+1)
        //Button Back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Add TableOrdersFragment
        //Comprobamos que en la interfaz tenemos un Framelayout llamado table_orders
        if (findViewById<View>(R.id.table_orders_fragment) != null) {
            //Comprobar primero que no fue añadido previamente porque sino se va a añadir cada vez que la actividad se recargue
            if (fragmentManager.findFragmentById(R.id.table_orders_fragment) == null) {
                //Añadir fragment en Activity
                val fragment = TableOrdersFragment.newInstance(tableNum)
                fragmentManager.beginTransaction()
                        .add(R.id.table_orders_fragment, fragment)
                        .commit()
            }
        }

        //Floating button acción, cambiamos a la activity para agregar platos
        add_dish_button.setOnClickListener {
            startActivityForResult(AddDishActivity.intent(this), REQUEST_CODE_ADD_DISH_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_ADD_DISH_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val dishToAdd = data?.getSerializableExtra(AddDishActivity.EXTRA_DISH_RESULT) as Dish

                //Obtengo el fragment TableOrdersFragment para pasarle el dish elegido
                val tableOrdersFragment = fragmentManager.findFragmentById(R.id.table_orders_fragment) as TableOrdersFragment
                tableOrdersFragment.updateFragmentListWithDish(dishToAdd)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        //Button back was pressed so we terminate the activity to go back to TableActivity
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        return false
    }

    override fun orderSelected(dish: Dish) {
        startActivity(DishDetailActivity.intent(this, dish))
    }
}
