package com.jgeniselli.desafio.burgers.cart

import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.commons.RequestViewModel
import com.jgeniselli.desafio.burgers.data.Order
import io.reactivex.Single

class CartViewModel : RequestViewModel<List<Order>, RequestBundle>() {

    override fun makeRequest(bundle: RequestBundle): Single<List<Order>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}