package com.tomasm.android01exam.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewSwitcher
import com.tomasm.android01exam.DISHES_API_URL

import com.tomasm.android01exam.R
import com.tomasm.android01exam.model.Dish
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

    lateinit var rootView: View
    lateinit var viewSwitcher: ViewSwitcher

    var dishes: List<Dish>? = null
        set(value) {

            field = value

            if (value != null) {
                //Tenemos la info descargada Y seteada en la var dishes, la utilizo para actualizar la vista con el modelo

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

            dishes.add(Dish(dishName,allergensList,price, description, origin))

        }

        return dishes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (inflater != null) {

            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_available_dishes, container, false)

            viewSwitcher = rootView.findViewById(R.id.view_switcher)
            viewSwitcher.setInAnimation(activity, android.R.anim.fade_in)
            viewSwitcher.setOutAnimation(activity, android.R.anim.fade_out)

            dishes = null

        }

        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

}