package com.tomasm.android01exam.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tomasm.android01exam.R
import com.tomasm.android01exam.model.Dish

/**
 * Created by TomasM on 11/27/17.
 */
class DishesRecyclerViewAdapter(val dishes: List<Dish>?) : RecyclerView.Adapter<DishesRecyclerViewAdapter.DishesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishesViewHolder {
        val dishCardView = LayoutInflater.from(parent?.context).inflate(R.layout.dish_card, parent, false)
        return DishesViewHolder(dishCardView)
    }

    override fun onBindViewHolder(holder: DishesViewHolder?, position: Int) {
        if (dishes != null) {
            holder?.bindDish(dishes[position])
        }
    }

    override fun getItemCount(): Int {
        return dishes?.size ?: 0
    }

    inner class DishesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dishNameTextView = itemView.findViewById<TextView>(R.id.dish_name)
        val dishImageView = itemView.findViewById<ImageView>(R.id.dish_image)
        val dishPriceTextView = itemView.findViewById<TextView>(R.id.dish_price)
        val dishOriginTextView = itemView.findViewById<TextView>(R.id.dish_origin)

        fun bindDish(dish: Dish) {

            //Accedemos al contexto de Dish a través de una de sus vistas
            val context = dishImageView.context

            //Actualizamos la vista con el modelo
            dishNameTextView.text = dish.name
            //dishImageView.setImageResource(dish.icon)
            dishPriceTextView.text = dish.price.toString()
            dishOriginTextView.text = dish.origin

            /*val humidityString = context.getString(R.string.humidity_format, forecast.humidity)
            humidity.text = humidityString*/
        }
    }
}