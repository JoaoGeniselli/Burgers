package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.CustomBurger
import com.jgeniselli.desafio.burgers.data.IBurger
import com.jgeniselli.desafio.burgers.data.Ingredient
import com.jgeniselli.desafio.burgers.data.Order
import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import io.reactivex.Single

interface BurgersDataSource {
    fun findAllBurgers(): Single<List<IBurger>>
    fun findAllPromotions(): Single<List<Promotion>>
    fun findBurgerById(id: Int): Single<IBurger>
    fun findAllIngredients(): Single<List<Ingredient>>
    fun addToCart(burger: CustomBurger): Single<Any>
    fun getCart(): Single<List<Order>>
}