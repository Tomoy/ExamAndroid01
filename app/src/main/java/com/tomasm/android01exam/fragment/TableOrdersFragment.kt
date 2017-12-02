package com.tomasm.android01exam.fragment

import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*

import com.tomasm.android01exam.R
import com.tomasm.android01exam.activity.TableCalculatorActivity
import com.tomasm.android01exam.adapter.DishesRecyclerViewAdapter
import com.tomasm.android01exam.model.Dish
import com.tomasm.android01exam.model.Orders


class TableOrdersFragment : Fragment() {

    var tableNumber: Int = 0
    lateinit var root : View
    lateinit var ordersList: RecyclerView

    companion object {

        private val ARG_TABLE_NUMBER = "argTableNumber"

        fun newInstance(tableNum: Int): TableOrdersFragment {
            val fragment = TableOrdersFragment()

            val args = Bundle()
            args.putInt(ARG_TABLE_NUMBER, tableNum)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (arguments != null) {
            tableNumber = arguments.getInt(ARG_TABLE_NUMBER)
        }
    }

    //Definimos las opciones del menu
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.calculator, menu)

    }

    //Definimos las acciones de los botónes del menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.menu_show_calculator) {
            //Sabemos que se hizo click en el menu opción calculator

            val intent = TableCalculatorActivity.intent(activity, tableNumber)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflamos el layout para este fragmento
        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_table_orders, container, false)

            ordersList = root.findViewById(R.id.orders_list)
            ordersList.layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.dishes_recycler_columns_amount))
            ordersList.itemAnimator = DefaultItemAnimator()

            val tableOrders = Orders.getOrdersForTable(tableNumber)
            ordersList.adapter = DishesRecyclerViewAdapter(tableOrders)


            /*dishesAdapter.onClickListener = View.OnClickListener { v:View? ->
                //Aca me entero que se pulsó una de las vistas, v es la vista que fue pulsada
                val position = tableOrdersList.getChildAdapterPosition(v)
                val dishSelected = ordersPerTable[position]
                onDishSelectedListener?.onDishSelected(dishSelected)
            }*/
        }

        return root
    }

    //Método llamado por la actividad TableDetail, cuando fue plato fue agregado, para agregarlo a la lista
    fun updateFragmentListWithDish(dish: Dish) {
        Orders.addOrderToTable(dish, tableNumber)
        val tableOrders = Orders.getOrdersForTable(tableNumber)
        ordersList.adapter = DishesRecyclerViewAdapter(tableOrders)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

}
