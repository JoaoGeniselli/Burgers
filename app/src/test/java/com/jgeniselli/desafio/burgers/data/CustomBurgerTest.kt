package com.jgeniselli.desafio.burgers.data

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.internal.verification.Times

class CustomBurgerTest {

    @Test
    fun addOneExtraIngredient() {
        val burger = mockBurger(1)
        val ingredient = mock(Ingredient::class.java)

        val customBurger = CustomBurger(burger)
        customBurger.addIngredient(ingredient, 1)

        assertEquals(2, customBurger.getIngredients().size)
        verify(burger, Times(0)).addIngredient(ingredient, 1)
    }

    @Test
    fun addFewExtraIngredient() {
        val burger = mockBurger(1)
        val ingredient1 = mock(Ingredient::class.java)
        val ingredient2 = mock(Ingredient::class.java)

        val customBurger = CustomBurger(burger)
        customBurger.addIngredient(ingredient1, 2)
        customBurger.addIngredient(ingredient2, 5)

        assertEquals(3, customBurger.getIngredients().size)
        verify(burger, Times(0)).addIngredient(ingredient1, 2)
        verify(burger, Times(0)).addIngredient(ingredient2, 5)
    }

    @Test
    fun removeExtraIngredient() {
        val burger = mockBurger(1)
        val ingredient = mock(Ingredient::class.java)

        val customBurger = CustomBurger(burger)
        customBurger.addIngredient(ingredient, 2)
        customBurger.removeIngredient(ingredient, 1)

        assertEquals(2, customBurger.getIngredients().size)
        verify(burger, Times(0)).removeIngredient(ingredient, 1)
    }

    @Test
    fun mergePrice() {

    }

    private fun mockBurger(ingredientAmount: Int): MenuBurger {
        val mock = mock(MenuBurger::class.java)

        `when`(mock.getId()).thenReturn(1)
        `when`(mock.getName()).thenReturn("Test")
        `when`(mock.getImageUrl()).thenReturn("")

        val ingredientMocks = ArrayList<Ingredient>()
        for (i in 1 .. ingredientAmount) {
            val ingredient = mock(Ingredient::class.java)
            `when`(ingredient.id).thenReturn(i + 200)
            ingredientMocks.add(ingredient)
        }
        `when`(mock.getIngredients()).thenReturn(ingredientMocks)
        return mock
    }


}