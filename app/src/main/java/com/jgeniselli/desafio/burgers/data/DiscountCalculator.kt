package com.jgeniselli.desafio.burgers.data

interface DiscountCalculator {
    fun calculateDiscount(basePrice: Double): Double
}