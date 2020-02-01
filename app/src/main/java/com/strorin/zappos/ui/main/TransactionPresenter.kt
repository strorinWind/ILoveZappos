package com.strorin.zappos.ui.main

import android.util.Log
import com.strorin.zappos.network.ApiLoader
import com.strorin.zappos.network.OrderBookDTO
import com.strorin.zappos.network.TransactionDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Response
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit


@InjectViewState
class TransactionPresenter: MvpPresenter<TransactionHistoryView>() {

    companion object {
        private const val CURRENCY_PAIR = "btcusd"
    }

    private val compositeDisposable = CompositeDisposable()


    override fun attachView(view: TransactionHistoryView?) {
        super.attachView(view)
        getHistory(view)
        getOrderBook()
//        Schedulers.io().schedulePeriodicallyDirect({ getOrderBook()}, 0, 1000, TimeUnit.MILLISECONDS)
    }

    override fun detachView(view: TransactionHistoryView?) {
        super.detachView(view)
        compositeDisposable.clear();
    }

    private fun getHistory(view: TransactionHistoryView?){
        view?.setLoading()
        val disposable = ApiLoader
            .bitstampApi
            .transactionHistory(CURRENCY_PAIR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::checkHistoryResponseAndShowState, this::handleErrorFromHistory)
        compositeDisposable.add(disposable)
    }

    private fun getOrderBook() {
        val disposable = ApiLoader
            .bitstampApi
            .orderBook(CURRENCY_PAIR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::checkOrderBookResponse)
        compositeDisposable.add(disposable)
    }

    private fun checkHistoryResponseAndShowState(response: Response<List<TransactionDTO>>){
        if (response.isSuccessful) {
            val body = response.body()
            if (viewState != null && body != null) {
                viewState.showGraph(body)
            }
        } else {
            //TODO
            //showError
        }
    }

    fun scheduleUpdate() {
        getOrderBook()
//        Schedulers.io().scheduleDirect({getOrderBook()}, 500, TimeUnit.MILLISECONDS)
    }

    private fun handleErrorFromHistory(e: Throwable){
        //TODO
        //showError
        Log.d("TESTTEST", e.localizedMessage)
    }

    private fun checkOrderBookResponse(response: Response<OrderBookDTO>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                viewState.setBidsAndAsks(body.bids, mutableListOf())
            }
        }
    }
}