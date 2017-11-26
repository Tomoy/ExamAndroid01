package com.tomasm.android01exam.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tomasm.android01exam.R

class TableCalculatorActivity : AppCompatActivity() {

    companion object {

        fun intent(context: Context) : Intent {
            val intent = Intent(context, TableCalculatorActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R
                .layout.activity_table_calculator)
        title = "Calculador Total"
    }
}
