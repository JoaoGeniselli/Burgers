package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.Burger
import io.reactivex.Single

interface BurgersDataSource {
    fun findAllBurgers(): Single<List<Burger>>
}