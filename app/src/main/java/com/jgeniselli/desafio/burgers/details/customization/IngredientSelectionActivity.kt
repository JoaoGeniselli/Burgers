package com.jgeniselli.desafio.burgers.details.customization

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.jgeniselli.desafio.burgers.R
import kotlinx.android.synthetic.main.activity_ingredient_selection.*

class IngredientSelectionActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_INGREDIENTS = "INGREDIENTS"
    }

    private val adapter = IngredientsRecyclerViewAdapter()

    private lateinit var viewModel: IngredientSelectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_selection)
        viewModel = ViewModelProviders.of(this).get(IngredientSelectionViewModel::class.java)
        observeViewModel()
        setupRecycler()
        val amounts = getAmountsFromIntent()
        viewModel.start(AmountsRequestBundle(amounts))
    }

    private fun setupRecycler() {
        with(recycler_view) {
            adapter = this@IngredientSelectionActivity.adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
    }

    private fun observeViewModel() {
        viewModel.result.observe(this, Observer {
            if (it != null) {
                empty_view.visibility = when {
                    it.isEmpty() -> View.VISIBLE
                    else -> View.GONE
                }
                adapter.updateContent(it)
            }
        })
        viewModel.finishForResult.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                finalize(it)
            }
        })
    }

    private fun finalize(updatedAmounts: HashMap<Int, Int>) {
        val data = Intent()
        data.putExtra(EXTRA_INGREDIENTS, updatedAmounts)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun getAmountsFromIntent(): HashMap<Int, Int> {
        val extraAmounts = intent.getSerializableExtra(EXTRA_INGREDIENTS) as HashMap<*, *>?
        var amounts = HashMap<Int, Int>()
        if (extraAmounts != null) {
            amounts = extraAmounts as HashMap<Int, Int>
        }
        return amounts
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.burger_customization, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.done) {
            viewModel.doneClicked()
        }
        return super.onOptionsItemSelected(item)
    }
}

