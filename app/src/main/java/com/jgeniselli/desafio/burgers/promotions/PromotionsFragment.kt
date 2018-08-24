package com.jgeniselli.desafio.burgers.promotions

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.commons.PromotionsListAdapter
import kotlinx.android.synthetic.main.fragment_promotions.*

class PromotionsFragment : Fragment() {

    private lateinit var adapter: PromotionsListAdapter
    private lateinit var viewModel: PromotionsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_promotions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = getString(R.string.promotions)
        adapter = PromotionsListAdapter(context!!)
        viewModel = ViewModelProviders.of(activity!!).get(PromotionsViewModel::class.java)
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
        AlertDialog.Builder(activity)
                .setTitle(R.string.warning)
                .setMessage(message)
                .setNeutralButton(R.string.ok, null)
                .show()
    }
}
