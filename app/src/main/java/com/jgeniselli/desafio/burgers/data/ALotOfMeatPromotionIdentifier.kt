package com.jgeniselli.desafio.burgers.data


class ALotOfMeatPromotionIdentifier : PromotionIdentifier {

    private val meatId = 3

    override fun applyPromotionIfAvailable(burger: Burger) {
        val meat = Ingredient.makeForPrice(meatId, 0.0, "")
        val meatAmount = burger.getAmount(meat)
        val promotion = Promotion.makeALotOfMeat(meatAmount)
        when {
            meatAmount > 3 -> burger.addPromotion(promotion)
            else -> burger.removePromotion(promotion)
        }
    }
}