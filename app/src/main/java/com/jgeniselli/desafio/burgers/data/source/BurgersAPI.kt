package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.BurgerData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BurgersAPI {

    @GET("api/lanche")
    fun getBurgers(): Single<List<BurgerData>>

    @GET("api/ingrediente/de/{id}")
    fun getIngredientsForBurger(@Path("id") burgerId: Int): Single<List<IngredientData>>
}