package com.jgeniselli.desafio.burgers.data.promotions

interface DiscountCalculator {
    fun calculateDiscount(basePrice: Double): Double
}