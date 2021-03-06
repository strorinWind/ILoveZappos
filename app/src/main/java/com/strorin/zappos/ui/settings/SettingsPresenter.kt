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

    private val JOB_TAG = "price_btcusd_notification"

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val p = PersistentStorage.getTrackingPrice(context)
        if (p != (-1).toFloat()) {
            viewState.setPrice(p)
        }
        if (PersistentStorage.isNotificationEnabled(context)) {
            viewState.setEnabled(true)
        }
    }

    fun onSaveButtonClicked(price: Float) {
        PersistentStorage.savePriceToTrack(context, price)
        viewState.showSaved()
        scheduleTracking()
    }

    fun onFeatureSwitched(enabled: Boolean) {
        viewState.setEnabled(enabled)
        PersistentStorage.setNotificationEnabled(context, enabled)
        if (enabled && PersistentStorage.getTrackingPrice(context) != (-1).toFloat()) {
            scheduleTracking()
        } else {
            JobDispatcher.initDispatcher(context)
            JobDispatcher.dispatcher.cancel(JOB_TAG)
        }
    }

    private fun scheduleTracking() {
        val periodicity =
            TimeUnit.MINUTES.toSeconds(50).toInt()
        val toleranceInterval = TimeUnit.MINUTES.toSeconds(70).toInt()

        JobDispatcher.initDispatcher(context)
        val job = JobDispatcher.dispatcher
            .newJobBuilder()
            .setService(MyJobService::class.java)
                .setTag(JOB_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(periodicity, toleranceInterval))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
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