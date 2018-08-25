package com.jgeniselli.desafio.burgers.data.promotions

import com.jgeniselli.desafio.burgers.data.MenuBurger
import com.jgeniselli.desafio.burgers.data.Ingredient
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.internal.verification.Times

class LightPromotionIdentifierTest {

    private val lettuceId = 1
    private val baconId = 2
    private val meatId = 3

    @Test
    fun ignoreWhenReceiveLettuceAndBacon() {
        val ids = arrayOf(lettuceId, baconId)
        val burger = mockBurger(ids)
        val identifier = LightPromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(0)).addPromotion(Promotion.makeLight())
    }

    @Test
    fun addPromotionWhenReceiveLettuceWithoutBacon() {
        val ids = arrayOf(lettuceId, meatId)
        val burger = mockBurger(ids)
        val identifier = LightPromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(1)).addPromotion(Promotion.makeLight())
    }

    @Test
    fun addThenRemovePromotionOnConditionsChanged() {
        val ids = arrayOf(lettuceId, meatId)
        val burger = mockBurger(ids)
        val identifier = LightPromotionIdentifier()
        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(1)).addPromotion(Promotion.makeLight())

        val newIngredients = mockIngredients(arrayOf(lettuceId, baconId))
        `when`(burger.getIngredients()).thenReturn(newIngredients)

        identifier.applyPromotionIfAvailable(burger)
        verify(burger, Times(1)).removePromotion(Promotion.makeLight())
    }

    private fun mockBurger(ingredientIds: Array<Int>): MenuBurger {
        val ingredientMocks = mockIngredients(ingredientIds)
        val mock = mock(MenuBurger::class.java)
        `when`(mock.getIngredients()).thenReturn(ingredientMocks)
        return mock
    }

    private fun mockIngredients(ingredientIds: Array<Int>): ArrayList<Ingredient> {
        val ingredientMocks = ArrayList<Ingredient>()
        ingredientIds.forEach {
            val ingredient = mock(Ingredient::class.java)
            `when`(ingredient.id).thenReturn(it)
            ingredientMocks.add(ingredient)
        }
        return ingredientMocks
    }
}

