package com.jgeniselli.desafio.burgers.data.promotions

import com.jgeniselli.desafio.burgers.data.Ingredient
import com.jgeniselli.desafio.burgers.data.source.PromotionData

class Promotion(val id: Int, val name: String, val description: String, private var discountCalculator: DiscountCalculator?) {
    fun getDiscountFor(ingredients: List<Ingredient>): Double =
            discountCalculator?.calculateDiscount(totalPrice(ingredients)) ?: 0.0

    private fun totalPrice(ingredients: List<Ingredient>): Double {
        var price = 0.0
        ingredients.forEach { price += it.price }
        return price
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Promotion) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }

    companion object {
        fun valueOf(data: PromotionData) : Promotion {
            with(data){
                return Promotion(id, name, description, null)
            }
        }

        fun valuesOf(datas: List<PromotionData>) : List<Promotion> {
            val promotions = ArrayList<Promotion>()
            datas.forEach { promotions.add(valueOf(it)) }
            return promotions
        }

        fun makeLight(): Promotion {
            return Promotion(1, "Light", "",
                    PercentageDiscountCalculator.makeForPercentage(10.0))
        }

        fun makeALotOfMeat(amount: Int): Promotion {
            val discount = (amount / 3).toDouble()
            return Promotion(2, "Muita Carne", "",
                    SubtractionDiscountCalculator.makeForSubtrahend(discount))
        }

        fun makeALotOfCheese(amount: Int): Promotion {
            val discount = (amount / 3).toDouble()
            return Promotion(2, "Muito Queijo", "",
                    SubtractionDiscountCalculator.makeForSubtrahend(discount))
        }
    }
}