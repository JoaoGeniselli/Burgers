package com.jgeniselli.desafio.burgers.promotions

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.commons.PromotionsListAdapter
import kotlinx.android.synthetic.main.activity_promotions.*

class PromotionsActivity : AppCompatActivity() {

    private lateinit var adapter: PromotionsListAdapter

    private lateinit var viewModel: PromotionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotions)
        adapter = PromotionsListAdapter(this)
        viewModel = ViewModelProviders.of(this).get(PromotionsViewModel::class.java)
        observeViewModel()
        setupList()
        viewModel.start()
    }

    private fun setupList() {
        list_view.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.error.observe(this, Observer {
            if (it != null) {
                displayDialog(it)
                viewModel.error.value = null
            }
        })
        viewModel.loading.observe(this, Observer {
            progress_bar.visibility = when {
                (it == null || !it) -> View.GONE
                else -> View.VISIBLE
            }
        })
        viewModel.promotions.observe(this, Observer {
            it ?: ArrayList()
            adapter.clear()
            adapter.addAll(it!!)
        })
    }

    private fun displayDialog(message: String) {
        AlertDialog.Builder(this)
                .setTitle(R.string.warning)
                .setMessage(message)
                .setNeutralButton(R.string.ok, null)
                .show()
    }
}
