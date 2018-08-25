package com.jgeniselli.desafio.burgers.data.promotions

import com.jgeniselli.desafio.burgers.data.Burger

interface PromotionIdentifier {
    fun applyPromotionIfAvailable(burger: Burger)
}