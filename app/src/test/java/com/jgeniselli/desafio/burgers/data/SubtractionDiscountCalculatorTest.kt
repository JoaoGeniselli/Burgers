package com.jgeniselli.desafio.burgers.data

import org.junit.Assert.*
import org.junit.Test

class SubtractionDiscountCalculatorTest {

    @Test
    fun calculatePercentage() {
        val calculator = makeCalculator(5.5)
        assertEquals(4.5, calculator.calculateDiscount(10.0), 0.0001)
    }

    private fun makeCalculator(subtrahend: Double) =
            SubtractionDiscountCalculator.makeForSubtrahend(subtrahend)

}