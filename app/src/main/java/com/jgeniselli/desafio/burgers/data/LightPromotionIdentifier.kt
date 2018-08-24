package com.jgeniselli.desafio.burgers.data


class LightPromotionIdentifier : PromotionIdentifier {

    private val lettuceId = 1
    private val baconId = 2

    override fun applyPromotionIfAvailable(burger: Burger) {
        val promotion = Promotion.makeLight()
        when {
            containsAnyLettuceAndNoneBacon(burger.getIngredients()) -> burger.addPromotion(promotion)
            else -> burger.removePromotion(promotion)
        }
    }

    private fun containsAnyLettuceAndNoneBacon(ingredients: List<Ingredient>): Boolean =
            ingredients.any { it.id == lettuceId } && ingredients.none { it.id == baconId }
}