package com.jgeniselli.desafio.burgers.data

import com.jgeniselli.desafio.burgers.data.promotions.PromotionIdentifierListener
import java.util.*
import java.util.Collections.unmodifiableMap

open class CustomBurger(b: IBurger) :
        MenuBurger(b.getId(), b.getName(), b.getImageUrl()) {

    val burger: MenuBurger = b as MenuBurger

    init {
        super.addIngredientChangesListener(PromotionIdentifierListener.getDefault())
    }

    override fun getName(): String = when {
        ingredients.isNotEmpty() -> burger.getName() + " - do seu jeito"
        else -> burger.getName()
    }

    override fun removeIngredient(ingredient: Ingredient, amountToRemove: Int) {
        super.addIngredient(ingredient, amountToRemove)
    }

    override fun getIngredients(): List<Ingredient> {
        val mergeSet = HashSet<Ingredient>()
        mergeSet.addAll(ingredients.keys)
        mergeSet.addAll(burger.getIngredients())
        return mergeSet.toList()
    }

    override fun getPromotionsDiscount(): Double =
            super.getPromotionsDiscount() + burger.getPromotionsDiscount()

    override fun getIngredientsTotalPrice(): Double =
            super.getIngredientsTotalPrice() + burger.getIngredientsTotalPrice()

    override fun getAmount(ingredient: Ingredient): Int =
            super.getAmount(ingredient) + burger.getAmount(ingredient)

    fun getExtraIngredientsForSelection(): Map<Ingredient, Int> {
        return unmodifiableMap(ingredients)
    }
}