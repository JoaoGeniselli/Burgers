package com.jgeniselli.desafio.burgers.details

import android.arch.lifecycle.MutableLiveData
import android.util.SparseArray
import com.jgeniselli.desafio.burgers.commons.Event
import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.commons.RequestViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.CustomBurger
import com.jgeniselli.desafio.burgers.data.Ingredient
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSourceCacheProxy
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import io.reactivex.Single

class DetailsViewModel : RequestViewModel<CustomBurger, IdRequestBundle>() {

    val successAndFinish = MutableLiveData<Event<String>>()
    private var service: BurgersDataSource? = null

    override fun makeRequest(bundle: IdRequestBundle): Single<CustomBurger> {
        service ?: apply {
            val api = RetrofitFactory.createAPI()
            service = BurgersDataSourceCacheProxy(BurgersService(api))
        }
        return service!!.findBurgerById(bundle.id)
                .map { CustomBurger(it) }

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

    fun getCustomIngredientsForSelection() : HashMap<Int, Int> {
        if (result.value == null) return HashMap()
        return convert(result.value!!.getExtraIngredientsForSelection())
    }

    private fun convert(map: Map<Ingredient, Int>): HashMap<Int, Int> {
        val intMap: HashMap<Int, Int> = HashMap()
        map.forEach {
            intMap[it.key.id] = it.value
        }
        return intMap
    }
}

data class IdRequestBundle(val id: Int) : RequestBundle
