package com.jgeniselli.desafio.burgers.data.promotions

import com.jgeniselli.desafio.burgers.data.IBurger

interface PromotionIdentifier {
    fun applyPromotionIfAvailable(burger: IBurger)
}