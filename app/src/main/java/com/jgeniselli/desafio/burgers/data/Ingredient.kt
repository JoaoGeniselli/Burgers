package com.jgeniselli.desafio.burgers.data

class Ingredient private constructor(val price: Double) {

    companion object {

        fun makeForPrice(price: Double): Ingredient {
            ensureValidPrice(price)
            return Ingredient(price)
        }

        fun ensureValidPrice(price: Double) {
            if (price < 0.0) throw InvalidPriceException()
        }
    }


}