package com.jgeniselli.desafio.burgers.promotions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.Promotion
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSourceCacheProxy
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import com.jgeniselli.desafio.burgers.data.source.PromotionsObserverProxy
import io.reactivex.android.schedulers.AndroidSchedulers

class PromotionsViewModel : ViewModel() {

    val promotions = MutableLiveData<List<Promotion>>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    private var service: BurgersDataSource? = null

    fun start() {
        service ?: apply {
            val api = RetrofitFactory.createAPI()
            service = PromotionsObserverProxy(
                    BurgersDataSourceCacheProxy(
                            BurgersService(api)
                    )
            )
        }

        service!!.findAllPromotions()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .doAfterTerminate { loading.value = false }
                .subscribe({ promotions.value = it }, { error.value = it.message })
    }
}
