package com.jgeniselli.desafio.burgers.details

import com.jgeniselli.desafio.burgers.data.Ingredient
import java.text.NumberFormat
import java.util.*

class SimpleIngredientDescription(val ingredient: Ingredient, private var amount: Int) : IngredientDescription {
    override fun getIngredientName(): String = ingredient.name

    override fun getAmount(): String = amount.toString()

    override fun setAmount(amount: Int) {
        this.amount = amount
    }

    override fun getIngredientImage(): String = ingredient.image

    override fun getIngredientPrice(): String {
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        return format.format(ingredient.price)
    }


}