package com.strorin.zappos.ui.settings

import android.content.Context
import android.util.Log
import com.firebase.jobdispatcher.Constraint
import com.firebase.jobdispatcher.Lifetime
import com.firebase.jobdispatcher.RetryStrategy
import com.firebase.jobdispatcher.Trigger
import com.strorin.zappos.BuildConfig
import com.strorin.zappos.data.PersistentStorage
import moxy.InjectViewState
import moxy.MvpPresenter
import com.strorin.zappos.notification.JobDispatcher
import com.strorin.zappos.notification.MyJobService
import java.util.concurrent.TimeUnit


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
        scheduleTracking()
    }

    private fun scheduleTracking() {
        val periodicity =
            TimeUnit.MINUTES.toSeconds(50).toInt() // Every 1 hour periodicity expressed as seconds
        val toleranceInterval = TimeUnit.MINUTES.toSeconds(70).toInt()

        JobDispatcher.initDispatcher(context)
        val job = JobDispatcher.dispatcher
            .newJobBuilder()
            .setService(MyJobService::class.java)
                .setTag("my-unique-tag")
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(periodicity, toleranceInterval))
                .setReplaceCurrent(true)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                    Constraint.ON_ANY_NETWORK
                )
                .build()

        if (BuildConfig.DEBUG) {
            Log.d("TESTTEST", job.toString())
        }

        JobDispatcher.dispatcher
            .mustSchedule(job)
    }
}