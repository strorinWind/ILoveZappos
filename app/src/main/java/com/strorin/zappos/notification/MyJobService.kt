package com.strorin.zappos.notification

import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.strorin.zappos.BuildConfig
import com.strorin.zappos.data.PersistentStorage
import com.strorin.zappos.network.ApiLoader
import com.strorin.zappos.network.TicketHour
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MyJobService : JobService() {
    private val CURRENCY_PAIR = "btcusd"

    private val compositeDisposable = CompositeDisposable()


    override fun onStartJob(job: JobParameters): Boolean {
        // Do some work here

        Log.d("TESTTEST", "jobStarted")
        val disposable = ApiLoader
            .bitstampApiNoCache
            .ticketHour(CURRENCY_PAIR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::checkTickerHourResponse) { }

        compositeDisposable.add(disposable)
        return true // Answers the question: "Is there still work going on?"
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return false // Answers the question: "Should this job be retried?"
    }

    private fun checkTickerHourResponse(response: Response<TicketHour>) {
        if (BuildConfig.DEBUG) {
            Log.d("TESTTEST", "response came")
            Log.d("TESTTEST", response.message())
            Log.d("TESTTEST", response.body()?.toString() ?: "")
        }

        compositeDisposable.clear()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                if (body.last < PersistentStorage.getTrackingPrice(applicationContext)) {
                    //create Notification
                    if (BuildConfig.DEBUG) {
                        Log.d("TESTTEST", "current price is below the target")
                    }
                    NotificationResolver(applicationContext).createPriceNotification(body.last)
                }
            }
        }
    }



}