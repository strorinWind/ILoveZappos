package com.strorin.zappos.ui.main

import androidx.lifecycle.ViewModel
import com.strorin.zappos.network.ApiLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TransactionHistoryViewModel : ViewModel() {
    companion object {
        private const val CURRENCY_PAIR = "btcusd"
    }

    fun newsCanBeLoaded(view: TransactionHistoryView){
        view.setLoading()

//        val disposable = ApiLoader
//            .bitstampApi
//            .transactionHistory(CURRENCY_PAIR)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(this::checkResponseAndShowState, this::handleError)

    }


}
