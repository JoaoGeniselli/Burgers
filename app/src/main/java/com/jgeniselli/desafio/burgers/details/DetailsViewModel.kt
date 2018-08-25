package com.jgeniselli.desafio.burgers.details

import android.arch.lifecycle.MutableLiveData
import com.jgeniselli.desafio.burgers.commons.Event
import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.commons.RequestViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.IBurger
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSourceCacheProxy
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import io.reactivex.Single

class DetailsViewModel : RequestViewModel<IBurger, IdRequestBundle>() {

    val successAndFinish = MutableLiveData<Event<String>>()
    private var service: BurgersDataSource? = null

    override fun makeRequest(bundle: IdRequestBundle): Single<IBurger> {
        service ?: apply {
            val api = RetrofitFactory.createAPI()
            service = BurgersDataSourceCacheProxy(BurgersService(api))

        }
        return service!!.findBurgerById(bundle.id)
    }

    fun addToCartButtonClicked() {
        if (result.value == null || service == null) return
        attachThreadAndLoading(service!!.addToCart(result.value!!))
                .subscribe({
                    successAndFinish.value = Event("Adicionado com Sucesso")
                }, {
                    postError("Ocorreu um erro ao adicionar lanche")
                })
    }
}

data class IdRequestBundle(val id: Int) : RequestBundle
