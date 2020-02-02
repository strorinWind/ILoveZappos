package com.strorin.zappos.ui.main.orderbook

import androidx.recyclerview.widget.DiffUtil

class OrderBookEntityDiffCallback(
    private val oldList: List<List<Float>>,
    private val newList: List<List<Float>>
): DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame(oldItemPosition, newItemPosition)
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        return newItem[0] == oldItem[0] && newItem[1] == oldItem[1]
    }
}