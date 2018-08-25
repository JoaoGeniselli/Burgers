package com.jgeniselli.desafio.burgers.commons

import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSourceCacheProxy
import com.jgeniselli.desafio.burgers.data.source.BurgersService

object Injection {

    fun getDefaultDataSource(): BurgersDataSource {
        val api = RetrofitFactory.createAPI()
        return BurgersDataSourceCacheProxy(BurgersService(api))
    }
}