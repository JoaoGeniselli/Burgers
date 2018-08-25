package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import com.jgeniselli.desafio.burgers.data.promotions.PromotionIdentifierListener
import io.reactivex.Single
import io.reactivex.Single.create

class PromotionsObserverProxy(private val child: BurgersDataSource) : BurgersDataSource {

    override fun findAllBurgers(): Single<List<Burger>> {
        return create { emitter ->
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

    override fun findBurgerById(id: Int): Single<Burger> {
        return create { emitter ->
            child.findBurgerById(id)
                    .subscribe({
                        val listener = PromotionIdentifierListener.getDefault()
                        it.addIngredientChangesListener(listener)
                        emitter.onSuccess(it)
                    }, {
                        emitter.onError(it)
                    })
        }
    }
}