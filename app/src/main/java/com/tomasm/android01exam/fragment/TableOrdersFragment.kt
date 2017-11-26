package com.tomasm.android01exam.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.*

import com.tomasm.android01exam.R
import com.tomasm.android01exam.activity.TableCalculatorActivity

class TableOrdersFragment : Fragment() {

    private var tableNumber: Int? = null

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

            val intent = TableCalculatorActivity.intent(activity)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_table_orders, container, false)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

}
