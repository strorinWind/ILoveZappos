package com.strorin.zappos.ui.main.bids

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.strorin.zappos.R

class BidRecyclerAdapter(
    context: Context
): RecyclerView.Adapter<BidsViewHolder>() {

    private var bidList = ArrayList<List<Float>>()
    private val inflater = LayoutInflater.from(context);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BidsViewHolder {
        return BidsViewHolder(
            inflater.inflate(R.layout.bid_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return bidList.size
    }

    override fun onBindViewHolder(holder: BidsViewHolder, position: Int) {
        holder.bind(bidList[position])
    }

    fun setDataset(bidsList: List<List<Float>>) {
        bidList.clear()
        bidList.addAll(bidsList)
        notifyDataSetChanged()
    }
}