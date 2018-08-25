package com.jgeniselli.desafio.burgers.data.source

import com.jgeniselli.desafio.burgers.data.IBurger
import com.jgeniselli.desafio.burgers.data.Ingredient
import com.jgeniselli.desafio.burgers.data.MenuBurger
import com.jgeniselli.desafio.burgers.data.promotions.Promotion
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.Single.create
import io.reactivex.SingleEmitter
import io.reactivex.schedulers.Schedulers

class BurgersService(private val api: BurgersAPI) : BurgersDataSource {

    override fun findAllBurgers(): Single<List<IBurger>> {
        return create { emitter: SingleEmitter<List<IBurger>> ->
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

    private fun makeBurgersStream(): Single<List<IBurger>> =
            api.getBurgers()
                    .subscribeOn(Schedulers.io())
                    .map { MenuBurger.valuesOf(it) }

    private fun getIngredients(burgers: List<IBurger>): Flowable<List<Ingredient>> {
        val streams = ArrayList<Single<List<Ingredient>>>()
        burgers.forEach { burger ->
            val single = makeIngredientsStream(burger)
            streams.add(single)
        }
        return Single.merge(streams)
                .subscribeOn(Schedulers.io())
    }

    override fun addToCart(burger: IBurger): Single<Any> =
            api.putBurgerToCart(burger.getId())
                    .subscribeOn(Schedulers.io())

    private fun makeIngredientsStream(burger: IBurger): Single<List<Ingredient>> =
            api.getIngredientsForBurger(burger.getId())
                    .map { Ingredient.valuesOf(it) }
                    .doOnSuccess { ingredients ->
                        addIngredientsToBurger(burger, ingredients)
                    }

    override fun findAllIngredients(): Single<List<Ingredient>> =
            api.getIngredients()
                    .subscribeOn(Schedulers.io())
                    .map { Ingredient.valuesOf(it) }

    private fun addIngredientsToBurger(burger: IBurger, ingredients: List<Ingredient>?) {
        if (ingredients == null) return
        ingredients.forEach {
            burger.addIngredient(it, 1)
        }
    }

    override fun findBurgerById(id: Int): Single<IBurger> {
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

}

