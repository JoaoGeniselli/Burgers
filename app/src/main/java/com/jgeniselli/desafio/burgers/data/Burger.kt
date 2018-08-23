package com.jgeniselli.desafio.burgers.data

import java.util.Collections.unmodifiableList

class Burger {

    val id = 0
    val name = ""
    private val ingredients = HashMap<Ingredient, Int>()
    val promotions = HashSet<Promotion>()

    fun addPromotion(promotion: Promotion) {
        promotions.add(promotion)
    }

    fun removePromotion(promotion: Promotion) {
        promotions.remove(promotion)
    }

    fun addIngredient(ingredient: Ingredient, amount: Int) {
        if (amount > 0)
            ingredients[ingredient] = amount
    }

    fun removeIngredient(ingredient: Ingredient, amountToRemove: Int) {
        if (ingredients.contains(ingredient) && amountToRemove > 0) {
            val currentAmount = ingredients[ingredient]!!
            val adjustedAmount = minOf(amountToRemove, currentAmount)
            ingredients[ingredient] = currentAmount.minus(adjustedAmount)
            clearIngredientIfNeeded(ingredient)
        }
    }

    fun getPrice(): Double {
        val totalPrice = getIngredientsTotalPrice()
        val discount = minOf(getPromotionsDiscount(), totalPrice)
        return totalPrice.minus(discount)
    }

    fun getIngredients(): List<Ingredient> = unmodifiableList(ingredients.keys.toList())

    private fun getIngredientsTotalPrice(): Double {
        var price = 0.0
        ingredients.forEach {
            price += it.key.price * it.value
        }
        return price
    }

    private fun getPromotionsDiscount(): Double {
        var discount = 0.0
        val ingredients = this.ingredients.keys.toList()
        promotions.forEach {
            discount += it.getDiscountFor(ingredients)
        }
        return discount
    }

    fun getAmount(ingredient: Ingredient): Int {
        if (ingredients.contains(ingredient))
            return ingredients[ingredient]!!
        return 0
    }

    private fun clearIngredientIfNeeded(ingredient: Ingredient) {
        if (ingredients[ingredient] == 0)
            ingredients.remove(ingredient)
    }
}

class Promotion {
    fun getId() = 0
    fun getDiscountFor(ingredients: List<Ingredient>) = 0.0
}