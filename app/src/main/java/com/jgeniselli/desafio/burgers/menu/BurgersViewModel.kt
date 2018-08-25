package com.jgeniselli.desafio.burgers.menu

import android.arch.lifecycle.MutableLiveData
import com.jgeniselli.desafio.burgers.commons.Injection
import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.commons.RequestViewModel
import com.jgeniselli.desafio.burgers.data.IBurger
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import io.reactivex.Single

class BurgersViewModel : RequestViewModel<List<IBurger>, RequestBundle>() {

    val selectedPosition = MutableLiveData<Int>()
    private var service: BurgersDataSource? = null

    override fun makeRequest(bundle: RequestBundle): Single<List<IBurger>> {
        service ?: apply {
            service = Injection.getDefaultDataSource()
        }
        return service!!.findAllBurgers()
    }
}