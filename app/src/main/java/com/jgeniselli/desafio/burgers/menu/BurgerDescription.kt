package com.jgeniselli.desafio.burgers.menu

interface BurgerDescription {
    fun getName(): String
    fun getImageUrl(): String
    fun getIngredientNames(): String
    fun getFormattedPrice(): String
}