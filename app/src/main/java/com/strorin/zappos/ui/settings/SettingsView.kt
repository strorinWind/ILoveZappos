package com.strorin.zappos.ui.settings

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SettingsView: MvpView {

    fun setEnabled(enabled: Boolean)

    fun setPrice(price: Float)

    fun showSaved()
}