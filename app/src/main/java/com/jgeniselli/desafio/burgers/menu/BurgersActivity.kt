package com.jgeniselli.desafio.burgers.menu

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.commons.replaceFragment

class BurgersActivity : AppCompatActivity() {

    private lateinit var viewModel: BurgersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_burgers)

        viewModel = ViewModelProviders.of(this).get(BurgersViewModel::class.java)

        viewModel.selectedPosition.observe(this, Observer { position ->
            if (position == null) return@Observer
            redirectToSelectedItem(position)
        })

        if (savedInstanceState == null)
            replaceFragment(BurgersFragment(), R.id.frame)
    }

    private fun redirectToSelectedItem(position: Int) {

    }
}
