package com.jgeniselli.desafio.burgers.commons

import com.jgeniselli.desafio.burgers.data.source.BurgersAPI
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {



    companion object {

        var retrofitInstance: Retrofit? = null

        @Synchronized
        fun getDefaultRetrofit(): Retrofit {
            retrofitInstance ?: apply {
                val converterFactory: Converter.Factory = GsonConverterFactory.create()
                retrofitInstance = Retrofit.Builder()
                        .baseUrl("http://localhost:8080")
                        .addConverterFactory(converterFactory)
                        .build()
            }
            return retrofitInstance!!
        }

        fun createAPI(): BurgersAPI {
            return getDefaultRetrofit().create(BurgersAPI::class.java)
        }
    }



}
