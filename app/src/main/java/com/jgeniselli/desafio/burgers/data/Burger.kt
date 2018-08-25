package com.jgeniselli.desafio.burgers.data

import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import java.util.Collections.unmodifiableList

class Burger(val id: Int, val name: String, val imageUrl: String) : Cloneable {

    private val ingredients = HashMap<Ingredient, Int>()
    private val promotions = HashSet<Promotion>()
    private val ingredientObservers = HashSet<IngredientChangesListener>()

    fun addPromotion(promotion: Promotion) {
        promotions.add(promotion)
    }

    fun removePromotion(promotion: Promotion) {
        promotions.remove(promotion)
    }

    fun addIngredient(ingredient: Ingredient, amount: Int) {
        if (amount > 0)
            ingredients[ingredient] = amount
        notifyIngredientChange()
    }

    private fun notifyIngredientChange() {
        ingredientObservers.forEach {
            it.onIngredientsChanged(this)
        }
    }

    fun removeIngredient(ingredient: Ingredient, amountToRemove: Int) {
        if (ingredients.contains(ingredient) && amountToRemove > 0) {
            val currentAmount = ingredients[ingredient]!!
            val adjustedAmount = minOf(amountToRemove, currentAmount)
            ingredients[ingredient] = currentAmount.minus(adjustedAmount)
            clearIngredientIfNeeded(ingredient)
            notifyIngredientChange()
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

    fun addIngredientChangesListener(listener: IngredientChangesListener) =
            ingredientObservers.add(listener)

    fun removeIngredientChangesListener(listener: IngredientChangesListener) {
        ingredientObservers.remove(listener)
    }

    companion object {
        fun valueOf(burgerData: BurgerData): Burger {
            return Burger(burgerData.id, burgerData.name, burgerData.image)
        }

        fun valuesOf(burgersData: List<BurgerData>): List<Burger> {
            val burgers = ArrayList<Burger>(burgersData.size)
            burgersData.forEach {
                burgers.add(Burger.valueOf(it))
            }
            return burgers
        }
    }

    public override fun clone(): Burger {
        val burger = Burger(id, name, imageUrl)
        burger.ingredients.putAll(ingredients)
        burger.promotions.addAll(promotions)
        burger.ingredientObservers.addAll(ingredientObservers)
        return burger
    }
}

interface IngredientChangesListener {
    fun onIngredientsChanged(burger: Burger)
}

