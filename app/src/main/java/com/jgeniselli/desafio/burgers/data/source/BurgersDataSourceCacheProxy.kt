package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.Promotion
import io.reactivex.Single
import java.util.*
import java.util.Collections.unmodifiableList
import kotlin.collections.ArrayList

class BurgersDataSourceCacheProxy(private val child: BurgersDataSource) : BurgersDataSource {

    private val allBurgers = ArrayList<Burger>()
    private val allPromotions = ArrayList<Promotion>()

    override fun findAllBurgers(): Single<List<Burger>> {
        if (allBurgers.isNotEmpty()) {
            return Single.create { emitter ->
                emitter.onSuccess(Collections.unmodifiableList(allBurgers))
            }
        }
        return Single.create { emitter ->
            child.findAllBurgers().subscribe({
                it ?: ArrayList()
                allBurgers.addAll(it)
                emitter.onSuccess(it)
            }, {
                allBurgers.clear()
                emitter.onError(it)
            })
        }
    }

    override fun findAllPromotions(): Single<List<Promotion>> {
        if (allPromotions.isNotEmpty()) {
            return Single.create { emitter ->
                emitter.onSuccess(unmodifiableList(allPromotions))
            }
        }
        return Single.create { emitter ->
            child.findAllPromotions().subscribe({
                it ?: ArrayList()
                allPromotions.addAll(it)
                emitter.onSuccess(it)
            }, {
                allPromotions.clear()
                emitter.onError(it)
            })
        }
    }
}