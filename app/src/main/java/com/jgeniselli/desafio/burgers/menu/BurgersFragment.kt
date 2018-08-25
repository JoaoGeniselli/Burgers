package com.jgeniselli.desafio.burgers.menu

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.commons.RequestBundle
import com.jgeniselli.desafio.burgers.data.IBurger
import displaySimpleAlert
import kotlinx.android.synthetic.main.fragment_burgers.*

class BurgersFragment : Fragment(), BurgersRecyclerViewAdapter.ItemClickListener {

    private var adapter: BurgersRecyclerViewAdapter = BurgersRecyclerViewAdapter(this)
    private lateinit var viewModel: BurgersViewModel

    override fun onPositionClicked(position: Int) {
        viewModel.selectedPosition.value = position
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_burgers, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = getString(R.string.burgers)
        viewModel = ViewModelProviders.of(activity!!).get(BurgersViewModel::class.java)
        observeBurgers()
        observeError()
        observeLoading()
        setupRecycler()
        viewModel.start(RequestBundle.empty())
    }

    private fun observeLoading() {
        viewModel.loadingState.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                progress_bar.visibility = when (it) {
                    false -> View.GONE
                    true -> View.VISIBLE
                }
            }
        })
    }

    private fun setupRecycler() {
        with(recycler_view) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@BurgersFragment.adapter
        }
    }

    private fun observeError() {
        viewModel.errorMessage.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                displaySimpleAlert(it)
            }
        })
    }

    private fun observeBurgers() {
        viewModel.result.observe(this, Observer {
            if (it != null)
                updateContent(it)
        })
    }

    private fun updateContent(burgers: List<IBurger>) {
        val descriptions = createDescriptions(burgers)
        adapter.updateContent(descriptions)
    }

    private fun createDescriptions(burgers: List<IBurger>): List<BurgerDescription> {
        return burgers.map { BurgerToDescriptionAdapter(it) }
    }
}
