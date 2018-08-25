package com.jgeniselli.desafio.burgers.data

import com.jgeniselli.desafio.burgers.data.promotions.Promotion

open class BurgerDecorator(private val child: IBurger) : IBurger {

    override fun getId(): Int = child.getId()

    override fun getName(): String = child.getName()

    override fun getImageUrl(): String = child.getImageUrl()

    override fun addPromotion(promotion: Promotion) {
        child.addPromotion(promotion)
    }

    override fun removePromotion(promotion: Promotion) {
        child.removePromotion(promotion)
    }

    override fun addIngredient(ingredient: Ingredient, amount: Int) {
        child.addIngredient(ingredient, amount)
    }

    override fun removeIngredient(ingredient: Ingredient, amountToRemove: Int) {
        child.addIngredient(ingredient, amountToRemove)
    }

    override fun getPrice(): Double = child.getPrice()

    override fun getIngredients(): List<Ingredient> = child.getIngredients()

    override fun getAmount(ingredient: Ingredient): Int {
        return child.getAmount(ingredient)
    }

    override fun addIngredientChangesListener(listener: IngredientChangesListener) {
        child.addIngredientChangesListener(listener)
    }

    override fun removeIngredientChangesListener(listener: IngredientChangesListener) {
        child.removeIngredientChangesListener(listener)
    }

    override fun clone(): IBurger = child.clone()

}