package com.jgeniselli.desafio.burgers.menu

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.commons.ItemOffsetDecoration
import com.jgeniselli.desafio.burgers.data.Burger

class BurgersFragment : Fragment() {

    var adapter: BurgersRecyclerViewAdapter = BurgersRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_burgers, container, false)
        val viewModel = ViewModelProviders.of(activity!!).get(BurgersViewModel::class.java)
        observeBurgers(viewModel)
        observeError(viewModel)
        setupRecycler(view)
        viewModel.start()
        return view
    }

    private fun setupRecycler(view: View?) {
        if (view is RecyclerView) {
            with(view) {
                layoutManager = GridLayoutManager(context, 2)
                adapter = this@BurgersFragment.adapter
            }
        }
    }

    private fun observeError(viewModel: BurgersViewModel) {
        viewModel.error.observe(this, Observer {
            if (it != null) {
                displayDialog(it)
            }
        })
    }

    private fun observeBurgers(viewModel: BurgersViewModel) {
        viewModel.burgers.observe(this, Observer {
            if (it != null) {
                updateContent(it)
            }
        })
    }

    private fun updateContent(burgers: List<Burger>) {
        val mainHandler = Handler(context?.mainLooper)
        mainHandler.post({
            val descriptions = createDescriptions(burgers)
            adapter.updateContent(descriptions)
        })
    }

    private fun createDescriptions(burgers: List<Burger>): List<BurgerDescription> {
        val descriptions = ArrayList<BurgerDescription>()
        burgers.forEach {
            descriptions.add(BurgerToDescriptionAdapter(it))
        }
        return descriptions
    }

    private fun displayDialog(message: String) {
        AlertDialog.Builder(context)
                .setTitle(R.string.warning)
                .setMessage(message)
                .setNeutralButton(R.string.ok, null)
                .show()
    }
}