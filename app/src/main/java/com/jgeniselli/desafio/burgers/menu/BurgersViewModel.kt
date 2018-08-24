package com.jgeniselli.desafio.burgers.menu

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import io.reactivex.android.schedulers.AndroidSchedulers

class BurgersViewModel : ViewModel() {

    val burgers = MutableLiveData<List<Burger>>()
    val error = MutableLiveData<String>()
    val selectedPosition = MutableLiveData<Int>()

    fun start() {
        if (burgers.value != null) return

        val api = RetrofitFactory.createAPI()
        val service = BurgersService(api)

        service.findAllBurgers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ burgers.value = it }, { error.value = it.message })
    }
}