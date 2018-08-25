package com.jgeniselli.desafio.burgers.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.data.Burger
import com.jgeniselli.desafio.burgers.menu.BurgerToDescriptionAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_BURGER_ID = "BURGER_ID"
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)

        viewModel.result.observe(this, Observer {
            if (it != null)
                updateContent(it)
        })

        viewModel.loadingState.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                progress_bar.visibility = when (it) {
                    false -> View.GONE
                    true -> View.VISIBLE
                }
            }
        })

        viewModel.successAndFinish.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                finalize()
            }
        })

        val id = intent.getIntExtra(EXTRA_BURGER_ID, 0)

        viewModel.start(IdRequestBundle(id))

        add_to_cart_btn.setOnClickListener {
            viewModel.addToCartButtonClicked()
        }
    }

    private fun finalize() {
        AlertDialog.Builder(this)
                .setMessage(R.string.success_on_add_to_cart)
                .setNeutralButton(R.string.ok) { _, _ -> finish() }
                .show()
    }

    private fun updateContent(burger: Burger) {
        val description = BurgerToDescriptionAdapter(burger)
        Picasso.get()
                .load(description.getImageUrl())
                .placeholder(R.drawable.ic_local_dining_black_24dp)
                .into(image_view)

        name_view.text = description.getName()
        price_view.text = description.getFormattedPrice()
        ingredients_view.text = description.getIngredientNames()
    }
}
