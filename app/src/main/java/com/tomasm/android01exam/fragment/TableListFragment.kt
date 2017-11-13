package com.tomasm.android01exam.fragment

import com.tomasm.android01exam.R
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.tomasm.android01exam.model.Table
import com.tomasm.android01exam.model.Tables

/**
 * Created by TomasM on 11/13/17.
 */
class TableListFragment : Fragment() {

    lateinit var root : View
    private var onTableSelectedListener: OnTableSelectedListener? = null


    interface OnTableSelectedListener {
        fun onTableSelected(table: Table?, position: Int)
    }

    companion object {

        fun newInstance(): TableListFragment {
            val fragment = TableListFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflamos el layout para este fragmento
        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_table_list, container, false)
            val list = root.findViewById<ListView>(R.id.table_list)
            val adapter = ArrayAdapter<Table>(activity, android.R.layout.simple_list_item_1, Tables.toArray())
            list.adapter = adapter

            //Para enterarnos cuando se hace click en un elemento de la ListView
            list.setOnItemClickListener { parent, view, position, id ->
                Log.v("TAG", "Click on mesa num: ${position}")
                onTableSelectedListener?.onTableSelected(Tables.get(position), position)
            }
        }

        return root
    }

}