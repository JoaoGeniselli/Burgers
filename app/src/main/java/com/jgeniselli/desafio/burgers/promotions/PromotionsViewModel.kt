package com.jgeniselli.desafio.burgers.promotions

import android.app.Application
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.commons.RequestViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSourceCacheProxy
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import io.reactivex.Single

class PromotionsViewModel(application: Application) :
        RequestViewModel<List<Promotion>, RequestBundle>(application) {

    private var service: BurgersDataSource? = null

    override fun makeRequest(bundle: RequestBundle): Single<List<Promotion>> {
        service ?: apply {
            val api = RetrofitFactory.createAPI()
            service = BurgersDataSourceCacheProxy(BurgersService(api))
        }
        return service!!.findAllPromotions()
    }

    override fun defaultErrorMessage(): Int = R.string.default_promotions_message
}
