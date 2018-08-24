package com.jgeniselli.desafio.burgers.commons

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jgeniselli.desafio.burgers.R

import com.jgeniselli.desafio.burgers.data.promotions.Promotion

class PromotionsListAdapter(context: Context) : ArrayAdapter<Promotion>(context, 0) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = when (convertView) {
            null -> LayoutInflater.from(context)
                    .inflate(R.layout.line_view_promotion, parent, false)
            else -> convertView
        }
        val item = getItem(position)
        view.findViewById<TextView>(R.id.title_text)?.text = item?.name
        view.findViewById<TextView>(R.id.description_text)?.text = item?.description
        return view
    }
}
