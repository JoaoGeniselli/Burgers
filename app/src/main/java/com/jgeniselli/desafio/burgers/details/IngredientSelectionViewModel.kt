package com.jgeniselli.desafio.burgers.details

import android.arch.lifecycle.MutableLiveData
import com.jgeniselli.desafio.burgers.commons.Event
import com.jgeniselli.desafio.burgers.commons.RequestViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.Ingredient
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSource
import com.jgeniselli.desafio.burgers.data.source.BurgersDataSourceCacheProxy
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import io.reactivex.Single
import java.util.*
import kotlin.collections.HashMap

class IngredientSelectionViewModel : RequestViewModel<List<IngredientDescription>, AmountsRequestBundle>() {

    private var service: BurgersDataSource? = null

    val finishForResult = MutableLiveData<Event<HashMap<Int, Int>>>()

    override fun makeRequest(bundle: AmountsRequestBundle): Single<List<IngredientDescription>> {
        service ?: apply {
            val api = RetrofitFactory.createAPI()
            service = BurgersDataSourceCacheProxy(BurgersService(api))
        }
        return service!!.findAllIngredients()
                .map { convertResult(it, bundle) }
    }

    private fun convertResult(ingredients: List<Ingredient>,
                              bundle: AmountsRequestBundle): List<IngredientDescription> {
        return when {
            bundle.amounts != null -> mergeAmounts(bundle.amounts, ingredients)
            else -> startAmounts(ingredients)
        }
    }

    private fun mergeAmounts(extraAmounts: HashMap<*, *>,
                             ingredients: List<Ingredient>): List<IngredientDescription> {
        val descriptions = ArrayList<IngredientDescription>()
        ingredients.forEach {
            val amount = when {
                extraAmounts[it.id] == null -> 0
                else -> extraAmounts[it.id] as Int
            }
            descriptions.add(SimpleIngredientDescription(it, amount))
        }
        return descriptions
    }

    private fun startAmounts(ingredients: List<Ingredient>): List<IngredientDescription> {
        val emptyMap = HashMap<Int, Int>()
        return mergeAmounts(emptyMap, ingredients)
    }

    fun doneClicked() {
        val updatedAmounts = updateAmountsAfterDone()
        finishForResult.value = Event(updatedAmounts)
    }

    private fun updateAmountsAfterDone(): HashMap<Int, Int> {
        val updatedAmounts = HashMap<Int, Int>()
        if (result.value == null) return updatedAmounts
        result.value!!.forEach {
            updatedAmounts[it.getIngredientId()] = it.getAmount()
        }
        return updatedAmounts
    }

    override fun locksResult(): Boolean = true
}