package com.jgeniselli.desafio.burgers.menu

import com.jgeniselli.desafio.burgers.data.Burger
import java.text.NumberFormat
import java.util.*

class BurgerToDescriptionAdapter(private val burger: Burger) : BurgerDescription {

    override fun getBurgerId() = burger.getId()

    override fun getName(): String = burger.getName()

    override fun getImageUrl(): String = burger.getImageUrl()

    override fun getIngredientNames(): String {
        val names = ArrayList<String>()
        burger.getIngredients()
                .sortedBy { it.id }
                .forEach { names.add(it.name) }
        return names.joinToString(separator = ", ")
    }

    override fun getFormattedPrice(): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        return "Preço: " + formatter.format(burger.getPrice())
    }
}