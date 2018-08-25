package com.jgeniselli.desafio.burgers.menu

import android.arch.lifecycle.MutableLiveData
import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.commons.RequestViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSourceCacheProxy
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import io.reactivex.Single

class BurgersViewModel : RequestViewModel<List<Burger>, RequestBundle>() {

    val selectedPosition = MutableLiveData<Int>()
    private var service: BurgersDataSource? = null
    
    override fun makeRequest(bundle: RequestBundle): Single<List<Burger>> {
        service ?: apply {
            val api = RetrofitFactory.createAPI()
            service = BurgersDataSourceCacheProxy(BurgersService(api))
        }
        return service!!.findAllBurgers()
    }
}