package com.strorin.zappos.ui.main

import android.util.Log
import com.strorin.zappos.network.ApiLoader
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
        view?.setLoading()
        val disposable = ApiLoader
            .bitstampApi
            .transactionHistory(CURRENCY_PAIR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::checkResponseAndShowState, this::handleError)
        compositeDisposable.add(disposable);
    }

    override fun detachView(view: TransactionHistoryView?) {
        super.detachView(view)
        compositeDisposable.clear();
    }

    private fun checkResponseAndShowState(response: Response<List<TransactionDTO>>){
//        viewState.showGraph(response)
        if (response.isSuccessful) {
            val body = response.body()
            if (viewState != null && body != null) {
                viewState.showGraph(body)
            }
        } else {
            //showError
        }
    }

    private fun handleError(e: Throwable){
        //showError
        Log.d("TESTTEST", e.localizedMessage)
    }
}