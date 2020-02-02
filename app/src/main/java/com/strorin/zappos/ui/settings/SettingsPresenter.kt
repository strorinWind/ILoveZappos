package com.strorin.zappos.ui.settings

import android.content.Context
import com.strorin.zappos.data.PersistentStorage
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SettingsPresenter(
    private val context: Context
): MvpPresenter<SettingsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val p = PersistentStorage.getTrackingPrice(context)
        if (p != (-1).toFloat()) {
            viewState.setPrice(p)
        }
    }

    fun onSaveButtonClicked(price: Float) {
        PersistentStorage.savePriceToTrack(context, price)
        viewState.showSaved()
    }
}