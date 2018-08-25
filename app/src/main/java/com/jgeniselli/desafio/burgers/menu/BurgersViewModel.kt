package com.jgeniselli.desafio.burgers.menu

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.commons.Injection
import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.commons.RequestViewModel
import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import io.reactivex.Single

class BurgersViewModel(application: Application) : RequestViewModel<List<Burger>, RequestBundle>(application) {

    val selectedPosition = MutableLiveData<Int>()
    private var service: BurgersDataSource? = null

    override fun makeRequest(bundle: RequestBundle): Single<List<Burger>> {
        service ?: apply {
            service = Injection.getDefaultDataSource()
        }
        return service!!.findAllBurgers()
    }

    override fun defaultErrorMessage(): Int = R.string.default_menu_error
}