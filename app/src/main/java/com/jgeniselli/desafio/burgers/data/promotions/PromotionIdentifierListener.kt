package com.jgeniselli.desafio.burgers.data.promotions

import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.IngredientChangesListener
import java.util.*

class PromotionIdentifierListener private constructor(): IngredientChangesListener {

    private var identifiers = Arrays.asList(
            LightPromotionIdentifier(),
            ALotOfMeatPromotionIdentifier(),
            ALotOfCheesePromotionIdentifier()
    )

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
                instance = PromotionIdentifierListener()
            }
            return instance!!
        }
    }
}
