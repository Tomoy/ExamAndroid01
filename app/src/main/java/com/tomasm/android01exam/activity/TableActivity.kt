package com.tomasm.android01exam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tomasm.android01exam.R
import com.tomasm.android01exam.fragment.TableListFragment
import com.tomasm.android01exam.model.Table

class TableActivity : AppCompatActivity(), TableListFragment.OnTableSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        title = getString(R.string.table_activity_title)

        //Comprobamos que en la interfaz tenemos un Framelayout llamado table_list_fragment
        if (findViewById<View>(R.id.table_list_fragment) != null) {
            //Comprobar primero que no fue añadido previamente porque sino se va a añadir cada vez que la actividad se recargue
            if (fragmentManager.findFragmentById(R.id.table_list_fragment) == null) {
                //Añadir fragment en Activity
                val fragment = TableListFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.table_list_fragment, fragment)
                        .commit()
            }
        }
    }

    override fun onTableSelected(table: Table?, position: Int) {
        startActivity(TableDetailActivity.intent(this, position))
    }
}
