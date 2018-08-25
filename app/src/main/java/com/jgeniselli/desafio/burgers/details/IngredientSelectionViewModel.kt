package com.jgeniselli.desafio.burgers.details

import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.commons.RequestViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.Ingredient
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSourceCacheProxy
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import io.reactivex.Single

class IngredientSelectionViewModel : RequestViewModel<List<Ingredient>, RequestBundle>() {

    private var service: BurgersDataSource? = null

    override fun makeRequest(bundle: RequestBundle): Single<List<Ingredient>> {
        service ?: apply {
            val api = RetrofitFactory.createAPI()
            service = BurgersDataSourceCacheProxy(BurgersService(api))
        }
        return service!!.findAllIngredients()
    }
}