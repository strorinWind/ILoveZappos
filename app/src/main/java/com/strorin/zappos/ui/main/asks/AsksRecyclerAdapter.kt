package com.strorin.zappos.ui.main.asks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.strorin.zappos.R


class AsksRecyclerAdapter(
    context: Context
): RecyclerView.Adapter<AsksViewHolder>() {

    private var askList = ArrayList<List<Float>>()
    private val inflater = LayoutInflater.from(context);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsksViewHolder {
        return AsksViewHolder(
            inflater.inflate(R.layout.ask_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return askList.size
    }

    override fun onBindViewHolder(holder: AsksViewHolder, position: Int) {
        holder.bind(askList[position])
    }

    fun setDataset(asksList: List<List<Float>>) {
        askList.clear()
        askList.addAll(asksList)
        notifyDataSetChanged()
    }
}