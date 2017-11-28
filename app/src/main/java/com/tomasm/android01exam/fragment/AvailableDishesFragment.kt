package com.tomasm.android01exam.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ViewSwitcher
import com.tomasm.android01exam.DISHES_API_URL

import com.tomasm.android01exam.R
import com.tomasm.android01exam.adapter.DishesRecyclerViewAdapter
import com.tomasm.android01exam.model.Dish
import kotlinx.android.synthetic.main.fragment_available_dishes.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.json.JSONObject
import java.net.URL
import java.util.*

class AvailableDishesFragment : Fragment() {

    //Enumerado con valores, en este caso números (0,1)
    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        AVAILABLE_DISHES(1)
    }

    companion object {

        fun newInstance(): AvailableDishesFragment {
            val fragment = AvailableDishesFragment()
            return fragment
        }
    }

    private var onDishSelectedListener: OnDishSelectedListener? = null

    lateinit var rootView: View
    lateinit var viewSwitcher: ViewSwitcher
    lateinit var dishesList: RecyclerView

    var dishes: List<Dish>? = null
        set(value) {

            field = value

            if (value != null) {
                //Tenemos la info descargada Y seteada en la var dishes, la utilizo para actualizar la vista con el modelo
                val dishesAdapter = DishesRecyclerViewAdapter(value)
                dishesList.adapter = dishesAdapter

                dishesAdapter.onClickListener = View.OnClickListener { v:View? ->
                    //Aca me entero que se pulsó una de las vistas, v es la vista que fue pulsada
                    val position = dishesList.getChildAdapterPosition(v)
                    val dishSelected = value[position]
                    onDishSelectedListener?.onDishSelected(dishSelected)
                }

                //Actualizo el switcher para que no muestre mas el loading y muestre el contenido
                viewSwitcher.displayedChild = VIEW_INDEX.AVAILABLE_DISHES.index

            } else {
                //Aun no tenemos la info de los platos, entonces la descargamos
                getDishes()
            }
        }

    fun getDishes() {
        viewSwitcher.displayedChild = VIEW_INDEX.LOADING.index

        async(UI) {

            val newDishes: Deferred<List<Dish>?> = bg {
                downloadDishes()
            }

            val downloadedDishes = newDishes.await()

            if (downloadedDishes != null) {
                dishes = downloadedDishes
            } else {

                //Ha habido un error, se lo decimos al usuario con un dialogo
                AlertDialog.Builder(activity)
                        .setTitle(getString(R.string.alert_error_title))
                        .setMessage(getString(R.string.download_dishes_alert_message))
                        .setPositiveButton(getString(R.string.alert_error_retry), { dialog, which ->
                            dialog.dismiss()
                            getDishes()
                        })
                        .setNegativeButton(getString(R.string.alert_error_cancel), { dialog, which -> activity.finish() })
                        .show()
            }
        }
    }

    fun downloadDishes() : List<Dish>? {

        //Demoramos 2 sec la descarga para que siempre se llegue a ver el loading..
        Thread.sleep(2000)

        var url = URL(DISHES_API_URL)
        val jsonString = Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next()

        val jsonRoot = JSONObject(jsonString)
        val dishesFromInternet = jsonRoot.getJSONArray("dishes")

        //Creamos la lista mutable vacía para ir llenando con los platos que obtengo de internet
        val dishes = mutableListOf<Dish>()

        for (dishIndex in 0 until dishesFromInternet.length()) {
            val currentDish = dishesFromInternet.getJSONObject(dishIndex)
            val dishName = currentDish.getString("name")
            val allergens = currentDish.getJSONArray("allergens")

            var allergensList = mutableListOf<String>()

            for (i in 0 until allergens.length()) {
                allergensList.add(allergens.getString(i))
            }

            val price = currentDish.getDouble("price").toFloat()
            val description = currentDish.getString("description")
            val origin = currentDish.getString("origin")

            val dishThumbId = when(dishIndex) {
                0 -> R.drawable.dish01
                1 -> R.drawable.dish02
                2 -> R.drawable.dish03
                3 -> R.drawable.dish04
                4 -> R.drawable.dish05
                5 -> R.drawable.dish06
                else -> R.drawable.dish01
            }

            dishes.add(Dish(dishName,allergensList,price, description, origin, dishThumbId))

        }

        return dishes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (inflater != null) {

            rootView = inflater.inflate(R.layout.fragment_available_dishes, container, false)

            viewSwitcher = rootView.findViewById(R.id.view_switcher)
            viewSwitcher.setInAnimation(activity, android.R.anim.fade_in)
            viewSwitcher.setOutAnimation(activity, android.R.anim.fade_out)

            dishesList = rootView.findViewById(R.id.dishes_list)
            dishesList.layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.dishes_recycler_columns_amount))
            dishesList.itemAnimator = DefaultItemAnimator()

            //Para llamar el seter de dishes
            dishes = null

        }

        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonOnAttach(activity)
    }

    fun commonOnAttach(listener:Any?) {
        if (listener is OnDishSelectedListener) {
            onDishSelectedListener = listener
        }
    }

    override fun onDetach() {
        super.onDetach()
        onDishSelectedListener = null
    }

    interface OnDishSelectedListener {
        fun onDishSelected(dish: Dish)
    }

}