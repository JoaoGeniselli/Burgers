package com.jgeniselli.desafio.burgers.data

class Ingredient private constructor(val id: Int, val price: Double, val name: String) {

    companion object {

        fun makeForPrice(id: Int, price: Double, name: String): Ingredient {
            ensureValidPrice(price)
            return Ingredient(id, price, name)
        }

        private fun ensureValidPrice(price: Double) {
            if (price < 0.0) throw InvalidPriceException()
        }
    }


}