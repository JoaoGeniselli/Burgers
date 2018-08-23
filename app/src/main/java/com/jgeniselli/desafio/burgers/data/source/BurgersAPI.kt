package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.BurgerData
import retrofit2.Call
import retrofit2.http.GET

interface BurgersAPI {

    @GET("/api/lanche")
    fun getBurgers(): Call<List<BurgerData>>
}