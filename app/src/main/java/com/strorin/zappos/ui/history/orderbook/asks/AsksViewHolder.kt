package com.strorin.zappos.ui.history.orderbook.asks

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.strorin.zappos.R

class AsksViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    private val amount: TextView = view.findViewById(R.id.amount)
    private val price: TextView = view.findViewById(R.id.price)

    fun bind(askEntity: List<Float>) {
        price.text = askEntity[0].toString()
        amount.text = askEntity[1].toString()
    }
}