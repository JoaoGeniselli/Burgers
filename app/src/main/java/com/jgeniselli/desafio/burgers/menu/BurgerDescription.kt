package com.jgeniselli.desafio.burgers.menu

interface BurgerDescription {
    fun getBurgerId(): Int
    fun getName(): String
    fun getImageUrl(): String
    fun getIngredientNames(): String
    fun getFormattedPrice(): String
}