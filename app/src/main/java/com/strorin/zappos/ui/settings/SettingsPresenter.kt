package com.strorin.zappos.ui.settings

import android.content.Context
import com.strorin.zappos.data.PersistentStorage
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SettingsPresenter: MvpPresenter<SettingsView>() {

    fun onSaveButtonClicked(price: Float, context: Context) {
        PersistentStorage.savePriceToTrack(context, price)
        viewState.showSaved()
    }
}