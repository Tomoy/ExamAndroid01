package com.tomasm.android01exam.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.tomasm.android01exam.R
import com.tomasm.android01exam.model.Dish
import kotlinx.android.synthetic.main.activity_table_calculator.*

class TableCalculatorActivity : AppCompatActivity() {

    companion object {

        val TABLE_NUMBER = "TableNumber"
        val TABLE_ORDERS = "TableOrders"

        fun intent(context: Context, tableNum: Int, tableOrders: Array<Dish>) : Intent {
            val intent = Intent(context, TableCalculatorActivity::class.java)
            intent.putExtra(TABLE_NUMBER, tableNum)
            intent.putExtra(TABLE_ORDERS, tableOrders)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R
                .layout.activity_table_calculator)

        title = getString(R.string.table_total_calculator)

        val tableNum = intent.getIntExtra(TABLE_NUMBER, 1)
        val tableOrders = intent.getSerializableExtra(TABLE_ORDERS) as Array<Dish>

        calculator_title.text = getString(R.string.table_calculator_title_format, tableNum + 1)


        val linearLayoutLabels = findViewById<View>(R.id.summary_labels) as LinearLayout
        val linearLayoutValues = findViewById<View>(R.id.summary_values) as LinearLayout

        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        var acumulatedTotal = 0.0
        val marginTop = resources.getDimension(R.dimen.default_margin).toInt()

        for (i in 0 until tableOrders.count()) {

            layoutParams.setMargins(0,marginTop * i,0,0)

            val testTextView = TextView(this)
            testTextView.textSize = 14f
            testTextView.text = tableOrders.get(i).name
            testTextView.layoutParams = layoutParams
            linearLayoutLabels.addView(testTextView)
        }

        for (i in 0 until tableOrders.count()) {

            layoutParams.setMargins(0,marginTop * i,0,0)
            val dishPrice = tableOrders.get(i).price

            val testTextView = TextView(this)
            testTextView.textSize = 14f
            testTextView.text = dishPrice.toString() + "€"
            testTextView.layoutParams = layoutParams
            linearLayoutValues.addView(testTextView)

            acumulatedTotal += dishPrice
        }

        calculator_total.text = getString(R.string.table_calculator_total_format, acumulatedTotal)
    }
}
