package com.strorin.zappos.ui.history

import com.strorin.zappos.network.ApiLoader
import com.strorin.zappos.network.OrderBookDTO
import com.strorin.zappos.network.TransactionDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Response
import io.reactivex.disposables.CompositeDisposable


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
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear();
    }

    fun onTryAgainButtonClicked(){
        getHistory(viewState)
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
            .bitstampApiNoCache
            .orderBook(CURRENCY_PAIR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::checkOrderBookResponse) { /* ignore*/}
        compositeDisposable.add(disposable)
    }

    private fun checkHistoryResponseAndShowState(response: Response<List<TransactionDTO>>){
        if (response.isSuccessful) {
            val body = response.body()
            if (viewState != null) {
                if (body != null) {
                    viewState.showGraph(body)
                } else {
                    viewState.showErrorLoading()
                }
            }
        } else {
            viewState.showErrorLoading()
        }
    }

    fun scheduleUpdate() {
        getOrderBook()
    }

    private fun handleErrorFromHistory(e: Throwable){
        viewState.showErrorLoading()
    }

    private fun checkOrderBookResponse(response: Response<OrderBookDTO>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                viewState.setBidsAndAsks(body.bids, body.asks)
            }
        }
    }
}