package com.jgeniselli.desafio.burgers.details

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.data.IBurger
import com.jgeniselli.desafio.burgers.details.IngredientSelectionActivity.Companion.EXTRA_INGREDIENTS
import com.jgeniselli.desafio.burgers.menu.BurgerToDescriptionAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_BURGER_ID = "BURGER_ID"
        const val REQUEST_CUSTOMIZE = 600
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        observeViewModel()
        setupButtons()
        val id = intent.getIntExtra(EXTRA_BURGER_ID, 0)
        viewModel.start(IdRequestBundle(id))
    }

    private fun setupButtons() {
        add_to_cart_btn.setOnClickListener {
            viewModel.addToCartButtonClicked()
        }
        customize_btn.setOnClickListener {
            redirectToCustomization()
        }
    }

    private fun observeViewModel() {
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (customizationReceived(requestCode, resultCode, data)) {
            val updatedAmounts = getAmountsFromIntent(data!!)
            viewModel.updateBurger(updatedAmounts)
        }
    }

    private fun getAmountsFromIntent(intent: Intent): HashMap<Int, Int> {
        val extraAmounts = intent.getSerializableExtra(EXTRA_INGREDIENTS) as HashMap<*, *>?
        var amounts = HashMap<Int, Int>()
        if (extraAmounts != null) {
            amounts = extraAmounts as HashMap<Int, Int>
        }
        return amounts
    }

    private fun customizationReceived(requestCode: Int, resultCode: Int, data: Intent?) =
            requestCode == REQUEST_CUSTOMIZE && resultCode == Activity.RESULT_OK && data != null

    private fun redirectToCustomization() {
        val customIngredients = viewModel.getCustomIngredientsForSelection()
        val intent = Intent(this, IngredientSelectionActivity::class.java)
        intent.putExtra(EXTRA_INGREDIENTS, customIngredients)
        startActivityForResult(intent, REQUEST_CUSTOMIZE)
    }

    private fun finalize() {
        AlertDialog.Builder(this)
                .setMessage(R.string.success_on_add_to_cart)
                .setNeutralButton(R.string.ok) { _, _ -> finish() }
                .show()
    }

    private fun updateContent(burger: IBurger) {
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
