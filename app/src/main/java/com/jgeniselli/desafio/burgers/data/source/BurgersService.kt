package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.BurgerData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BurgersService(val api: BurgersAPI) : BurgersRepository {

    override fun findAllBurgers(): List<Burger> {
        val call = api.getBurgers()
        val dtos = call.execute()

        call.enqueue(callback { response, thorwable ->
            response?.let {
                
            }
            thorwable?.let {
                // ação caso o throwable for retornado
            }
        })

        return createBurgersFrom(dtos.body())
    }

    fun <T> callback(fn: (Throwable?, Response<T>?) -> Unit): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) = fn(null, response)
            override fun onFailure(call: Call<T>, t: Throwable) = fn(t, null)
        }
    }

    private fun createBurgersFrom(dtos: List<BurgerData>?): List<Burger> {
        if (dtos == null) return ArrayList()
        val burgers = ArrayList<Burger>()
        dtos.forEach {
            burgers.add(Burger.valueOf(it))
        }
        return burgers
    }
}
