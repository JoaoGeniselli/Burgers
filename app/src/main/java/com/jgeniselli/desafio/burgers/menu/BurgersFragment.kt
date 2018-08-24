package com.jgeniselli.desafio.burgers.menu

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.data.Burger
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
        viewModel = ViewModelProviders.of(activity!!).get(BurgersViewModel::class.java)
        observeBurgers()
        observeError()
        observeLoading()
        setupRecycler()
        viewModel.start()
    }

    private fun observeLoading() {
        viewModel.loading.observe(this, Observer {
            progress_bar.visibility = when {
                (it == null || !it) -> View.GONE
                else -> View.VISIBLE
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
        viewModel.error.observe(this, Observer {
            if (it != null) {
                displayDialog(it)
                viewModel.error.value = null
            }
        })
    }

    private fun observeBurgers() {
        viewModel.burgers.observe(this, Observer {
            if (it != null) {
                updateContent(it)
            }
        })
    }

    private fun updateContent(burgers: List<Burger>) {
        val descriptions = createDescriptions(burgers)
        adapter.updateContent(descriptions)
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
