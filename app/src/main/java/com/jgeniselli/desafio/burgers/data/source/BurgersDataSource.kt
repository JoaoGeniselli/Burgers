package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import io.reactivex.Single

interface BurgersDataSource {
    fun findAllBurgers(): Single<List<Burger>>
    fun findAllPromotions(): Single<List<Promotion>>
    fun findBurgerById(id: Int): Single<Burger>
}