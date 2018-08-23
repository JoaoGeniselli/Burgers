package com.jgeniselli.desafio.burgers.data

class SubtractionDiscountCalculator private constructor(
        private val subtrahend: Double
) : DiscountCalculator {

    override fun calculateDiscount(basePrice: Double) = basePrice - subtrahend

    companion object {
        fun makeForSubtrahend(subtrahend: Double): SubtractionDiscountCalculator {
            return SubtractionDiscountCalculator(subtrahend)
        }
    }
}
