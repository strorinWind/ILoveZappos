package com.strorin.zappos

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class BitstampApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}
