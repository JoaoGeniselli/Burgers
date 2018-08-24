package com.jgeniselli.desafio.burgers.menu

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jgeniselli.desafio.burgers.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.line_view_burger.view.*

class BurgersRecyclerViewAdapter(private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<BurgersRecyclerViewAdapter.ViewHolder>() {

    private var values: List<BurgerDescription> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.line_view_burger, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        with(holder) {
            nameView.text = item.getName()
            priceView.text = item.getFormattedPrice()
            ingredientsView.text = item.getIngredientNames()
            Picasso.get()
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_local_dining_black_24dp)
                    .noFade()
                    .into(imageView)
        }

        with(holder.mView) {
            tag = item.getBurgerId()
            setOnClickListener {
                itemClickListener.onPositionClicked(holder.layoutPosition)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    fun updateContent(descriptions: List<BurgerDescription>) {
        values = descriptions
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val nameView: TextView = mView.name_view
        val priceView: TextView = mView.price_view
        val ingredientsView: TextView = mView.ingredients_view
        val imageView: ImageView = mView.icon_view
    }

    interface ItemClickListener {
        fun onPositionClicked(position: Int)
    }
}
