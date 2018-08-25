package com.jgeniselli.desafio.burgers.promotions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.commons.PromotionsListAdapter
import com.jgeniselli.desafio.burgers.commons.RequestBundle
import displaySimpleAlert
import kotlinx.android.synthetic.main.fragment_promotions.*

class PromotionsFragment : Fragment() {

    private lateinit var adapter: PromotionsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_promotions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = getString(R.string.promotions)
        setupList()
        val viewModel = ViewModelProviders.of(activity!!).get(PromotionsViewModel::class.java)
        observeViewModel(viewModel)
        viewModel.start(RequestBundle.empty())
    }

    private fun setupList() {
        adapter = PromotionsListAdapter(context!!)
        list_view.adapter = adapter
    }

    private fun observeViewModel(viewModel: PromotionsViewModel) {
        viewModel.errorMessage.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                displaySimpleAlert(it)
            }
        })
        viewModel.loadingState.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                progress_bar.visibility = when (it) {
                    false -> View.GONE
                    true -> View.VISIBLE
                }
            }
        })
        viewModel.result.observe(this, Observer {
            if (it != null) {
                adapter.clear()
                adapter.addAll(it)
            }
        })
    }

}


