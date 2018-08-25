package com.jgeniselli.desafio.burgers.data.promotions

class SubtractionDiscountCalculator private constructor(
        private val subtrahend: Double
) : DiscountCalculator {

    override fun calculateDiscount(basePrice: Double): Double {
        return subtrahend
    }

    companion object {
        fun makeForSubtrahend(subtrahend: Double): SubtractionDiscountCalculator {
            return SubtractionDiscountCalculator(subtrahend)
        }
    }
}
