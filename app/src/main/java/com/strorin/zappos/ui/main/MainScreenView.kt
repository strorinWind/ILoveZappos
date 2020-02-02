package com.strorin.zappos.ui.main

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainScreenView: MvpView {
    fun showState(state: MainScreenState)
}

enum class MainScreenState {
    HistoryGraph,
    Settings
}