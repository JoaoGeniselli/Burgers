package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.Burger

interface BurgersRepository {
    fun findAllBurgers(): List<Burger>
}