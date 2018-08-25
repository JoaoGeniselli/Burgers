package com.jgeniselli.desafio.burgers.data

import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.internal.verification.Times
import java.util.Collections.singletonList

class MenuBurgerTest {

    private lateinit var burger: MenuBurger

    @Before
    fun createBurger() {
        burger = MenuBurger(1, "Test", "")
    }

    @Test
    fun startEmpty() {
        with(burger) {
            assertEquals(0, getIngredients().size)
        }
    }

    @Test
    fun addOneIngredient() {
        with(burger) {
            val ingredient = makeIngredient()
            addIngredient(ingredient, 1)
            assertEquals(1, getAmount(ingredient))
            assertEquals(1, getIngredients().size)
        }
    }

    @Test
    fun addMoreThanOneIngredient() {
        with(burger) {
            val ingredient1 = makeIngredient()
            val ingredient2 = makeIngredient()
            addIngredient(ingredient1, 1)
            addIngredient(ingredient2, 5)
            assertEquals(1, getAmount(ingredient1))
            assertEquals(5, getAmount(ingredient2))
            assertEquals(2, getIngredients().size)
        }
    }

    @Test
    fun ignoreWhenAddInvalidIngredient_zero() {
        with(burger) {
            addIngredient(makeIngredient(), 0)
            assertEquals(0, getIngredients().size)
        }
    }

    @Test
    fun ignoreWhenAddInvalidIngredient_negative() {
        with(burger) {
            addIngredient(makeIngredient(), -1)
            assertEquals(0, getIngredients().size)
        }
    }

    @Test
    fun getZeroAmountFromUnknownIngredient() {
        with(burger) {
            assertEquals(0, getAmount(makeIngredient()))
            assertEquals(0, getIngredients().size)
        }
    }

    @Test
    fun removeOneIngredient() {
        with(burger) {
            val ingredient = makeIngredient()
            addIngredient(ingredient, 5)
            removeIngredient(ingredient, 3)
            assertEquals(2, getAmount(ingredient))
        }
    }

    @Test
    fun removeMoreThanOneIngredient() {
        with(burger) {
            val ingredient1 = makeIngredient()
            val ingredient2 = makeIngredient()
            addIngredient(ingredient1, 5)
            addIngredient(ingredient2, 6)
            removeIngredient(ingredient1, 3)
            removeIngredient(ingredient2, 1)
            assertEquals(2, getAmount(ingredient1))
            assertEquals(5, getAmount(ingredient2))
        }
    }

    @Test
    fun removeWithoutAdd() {
        with(burger) {
            val ingredient1 = makeIngredient()
            val ingredient2 = makeIngredient()
            addIngredient(ingredient1, 5)
            removeIngredient(ingredient2, 1)
            assertEquals(5, getAmount(ingredient1))
            assertEquals(0, getAmount(ingredient2))
        }
    }

    @Test
    fun removeMoreThanOneTimeForSameIngredient() {
        with(burger) {
            val ingredient1 = makeIngredient()
            addIngredient(ingredient1, 5)
            removeIngredient(ingredient1, 3)
            removeIngredient(ingredient1, 1)
            assertEquals(1, getAmount(ingredient1))
        }
    }

    @Test
    fun ignoreWhenRemoveZeroAmount() {
        with(burger) {
            val ingredient1 = makeIngredient()
            addIngredient(ingredient1, 5)
            removeIngredient(ingredient1, 0)
            assertEquals(5, getAmount(ingredient1))
        }
    }

    @Test
    fun ignoreWhenRemoveNegativeAmount() {
        with(burger) {
            val ingredient1 = makeIngredient()
            addIngredient(ingredient1, 5)
            removeIngredient(ingredient1, -1)
            assertEquals(5, getAmount(ingredient1))
        }
    }

    @Test
    fun removeIngredientByRemovingTotalAmount() {
        with(burger) {
            val ingredient1 = makeIngredient()
            addIngredient(ingredient1, 5)
            removeIngredient(ingredient1, 5)
            assertEquals(0, getAmount(ingredient1))
            assertEquals(0, getIngredients().size)
        }
    }

    @Test
    fun removeIngredientByRemovingMoreThanTotalAmount() {
        with(burger) {
            val ingredient1 = makeIngredient()
            addIngredient(ingredient1, 5)
            removeIngredient(ingredient1, 8)
            assertEquals(0, getAmount(ingredient1))
            assertEquals(0, getIngredients().size)
        }
    }

    @Test
    fun mustGetPriceFromIngredient_amount_1() {
        with(burger) {
            val ingredient1 = makeIngredient(1.50)
            addIngredient(ingredient1, 1)
            assertEquals(1.50, getPrice(), 0.001)
        }
    }

    @Test
    fun mustGetPriceFromIngredient_amount_more_than_1() {
        with(burger) {
            val ingredient1 = makeIngredient(1.50)
            addIngredient(ingredient1, 3)
            assertEquals(4.50, getPrice(), 0.001)
        }
    }

    @Test
    fun mustGetPriceFromMoreThanOneIngredient() {
        with(burger) {
            val ingredient1 = makeIngredient(1.50)
            val ingredient2 = makeIngredient(2.00)
            addIngredient(ingredient1, 3)
            addIngredient(ingredient2, 8)
            assertEquals(20.50, getPrice(), 0.001)
        }
    }

    @Test
    fun mustUpdatePriceWhenRemovingIngredient_partially() {
        with(burger) {
            val ingredient1 = makeIngredient(1.50)
            val ingredient2 = makeIngredient(2.00)
            addIngredient(ingredient1, 3)
            addIngredient(ingredient2, 5)
            removeIngredient(ingredient2, 2)

            assertEquals(10.50, getPrice(), 0.001)
            verify(ingredient1, Times(1)).price
            verify(ingredient2, Times(1)).price
        }
    }

    @Test
    fun mustUpdatePriceWhenRemovingIngredient_totally() {
        with(burger) {
            val ingredient1 = makeIngredient(1.50)
            val ingredient2 = makeIngredient(2.00)

            addIngredient(ingredient1, 3)
            addIngredient(ingredient2, 5)
            removeIngredient(ingredient1, 3)
            removeIngredient(ingredient2, 5)
            assertEquals(0.0, getPrice(), 0.001)
            verify(ingredient1, Times(0)).price
            verify(ingredient2, Times(0)).price
        }
    }

    @Test
    fun addOnePromotion() {
        with(burger) {
            val ingredient = makeIngredient(1.50)
            val ingredients = singletonList(ingredient)
            val promotion = makePromotion(1.0, ingredients)
            addIngredient(ingredient, 3)
            addPromotion(promotion)
            assertEquals(3.50, getPrice(), 0.001)
            verify(promotion, Times(1)).getDiscountFor(ingredients)
        }
    }

    @Test
    fun addMoreThanOnePromotion() {
        with(burger) {
            val ingredient = makeIngredient(1.50)
            val ingredients = singletonList(ingredient)
            val promotion1 = makePromotion(1.0, ingredients)
            val promotion2 = makePromotion(2.1, ingredients)
            addIngredient(ingredient, 3)
            addPromotion(promotion1)
            addPromotion(promotion2)
            assertEquals(1.40, getPrice(), 0.001)
            verify(promotion1, Times(1)).getDiscountFor(ingredients)
            verify(promotion2, Times(1)).getDiscountFor(ingredients)
        }
    }

    @Test
    fun updatePriceWhenRemovingOnePromotion() {
        with(burger) {
            val ingredient = makeIngredient(1.50)
            val ingredients = singletonList(ingredient)
            val promotion1 = makePromotion(1.0, ingredients)
            val promotion2 = makePromotion(2.1, ingredients)
            addIngredient(ingredient, 3)
            addPromotion(promotion1)
            addPromotion(promotion2)
            removePromotion(promotion2)
            assertEquals(3.50, getPrice(), 0.001)
            verify(promotion1, Times(1)).getDiscountFor(ingredients)
            verify(promotion2, Times(0)).getDiscountFor(ingredients)
        }
    }

    @Test
    fun updatePriceWhenRemovingAllPromotions() {
        with(burger) {
            val ingredient = makeIngredient(1.50)
            val ingredients = singletonList(ingredient)
            val promotion1 = makePromotion(1.0, ingredients)
            val promotion2 = makePromotion(2.1, ingredients)
            addIngredient(ingredient, 3)
            addPromotion(promotion1)
            addPromotion(promotion2)
            removePromotion(promotion1)
            removePromotion(promotion2)
            assertEquals(4.50, getPrice(), 0.001)
            verify(promotion1, Times(0)).getDiscountFor(ingredients)
            verify(promotion2, Times(0)).getDiscountFor(ingredients)
        }
    }

    @Test
    fun keepOneDiscountWhenAddingSamePromotionTwice() {
        with(burger) {
            val ingredient = makeIngredient(1.50)
            val ingredients = singletonList(ingredient)
            val promotion1 = makePromotion(3.3, ingredients)
            addIngredient(ingredient, 3)
            addPromotion(promotion1)
            addPromotion(promotion1)
            assertEquals(1.20, getPrice(), 0.001)
            verify(promotion1, Times(1)).getDiscountFor(ingredients)
        }
    }

    @Test
    fun makeBurgerFreeWhenAddingDiscountGreaterThanPrice() {
        with(burger) {
            val ingredient = makeIngredient(1.50)
            val ingredients = singletonList(ingredient)
            addIngredient(ingredient, 3)
            addPromotion(makePromotion(10.0, ingredients))
            assertEquals(0.0, getPrice(), 0.001)
        }
    }

    @Test
    fun notifyAddToObserver() {
        with(burger) {
            val listener = makeIngredientsListener()
            val ingredient = makeIngredient(1.50)
            addIngredientChangesListener(listener)
            addIngredient(ingredient, 3)
            verify(listener, Times(1)).onIngredientsChanged(burger)
        }
    }

    @Test
    fun notifyRemoveToObserver() {
        with(burger) {
            val listener = makeIngredientsListener()
            val ingredient = makeIngredient(1.50)
            addIngredientChangesListener(listener)
            addIngredient(ingredient, 3)
            removeIngredient(ingredient, 2)
            verify(listener, Times(2)).onIngredientsChanged(burger)
        }
    }

    @Test
    fun notifyToManyObservers() {
        with(burger) {
            val listener1 = makeIngredientsListener()
            val listener2 = makeIngredientsListener()
            val ingredient = makeIngredient(1.50)
            addIngredientChangesListener(listener1)
            addIngredientChangesListener(listener2)
            addIngredient(ingredient, 3)
            removeIngredient(ingredient, 1)
            verify(listener1, Times(2)).onIngredientsChanged(burger)
            verify(listener2, Times(2)).onIngredientsChanged(burger)
        }
    }

    @Test
    fun updateNotificationsWhenRemovingObservers() {
        with(burger) {
            val listener1 = makeIngredientsListener()
            val ingredient = makeIngredient(1.50)
            addIngredientChangesListener(listener1)
            removeIngredientChangesListener(listener1)
            addIngredient(ingredient, 3)
            verify(listener1, Times(0)).onIngredientsChanged(burger)
        }
    }

    private fun makeIngredient(): Ingredient = makeIngredient(10.0)

    private fun makeIngredient(price: Double): Ingredient {
        val mock = mock(Ingredient::class.java)
        `when`(mock.price).thenReturn(price)
        return mock
    }

    private fun makePromotion(discount: Double, ingredients: List<Ingredient>): Promotion {
        val mock = mock(Promotion::class.java)
        `when`(mock.getDiscountFor(ingredients)).thenReturn(discount)
        return mock
    }

    private fun makeIngredientsListener(): IngredientChangesListener {
        return mock(IngredientChangesListener::class.java)
    }
}