package com.tomasm.android01exam.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.tomasm.android01exam.HelperClass
import com.tomasm.android01exam.R
import com.tomasm.android01exam.model.Dish
import kotlinx.android.synthetic.main.dish_detail_card.*

/**
 * Created by TomasM on 12/3/17.
 */
class DishDetailActivity: AppCompatActivity() {

    companion object {

        val SELECTED_DISH = "SelectedDish"

        fun intent(context: Context, dish:Dish) : Intent {
            val intent = Intent(context, DishDetailActivity::class.java)
            intent.putExtra(SELECTED_DISH, dish)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dish_detail_card)

        //Botón Volver
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.dish_detail_title)

        val selectedDish = intent.getSerializableExtra(SELECTED_DISH) as Dish

        updateViewWithModel(selectedDish)
    }

    fun updateViewWithModel(dish: Dish) {

        order_name.text = dish.name
        order_image.setImageResource(dish.imageId)
        order_price.text =  getString(R.string.dish_price_format, dish.price)
        order_origin.text = getString(R.string.dish_origin_format, dish.origin)

        order_allergens.text = getString(R.string.allergens_title)

        //Lógica para mostrar el icono correspondiente al alergeno, sería mas fácil si supiera armar las propiedades del imageview dinmaicamente :/
        if (dish.allergens != null) {

            //Seteo primero las imagenes 2 y 3 a vacías, porque no siempre los menus tienen 2 o 3 alérgenos
            order_allergen02.setImageResource(android.R.color.transparent)
            order_allergen03.setImageResource(android.R.color.transparent)

            for (allergenIndex in 0 until dish.allergens.count()) {

                when (allergenIndex) {
                    0 ->  order_allergen01.setImageResource(HelperClass.getAllergenThumbId(dish.allergens.get(allergenIndex)))
                    1 ->  order_allergen02.setImageResource(HelperClass.getAllergenThumbId(dish.allergens.get(allergenIndex)))
                    2 ->  order_allergen03.setImageResource(HelperClass.getAllergenThumbId(dish.allergens.get(allergenIndex)))
                }
            }
        }

        order_description.text = dish.description

        client_comments_field.setOnEditorActionListener() { v, actionId, event ->
            when(actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    val view = getWindow().getDecorView().getRootView()
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    true
                }
                else -> false
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
}