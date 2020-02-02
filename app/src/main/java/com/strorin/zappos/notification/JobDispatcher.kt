package com.strorin.zappos.notification

import android.content.Context
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver

object JobDispatcher {
    lateinit var dispatcher: FirebaseJobDispatcher

    fun initDispatcher(context: Context){
        if (!::dispatcher.isInitialized){
            dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
        }
    }
}