package com.jgeniselli.desafio.burgers.cart

import android.app.Application
import com.jgeniselli.desafio.burgers.commons.Injection
import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.commons.RequestViewModel
import com.jgeniselli.desafio.burgers.data.order.Order
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import io.reactivex.Single

class CartViewModel(application: Application) : RequestViewModel<List<Order>, RequestBundle>(application) {

    private var service: BurgersDataSource? = null

    override fun makeRequest(bundle: RequestBundle): Single<List<Order>> {
        service ?: apply {
            service = Injection.getDefaultDataSource()
        }
        return service!!.getCart()
    }

}