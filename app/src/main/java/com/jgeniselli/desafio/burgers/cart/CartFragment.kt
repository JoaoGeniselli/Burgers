package com.jgeniselli.desafio.burgers.cart

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.data.order.Order
import displaySimpleAlert
import kotlinx.android.synthetic.main.fragment_burgers.*

class CartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        viewModel.result.observe(this, Observer {
            updateContent(it)
        })
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
    }

    private fun updateContent(orders: List<Order>?) {

    }

}
