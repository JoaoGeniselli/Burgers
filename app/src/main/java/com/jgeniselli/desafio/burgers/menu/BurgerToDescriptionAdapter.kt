package com.jgeniselli.desafio.burgers.menu

import com.jgeniselli.desafio.burgers.data.Burger
import java.text.NumberFormat
import java.util.*

class BurgerToDescriptionAdapter(private val burger: Burger) : BurgerDescription {

    override fun getName(): String = burger.name

    override fun getImageUrl(): String = burger.imageUrl

    override fun getIngredientNames(): String {
        val ingredients = burger.getIngredients()
        var content = ""
        ingredients.forEachIndexed { index, ingredient ->
            run {
                content += ingredient.name
                if (index > 0 && index < ingredients.lastIndex) {
                    content += ", "
                }
            }
        }
        return content
    }

    override fun getFormattedPrice(): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        return "Preço: " + formatter.format(burger.getPrice())
    }
}