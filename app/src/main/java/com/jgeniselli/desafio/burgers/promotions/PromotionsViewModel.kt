package com.jgeniselli.desafio.burgers.promotions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.Promotion
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import io.reactivex.android.schedulers.AndroidSchedulers

class PromotionsViewModel : ViewModel() {

    val promotions = MutableLiveData<List<Promotion>>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun start() {
        val api = RetrofitFactory.createAPI()
        val service = BurgersService(api)

        service.findAllPromotions()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .doAfterTerminate { loading.value = false }
                .subscribe({ promotions.value = it }, { error.value = it.message })
    }
}
