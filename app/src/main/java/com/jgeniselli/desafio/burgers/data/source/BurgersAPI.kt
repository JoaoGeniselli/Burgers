package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.BurgerData
import com.jgeniselli.desafio.burgers.data.order.OrderData
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import java.io.Serializable

interface BurgersAPI {

    @GET("lanche")
    fun getBurgers(): Single<List<BurgerData>>

    @GET("ingrediente/de/{id}")
    fun getIngredientsForBurger(@Path("id") burgerId: Int): Single<List<IngredientData>>

    @GET("promocao")
    fun getPromotions(): Single<List<PromotionData>>

    @GET("ingrediente")
    fun getIngredients(): Single<List<IngredientData>>

    @GET("lanche/{id}")
    fun getBurgerById(@Path("id") id: Int): Single<BurgerData>

    @PUT("pedido/{id}")
    fun putBurgerToCart(@Path("id") id: Int, @Body extras: ExtrasRequest): Single<Any>

    @GET("pedido")
    fun getCart(): Single<List<OrderData>>
}

data class ExtrasRequest(val extras: String) : Serializable