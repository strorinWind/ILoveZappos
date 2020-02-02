package com.strorin.zappos.ui.main.orderbook.bids

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.strorin.zappos.R

class BidsViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val amount: TextView = view.findViewById(R.id.amount)
    private val price: TextView = view.findViewById(R.id.price)

    fun bind(bidEntity: List<Float>) {
        price.text = bidEntity[0].toString()
        amount.text = bidEntity[1].toString()
    }
}