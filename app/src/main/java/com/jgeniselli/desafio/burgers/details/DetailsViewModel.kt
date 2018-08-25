package com.jgeniselli.desafio.burgers.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSourceCacheProxy
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import com.jgeniselli.desafio.burgers.data.source.PromotionsObserverProxy
import io.reactivex.android.schedulers.AndroidSchedulers

class DetailsViewModel : ViewModel() {

    val burger = MutableLiveData<Burger>()
    private var service: BurgersDataSource? = null

    fun start(burgerId: Int) {
        service ?: apply {
            val api = RetrofitFactory.createAPI()
            service = PromotionsObserverProxy(
                    BurgersDataSourceCacheProxy(
                            BurgersService(api)
                    )
            )
        }

        service!!.findBurgerById(burgerId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {  }
                .doAfterTerminate{  }
                .subscribe({ burger.value = it }, {  })

    }
}
