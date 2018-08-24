package com.jgeniselli.desafio.burgers.menu

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSourceCacheProxy
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import com.jgeniselli.desafio.burgers.data.source.PromotionsObserverProxy
import io.reactivex.android.schedulers.AndroidSchedulers

class BurgersViewModel : ViewModel() {

    val burgers = MutableLiveData<List<Burger>>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val selectedPosition = MutableLiveData<Int>()

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

        service!!.findAllBurgers()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .doAfterTerminate{ loading.value = false }
                .subscribe({ burgers.value = it }, { error.value = it.message })
    }
}