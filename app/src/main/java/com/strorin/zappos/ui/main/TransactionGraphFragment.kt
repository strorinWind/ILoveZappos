package com.strorin.zappos.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.strorin.zappos.R
import com.strorin.zappos.network.TransactionDTO
import com.github.mikephil.charting.data.LineData
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class TransactionGraphFragment : MvpAppCompatFragment(), TransactionHistoryView {

    companion object {
        fun newInstance() = TransactionGraphFragment()
    }


    @InjectPresenter
    lateinit var presenter: TransactionPresenter

    private lateinit var viewModel: TransactionHistoryViewModel

    private lateinit var chart: LineChart
    private lateinit var progress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  inflater.inflate(R.layout.transaction_graph_fragment, container, false)
        chart = view.findViewById(R.id.transaction_chart)
        progress = view.findViewById(R.id.progress)
        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(TransactionHistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun setLoading() {
        chart.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }

    override fun showGraph(data: List<TransactionDTO>) {
        chart.visibility = View.VISIBLE
        progress.visibility = View.GONE

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

}
