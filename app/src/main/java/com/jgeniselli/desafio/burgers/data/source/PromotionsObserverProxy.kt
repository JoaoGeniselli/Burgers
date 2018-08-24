package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.Promotion
import com.jgeniselli.desafio.burgers.data.PromotionIdentifierListener
import io.reactivex.Single

class PromotionsObserverProxy(private val child: BurgersDataSource) : BurgersDataSource {

    override fun findAllBurgers(): Single<List<Burger>> {
        return Single.create { emitter ->
            child.findAllBurgers()
                    .subscribe({
                        addObserving(it)
                        emitter.onSuccess(it)
                    }, {
                        emitter.onError(it)
                    })
        }
    }

    private fun addObserving(burgers: List<Burger>?) {
        val listener = PromotionIdentifierListener.getDefault()
        burgers?.forEach {
            it.addIngredientChangesListener(listener)
        }
    }

    override fun findAllPromotions(): Single<List<Promotion>> {
        return child.findAllPromotions()
    }
}