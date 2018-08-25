package com.jgeniselli.desafio.burgers.data

import com.jgeniselli.desafio.burgers.data.promotions.PercentageDiscountCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class PercentageDiscountCalculatorTest {

    @Test
    fun calculatePercentage() {
        val calculator = makeCalculator(10.0)
        assertEquals(1.0, calculator.calculateDiscount(10.0), 0.0001)
    }

    @Test(expected = InvalidPercentageException::class)
    fun blockInstantiationOnInvalidPercentage() {
        makeCalculator(-10.0)
    }

    private fun makeCalculator(percentage: Double): PercentageDiscountCalculator =
            PercentageDiscountCalculator.makeForPercentage(percentage)

}