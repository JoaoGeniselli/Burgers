package com.jgeniselli.desafio.burgers.data.source

import android.util.SparseArray
import com.jgeniselli.desafio.burgers.data.IBurger
import com.jgeniselli.desafio.burgers.data.Ingredient
import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import io.reactivex.Single

class BurgersDataSourceCacheProxy(private val child: BurgersDataSource) : BurgersDataSource {

    private var allBurgersCache: Single<List<IBurger>>? = null
    private var allIngredientsCache: Single<List<Ingredient>>? = null
    private var allPromotionsCache: Single<List<Promotion>>? = null
    private var burgersByIdCache = SparseArray<Single<IBurger>>()

    override fun findAllBurgers(): Single<List<IBurger>> {
        allBurgersCache ?: apply {
            allBurgersCache = child.findAllBurgers().cache()
        }
        return allBurgersCache!!
    }

    override fun findAllPromotions(): Single<List<Promotion>> {
        allPromotionsCache ?: apply {
            allPromotionsCache = child.findAllPromotions().cache()
        }
        return allPromotionsCache!!
    }

    override fun findBurgerById(id: Int): Single<IBurger> {
        if (burgersByIdCache[id] == null) {
            burgersByIdCache.put(id, child.findBurgerById(id).cache())
        }
        return burgersByIdCache[id]
    }

    override fun addToCart(burger: IBurger): Single<Any> {
        return child.addToCart(burger)
    }

    override fun findAllIngredients(): Single<List<Ingredient>> {
        allIngredientsCache ?: apply {
            allIngredientsCache = child.findAllIngredients().cache()
        }
        return allIngredientsCache!!
    }
}