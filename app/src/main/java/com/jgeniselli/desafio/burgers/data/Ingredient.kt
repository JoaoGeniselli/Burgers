package com.jgeniselli.desafio.burgers.data

import com.jgeniselli.desafio.burgers.data.source.IngredientData

class Ingredient private constructor(val id: Int, val price: Double, val name: String, image: String) {

    companion object {

        fun makeForPrice(id: Int, price: Double, name: String): Ingredient {
            ensureValidPrice(price)
            return Ingredient(id, price, name, "")
        }

        private fun ensureValidPrice(price: Double) {
            if (price < 0.0) throw InvalidPriceException()
        }

        fun valueOf(data: IngredientData): Ingredient = with(data) {
            Ingredient(id, price, name, image)
        }

        fun valuesOf(datas: List<IngredientData>): List<Ingredient> {
            val ingredients = ArrayList<Ingredient>(datas.size)
            datas.forEach {
                ingredients.add(valueOf(it))
            }
            return ingredients
        }
    }


}

