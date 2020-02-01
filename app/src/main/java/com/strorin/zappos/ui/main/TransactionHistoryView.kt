package com.strorin.zappos.ui.main

import com.strorin.zappos.network.TransactionDTO
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TransactionHistoryView: MvpView {

    fun setLoading()

    fun showGraph(data: List<TransactionDTO>)
}