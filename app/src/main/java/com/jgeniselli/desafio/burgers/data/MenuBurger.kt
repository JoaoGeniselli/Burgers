package com.jgeniselli.desafio.burgers.data

import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import com.jgeniselli.desafio.burgers.data.promotions.PromotionIdentifierListener
import java.util.Collections.unmodifiableList

interface IBurger {
    fun getId(): Int
    fun getName(): String
    fun getImageUrl(): String
    fun addPromotion(promotion: Promotion)
    fun removePromotion(promotion: Promotion)
    fun addIngredient(ingredient: Ingredient, amount: Int)
    fun removeIngredient(ingredient: Ingredient, amountToRemove: Int)
    fun getPrice(): Double
    fun getIngredients(): List<Ingredient>
    fun getAmount(ingredient: Ingredient): Int
    fun addIngredientChangesListener(listener: IngredientChangesListener): Boolean
    fun removeIngredientChangesListener(listener: IngredientChangesListener)
    fun clone(): IBurger
}

class MenuBurger(private val _id: Int, private val _name: String, private val _imageUrl: String) : Cloneable, IBurger {

    private val ingredients = HashMap<Ingredient, Int>()
    private val promotions = HashSet<Promotion>()
    private val ingredientObservers = HashSet<IngredientChangesListener>()

    override fun getId(): Int = _id

    override fun getName(): String = _name

    override fun getImageUrl(): String = _imageUrl

    override fun addPromotion(promotion: Promotion) {
        promotions.add(promotion)
    }

    override fun removePromotion(promotion: Promotion) {
        promotions.remove(promotion)
    }

    override fun addIngredient(ingredient: Ingredient, amount: Int) {
        if (amount > 0)
            ingredients[ingredient] = amount
        notifyIngredientChange()
    }

    private fun notifyIngredientChange() {
        ingredientObservers.forEach {
            it.onIngredientsChanged(this)
        }
    }

    override fun removeIngredient(ingredient: Ingredient, amountToRemove: Int) {
        if (ingredients.contains(ingredient) && amountToRemove > 0) {
            val currentAmount = ingredients[ingredient]!!
            val adjustedAmount = minOf(amountToRemove, currentAmount)
            ingredients[ingredient] = currentAmount.minus(adjustedAmount)
            clearIngredientIfNeeded(ingredient)
            notifyIngredientChange()
        }
    }

    override fun getPrice(): Double {
        val totalPrice = getIngredientsTotalPrice()
        val discount = minOf(getPromotionsDiscount(), totalPrice)
        return totalPrice.minus(discount)
    }

    override fun getIngredients(): List<Ingredient> = unmodifiableList(ingredients.keys.toList())

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

    override fun getAmount(ingredient: Ingredient): Int {
        if (ingredients.contains(ingredient))
            return ingredients[ingredient]!!
        return 0
    }

    private fun clearIngredientIfNeeded(ingredient: Ingredient) {
        if (ingredients[ingredient] == 0)
            ingredients.remove(ingredient)
    }

    override fun addIngredientChangesListener(listener: IngredientChangesListener) =
            ingredientObservers.add(listener)

    override fun removeIngredientChangesListener(listener: IngredientChangesListener) {
        ingredientObservers.remove(listener)
    }

    companion object {
        fun valueOf(burgerData: BurgerData): MenuBurger {
            val burger = MenuBurger(burgerData.id, burgerData.name, burgerData.image)
            burger.addIngredientChangesListener(PromotionIdentifierListener.getDefault())
            return burger
        }

        fun valuesOf(burgersData: List<BurgerData>): List<MenuBurger> {
            return burgersData.map { valueOf(it) }
        }
    }

    override fun clone(): MenuBurger {
        val burger = MenuBurger(_id, _name, _imageUrl)
        burger.ingredients.putAll(ingredients)
        burger.promotions.addAll(promotions)
        burger.ingredientObservers.addAll(ingredientObservers)
        return burger
    }
}

interface IngredientChangesListener {
    fun onIngredientsChanged(burger: IBurger)
}

