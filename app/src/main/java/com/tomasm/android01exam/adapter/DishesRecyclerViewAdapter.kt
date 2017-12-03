package com.tomasm.android01exam.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tomasm.android01exam.HelperClass
import com.tomasm.android01exam.R
import com.tomasm.android01exam.model.Dish

/**
 * Created by TomasM on 11/27/17.
 */
class DishesRecyclerViewAdapter(val dishes: List<Dish>?) : RecyclerView.Adapter<DishesRecyclerViewAdapter.DishesViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishesViewHolder {
        val dishCardView = LayoutInflater.from(parent?.context).inflate(R.layout.dish_card, parent, false)
        dishCardView.setOnClickListener(onClickListener)
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
        val allergensTitle = itemView.findViewById<TextView>(R.id.dish_allergens)

        val allergenImageView01 = itemView.findViewById<ImageView>(R.id.dish_allergen01)
        val allergenImageView02 = itemView.findViewById<ImageView>(R.id.dish_allergen02)
        val allergenImageView03 = itemView.findViewById<ImageView>(R.id.dish_allergen03)

        fun bindDish(dish: Dish) {


            //Accedemos al contexto de Dish a través de una de sus vistas
            val context = dishImageView.context

            //Actualizamos la vista con el modelo
            dishNameTextView.text = dish.name
            dishImageView.setImageResource(dish.thumbId)
            dishPriceTextView.text = context.getString(R.string.dish_price_format, dish.price)
            dishOriginTextView.text = context.getString(R.string.dish_origin_format, dish.origin)

            allergensTitle.text = context.getString(R.string.allergens_title)

            //Lógica para mostrar el icono correspondiente al alergeno, sería mas fácil si supiera armar las propiedades del imageview dinmaicamente :/
            if (dish.allergens != null) {

                //Seteo primero las imagenes 2 y 3 a vacías, porque no siempre los menus tienen 2 o 3 alérgenos
                allergenImageView02.setImageResource(android.R.color.transparent)
                allergenImageView03.setImageResource(android.R.color.transparent)

                for (allergenIndex in 0 until dish.allergens.count()) {

                    when (allergenIndex) {
                        0 ->  allergenImageView01.setImageResource(HelperClass.getAllergenThumbId(dish.allergens.get(allergenIndex)))
                        1 ->  allergenImageView02.setImageResource(HelperClass.getAllergenThumbId(dish.allergens.get(allergenIndex)))
                        2 ->  allergenImageView03.setImageResource(HelperClass.getAllergenThumbId(dish.allergens.get(allergenIndex)))
                    }
                }
            }
        }
    }
}