package com.jgeniselli.desafio.burgers.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.data.Ingredient
import kotlinx.android.synthetic.main.activity_ingredient_selection.*
import java.text.NumberFormat
import java.util.*

class IngredientSelectionActivity : AppCompatActivity() {

    private val adapter = IngredientsRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_selection)

        val viewModel = ViewModelProviders.of(this).get(IngredientSelectionViewModel::class.java)

        viewModel.result.observe(this, Observer {
            if (it != null)
                updateContent(it)
        })

        viewModel.start(RequestBundle.empty())

        with(recycler_view) {
            adapter = this@IngredientSelectionActivity.adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
    }

    private fun updateContent(it: List<Ingredient>) {
        val descriptions = convertResult(it)
        adapter.updateContent(descriptions)
    }

    private fun convertResult(it: List<Ingredient>): List<IngredientDescription> {
        val extraAmounts = intent.getSerializableExtra("INGREDIENTS")
        if (extraAmounts != null && extraAmounts is HashMap<*, *>) {
            return mergeAmounts(extraAmounts, it)
        }
        return startAmounts(it)
    }

    private fun mergeAmounts(extraAmounts: HashMap<*, *>, ingredients: List<Ingredient>): List<IngredientDescription> {
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
}

class SimpleIngredientDescription(val ingredient: Ingredient, private var amount: Int) : IngredientDescription {
    override fun getIngredientName(): String = ingredient.name

    override fun getAmount(): String = amount.toString()

    override fun setAmount(amount: Int) {
        this.amount = amount
    }

    override fun getIngredientImage(): String = ingredient.image

    override fun getIngredientPrice(): String {
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        return format.format(ingredient.price)
    }


}