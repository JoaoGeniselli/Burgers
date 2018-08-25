package com.jgeniselli.desafio.burgers.data.source

import android.util.SparseArray
import com.jgeniselli.desafio.burgers.data.CustomBurger
import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.Ingredient
import com.jgeniselli.desafio.burgers.data.order.Order
import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import io.reactivex.Single

class BurgersDataSourceCacheProxy(private val child: BurgersDataSource) : BurgersDataSource {

    private var allBurgersCache: Single<List<Burger>>? = null
    private var allIngredientsCache: Single<List<Ingredient>>? = null
    private var allPromotionsCache: Single<List<Promotion>>? = null
    private var burgersByIdCache = SparseArray<Single<Burger>>()

    override fun findAllBurgers(): Single<List<Burger>> {
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

    override fun findBurgerById(id: Int): Single<Burger> {
        if (burgersByIdCache[id] == null) {
            burgersByIdCache.put(id, child.findBurgerById(id).cache())
        }
        return burgersByIdCache[id]
    }

    override fun addToCart(burger: CustomBurger): Single<Any> {
        return child.addToCart(burger)
    }

    override fun findAllIngredients(): Single<List<Ingredient>> {
        allIngredientsCache ?: apply {
            allIngredientsCache = child.findAllIngredients().cache()
        }
        return allIngredientsCache!!
    }

    override fun getCart(): Single<List<Order>> {
        return child.getCart()
    }
}