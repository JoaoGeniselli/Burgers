package com.jgeniselli.desafio.burgers.menu

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
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.data.Burger
import kotlinx.android.synthetic.main.fragment_menu_list.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [MenuFragment.OnListFragmentInteractionListener] interface.
 */
class MenuFragment : Fragment() {

    private var columnCount = 2

    var adapter: BurgersRecyclerViewAdapter = BurgersRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_menu_list, container, false)

        val viewModel = ViewModelProviders.of(activity!!).get(MenuViewModel::class.java)
        viewModel.burgers.observe(this, Observer {
            if (it != null) {
                updateContent(it)
            }
        })

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = this@MenuFragment.adapter
            }
        }
        viewModel.start()
        return view
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
}
