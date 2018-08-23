package com.jgeniselli.desafio.burgers.data

import java.io.Serializable

data class BurgerData(val id: Int, val name: String, val image: String, val ingredients: List<Int>) : Serializable