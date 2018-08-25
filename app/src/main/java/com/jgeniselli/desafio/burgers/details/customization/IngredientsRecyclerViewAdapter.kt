package com.jgeniselli.desafio.burgers.details.customization

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.jgeniselli.desafio.burgers.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.line_view_ingredient_control.view.*

class IngredientsRecyclerViewAdapter : RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder>() {

    private var ingredients: List<IngredientDescription> = ArrayList()

    fun updateContent(ingredients: List<IngredientDescription>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.line_view_ingredient_control, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = ingredients[position]
        with(viewHolder) {
            nameView.text = item.getIngredientName()
            amountView.text = item.getFormattedAmount()
            priceView.text = item.getIngredientPrice()
            Picasso.get()
                    .load(item.getIngredientImage())
                    .placeholder(R.drawable.ic_burger_primary)
                    .into(imageView)
            removeButton.setOnClickListener {
                decreateAmount(item, amountView)
            }
            addButton.setOnClickListener {
                increaseAmount(item, amountView)
            }
        }
    }

    private fun increaseAmount(item: IngredientDescription, amountView: TextView) {
        var currentAmount = item.getAmount().toInt()
        if (currentAmount < 99) {
            currentAmount++
            item.setAmount(currentAmount)
            amountView.text = item.getFormattedAmount()
        }
    }

    private fun decreateAmount(item: IngredientDescription, amountView: TextView) {
        var currentAmount = item.getAmount().toInt()
        if (currentAmount > 0) {
            currentAmount--
            item.setAmount(currentAmount)
            amountView.text = item.getFormattedAmount()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.name_view
        val amountView: TextView = view.amount_view
        val removeButton: ImageButton = view.remove_btn
        val addButton: ImageButton = view.add_btn
        val imageView: ImageView = view.image_view
        val priceView: TextView = view.price_view
    }
}

interface IngredientDescription {
    fun getIngredientId(): Int
    fun getIngredientName(): String
    fun getAmount(): Int
    fun getFormattedAmount(): String
    fun setAmount(amount: Int)
    fun getIngredientPrice(): String
    fun getIngredientImage(): String
}