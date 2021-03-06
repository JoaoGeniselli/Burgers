package com.jgeniselli.desafio.burgers.data.promotions

import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.IngredientChangesListener
import java.util.*

class PromotionIdentifierListener constructor(
        private var identifiers: List<PromotionIdentifier>): IngredientChangesListener {

    override fun onIngredientsChanged(burger: Burger) {
        identifiers.forEach {
            it.applyPromotionIfAvailable(burger)
        }
    }

    companion object {

        private var instance: PromotionIdentifierListener? = null

        fun getDefault() : PromotionIdentifierListener {
            instance
                    ?: synchronized(this) {
                instance = PromotionIdentifierListener(Arrays.asList(
                        LightPromotionIdentifier(),
                        ALotOfMeatPromotionIdentifier(),
                        ALotOfCheesePromotionIdentifier()
                ))
            }
            return instance!!
        }
    }
}
