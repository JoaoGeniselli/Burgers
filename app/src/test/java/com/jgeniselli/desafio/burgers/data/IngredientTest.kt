package com.jgeniselli.desafio.burgers.data

import org.junit.Assert.assertEquals
import org.junit.Test

class IngredientTest {

    @Test
    fun keepPrice() {
        val ingredient1 = makeIngredient(1.23)
        val ingredient2 = makeIngredient(8.75)
        val ingredient3 = makeIngredient(0.00)
        assertEquals(1.23, ingredient1.price, 0.001)
        assertEquals(8.75, ingredient2.price, 0.001)
        assertEquals(0.00, ingredient3.price, 0.001)
    }

    @Test(expected = InvalidPriceException::class)
    fun blockInstantiationForNegativePrice() {
        makeIngredient(-0.5)
    }

    fun makeIngredient(price: Double) = Ingredient.makeForPrice(1, price, "Test")

}