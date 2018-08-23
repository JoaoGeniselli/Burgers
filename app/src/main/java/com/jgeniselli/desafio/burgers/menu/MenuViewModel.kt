package com.jgeniselli.desafio.burgers.menu

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jgeniselli.desafio.burgers.commons.RetrofitFactory
import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.data.Ingredient
import com.jgeniselli.desafio.burgers.data.source.BurgersService
import java.util.logging.Handler

class MenuViewModel : ViewModel() {

    val burgers: MutableLiveData<List<Burger>> = MutableLiveData()

    fun start() {
        if (burgers.value == null) {


            val api = RetrofitFactory.createAPI()
            val service = BurgersService(api)

            val models: List<Burger> = service.findAllBurgers()
            burgers.value = models


//            val models = ArrayList<Burger>()
//            var burger = Burger(1, "X-Bacon", "http://4.bp.blogspot.com/-04M3ljOud1Q/UO9owTBtSYI/AAAAAAAAAsc/gq0c1-knR-o/s1600/double_cheeseburger_bacon_0.png")
//            burger.addIngredient(Ingredient.makeForPrice(1, 1.5, "Test"), 2)
//
//            burger = Burger(1, "X-Bacon", "http://4.bp.blogspot.com/-04M3ljOud1Q/UO9owTBtSYI/AAAAAAAAAsc/gq0c1-knR-o/s1600/double_cheeseburger_bacon_0.png")
//            burger.addIngredient(Ingredient.makeForPrice(1, 1.5, "Pão com Gergelim"), 2)
//            burger.addIngredient(Ingredient.makeForPrice(2, 2.0, "Bacon"), 2)
//            models.add(burger)
//
//            burger = Burger(1, "X-Bacon", "http://4.bp.blogspot.com/-04M3ljOud1Q/UO9owTBtSYI/AAAAAAAAAsc/gq0c1-knR-o/s1600/double_cheeseburger_bacon_0.png")
//            burger.addIngredient(Ingredient.makeForPrice(1, 1.5, "Test"), 2)
//            models.add(burger)
//
//            burger = Burger(1, "X-Bacon", "http://4.bp.blogspot.com/-04M3ljOud1Q/UO9owTBtSYI/AAAAAAAAAsc/gq0c1-knR-o/s1600/double_cheeseburger_bacon_0.png")
//            burger.addIngredient(Ingredient.makeForPrice(1, 1.5, "Test"), 2)
//            models.add(burger)

        }
    }
}


/*

    {
        "id": 1,
        "name": "X-Bacon",
        "ingredients": [
            2,
            3,
            5,
            6
        ],
        "image": "https://goo.gl/W9WdaF"


 */
