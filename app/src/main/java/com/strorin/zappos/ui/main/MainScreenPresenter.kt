package com.strorin.zappos.ui.main

import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainScreenPresenter: MvpPresenter<MainScreenView>() {

    private var currentState: MainScreenState = MainScreenState.HistoryGraph

    fun onGraphClicked(){
        if (currentState != MainScreenState.HistoryGraph) {
            currentState = MainScreenState.HistoryGraph
            viewState.showState(MainScreenState.HistoryGraph)
        }
    }

    fun onSettingsClicked() {
        if (currentState != MainScreenState.Settings) {
            currentState = MainScreenState.Settings
            viewState.showState(MainScreenState.Settings)
        }
    }
}