package com.strorin.zappos.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.strorin.zappos.R
import com.strorin.zappos.network.TransactionDTO
import com.github.mikephil.charting.data.LineData
import com.strorin.zappos.ui.main.asks.AsksRecyclerAdapter
import com.strorin.zappos.ui.main.bids.BidRecyclerAdapter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class TransactionGraphFragment : MvpAppCompatFragment(), TransactionHistoryView {

    companion object {
        fun newInstance() = TransactionGraphFragment()
    }


    @InjectPresenter
    lateinit var presenter: TransactionPresenter


    private lateinit var chart: LineChart
    private lateinit var progress: ProgressBar
    private lateinit var bidsRecyclerView: RecyclerView
    private lateinit var asksRecyclerView: RecyclerView
    private lateinit var errorLayout: LinearLayout
    private lateinit var tryAgainBtn: Button

    private lateinit var bidAdapter: BidRecyclerAdapter
    private lateinit var asksAdapter: AsksRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  inflater.inflate(R.layout.transaction_graph_fragment, container, false)

        findViews(view)
        setupUi(inflater.context)

        return  view
    }

    override fun onStart() {
        super.onStart()
        tryAgainBtn.setOnClickListener { presenter.onTryAgainButtonClicked() }
    }

    override fun onStop() {
        super.onStop()
        tryAgainBtn.setOnClickListener(null)
    }

    override fun setLoading() {
        chart.visibility = View.GONE
        progress.visibility = View.VISIBLE
        errorLayout.visibility = View.GONE
    }

    override fun showGraph(data: List<TransactionDTO>) {
        chart.visibility = View.VISIBLE
        progress.visibility = View.GONE
        errorLayout.visibility = View.GONE

        val entries: MutableList<Entry> = mutableListOf<Entry>();
        for (entry: TransactionDTO in data) {
            entries.add(Entry(entry.date.toFloat(), entry.price.toFloat()))
        }

        val dataSet = LineDataSet(entries, "BTCUSD")
        dataSet.color = R.color.colorPrimary;
        dataSet.valueTextColor = R.color.colorAccent

        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.description = null
        chart.xAxis.setDrawLabels(false)
        chart.invalidate()
    }

    override fun setBidsAndAsks(bids: List<List<Float>>, asks: List<List<Float>>) {
        bidAdapter.setDataset(bids)
        asksAdapter.setDataset(asks)

        presenter.scheduleUpdate()
    }

    override fun showErrorLoading() {
        errorLayout.visibility = View.VISIBLE
        progress.visibility = View.GONE
        chart.visibility = View.GONE
    }

    private fun setupUi(context: Context){
        bidsRecyclerView.layoutManager = LinearLayoutManager(context)
        asksRecyclerView.layoutManager = LinearLayoutManager(context)

        bidAdapter = BidRecyclerAdapter(context)
        bidsRecyclerView.adapter = bidAdapter
        asksAdapter = AsksRecyclerAdapter(context)
        asksRecyclerView.adapter = asksAdapter
    }

    private fun findViews(view: View){
        chart = view.findViewById(R.id.transaction_chart)
        progress = view.findViewById(R.id.progress_graph)
        bidsRecyclerView = view.findViewById(R.id.bids_rv)
        asksRecyclerView = view.findViewById(R.id.asks_rv)
        errorLayout = view.findViewById(R.id.error_layout)
        tryAgainBtn = view.findViewById(R.id.try_again_btn)
    }
}
