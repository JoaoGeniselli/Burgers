package com.jgeniselli.desafio.burgers.data

class PercentageDiscountCalculator private constructor(discountPercentage: Double) : DiscountCalculator {

    private val calculationPercentage = discountPercentage / 100.0

    override fun calculateDiscount(basePrice: Double): Double =
            basePrice - (basePrice * calculationPercentage)

    companion object {
        fun makeForPercentage(percentage: Double): PercentageDiscountCalculator {
            if (percentage < 0.0) throw InvalidPercentageException()
            return PercentageDiscountCalculator(percentage)
        }
    }
}