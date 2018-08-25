package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.*
import com.jgeniselli.desafio.burgers.data.order.Order
import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.Single.create
import io.reactivex.SingleEmitter
import io.reactivex.schedulers.Schedulers

class BurgersService(private val api: BurgersAPI) : BurgersDataSource {

    override fun findAllBurgers(): Single<List<Burger>> {
        return create { emitter: SingleEmitter<List<Burger>> ->
            makeBurgersStream().subscribe({ burgers ->
                getIngredients(burgers).doOnComplete {
                    emitter.onSuccess(burgers)
                }.subscribe()
            }, {
                emitter.onError(it)
            })
        }
    }

    override fun findAllPromotions(): Single<List<Promotion>> {
        return api.getPromotions()
                .subscribeOn(Schedulers.io())
                .map { Promotion.valuesOf(it) }
    }

    private fun makeBurgersStream(): Single<List<Burger>> =
            api.getBurgers()
                    .subscribeOn(Schedulers.io())
                    .map { MenuBurger.valuesOf(it) }

    private fun getIngredients(burgers: List<Burger>): Flowable<List<Ingredient>> {
        val streams = ArrayList<Single<List<Ingredient>>>()
        burgers.forEach { burger ->
            val single = makeIngredientsStream(burger)
            streams.add(single)
        }
        return Single.merge(streams)
                .subscribeOn(Schedulers.io())
    }

    override fun addToCart(burger: CustomBurger): Single<Any> {
        return api.putBurgerToCart(burger.getId(), ExtrasRequest(extractExtras(burger)))
                .subscribeOn(Schedulers.io())
    }

    private fun extractExtras(burger: CustomBurger): String {
        val ids = ArrayList<Int>()
        burger.getExtraIngredientsForSelection().forEach {
            if (it.value != 0) {
                for (i in 1..it.value)
                    ids.add(it.key.id)
            }
        }
        return ids.joinToString(prefix = "[", separator = ",", postfix = "]")
    }

    private fun makeIngredientsStream(burger: Burger): Single<List<Ingredient>> =
            api.getIngredientsForBurger(burger.getId())
                    .map { Ingredient.valuesOf(it) }
                    .doOnSuccess { ingredients ->
                        addIngredientsToBurger(burger, ingredients)
                    }

    override fun findAllIngredients(): Single<List<Ingredient>> =
            api.getIngredients()
                    .subscribeOn(Schedulers.io())
                    .map { Ingredient.valuesOf(it) }

    private fun addIngredientsToBurger(burger: Burger, ingredients: List<Ingredient>?) {
        if (ingredients == null) return
        ingredients.forEach {
            burger.addIngredient(it, 1)
        }
    }

    override fun findBurgerById(id: Int): Single<Burger> {
        return create { emitter ->
            makeBurgerIdStream(id).subscribe({ burger ->
                makeIngredientsStream(burger).subscribe({
                    emitter.onSuccess(burger)
                }, {
                    emitter.onError(it)
                })
            }, {
                emitter.onError(it)
            })
        }
    }

    private fun makeBurgerIdStream(id: Int) =
            api.getBurgerById(id)
                    .subscribeOn(Schedulers.io())
                    .map { MenuBurger.valueOf(it) }

    override fun getCart(): Single<List<Order>> {
        return create { emitter ->
            makeCartRequest().subscribe({
                getBurgersForOrders(it).doOnComplete {
                    getExtrasForOrders(it).subscribe({
                        emitter.onSuccess(it)
                    },{
                        emitter.onError(it)
                    })
                }.subscribe()
            }, {
                emitter.onError(it)
            })
        }
    }

    private fun getBurgersForOrders(orders: List<Order>): Flowable<Burger> {
        val streams = ArrayList<Single<Burger>>()
        orders.forEach { order ->
            val stream = findBurgerById(order.burgerId).doAfterSuccess {
                order.burger = it
            }
            streams.add(stream)
        }
        return Single.merge(streams)
                .subscribeOn(Schedulers.io())
    }

    private fun makeCartRequest() =
            api.getCart()
                    .subscribeOn(Schedulers.io())
                    .map { Order.valuesOf(it) }

    private fun getExtrasForOrders(orders: List<Order>): Single<List<Order>> {
        return create { emitter ->
            findAllIngredients().subscribe({
                bindIngredientsToOrders(it, orders)
                emitter.onSuccess(orders)
            }, {
                emitter.onError(it)
            })
        }
    }

    private fun bindIngredientsToOrders(ingredients: List<Ingredient>, orders: List<Order>) {
        orders.forEach { order ->
            for (i in 1.. order.extraIds.size) {
                val filtered = ingredients.filter { it.id == i }
                if (filtered.isNotEmpty())
                    order.burger?.addIngredient(filtered.first(), 1)
            }
        }
    }
}