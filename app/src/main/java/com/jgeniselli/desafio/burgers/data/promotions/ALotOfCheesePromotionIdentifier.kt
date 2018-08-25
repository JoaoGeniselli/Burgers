package com.jgeniselli.desafio.burgers.data.promotions

import com.jgeniselli.desafio.burgers.data.IBurger
import com.jgeniselli.desafio.burgers.data.Ingredient

class ALotOfCheesePromotionIdentifier : PromotionIdentifier {

    private val cheeseId = 5

    override fun applyPromotionIfAvailable(burger: IBurger) {
        val cheese = Ingredient.makeForPrice(cheeseId, 0.0, "")
        val cheeseAmount = burger.getAmount(cheese)
        val promotion = Promotion.makeALotOfCheese(cheeseAmount)
        when {
            cheeseAmount >= 3 -> burger.addPromotion(promotion)
            else -> burger.removePromotion(promotion)
        }

    }
}
