package com.jgeniselli.desafio.burgers.commons

import com.jgeniselli.desafio.burgers.data.source.BurgersAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class RetrofitFactory {

    companion object {
        var retrofitInstance: Retrofit? = null

        private fun getDefaultRetrofit(): Retrofit {
            retrofitInstance ?: synchronized(this) {
                retrofitInstance = Retrofit.Builder()
                        .baseUrl("http://192.168.0.103:8080/api/")
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofitInstance!!
        }

        fun createAPI(): BurgersAPI {
            return getDefaultRetrofit().create(BurgersAPI::class.java)
        }
    }
}
