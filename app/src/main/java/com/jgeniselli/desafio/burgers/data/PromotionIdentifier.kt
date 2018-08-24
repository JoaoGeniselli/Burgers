package com.jgeniselli.desafio.burgers.data

interface PromotionIdentifier {
    fun applyPromotionIfAvailable(burger: Burger)
}