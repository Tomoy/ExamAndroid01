package com.tomasm.android01exam.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tomasm.android01exam.R
import kotlinx.android.synthetic.main.activity_table_calculator.*

class TableCalculatorActivity : AppCompatActivity() {

    companion object {

        val TABLE_NUMBER = "TABLE_NUMBER"

        fun intent(context: Context, tableNum: Int) : Intent {
            val intent = Intent(context, TableCalculatorActivity::class.java)
            intent.putExtra(TABLE_NUMBER, tableNum)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R
                .layout.activity_table_calculator)

        title = getString(R.string.table_total_calculator)

        val tableNum = intent.getIntExtra(TABLE_NUMBER, 1)

        calculator_title.text = getString(R.string.table_calculator_title_format, tableNum + 1)
        amount_orders.text = "2"
        total_orders.text = "20 €"
    }
}
