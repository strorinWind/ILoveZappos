package com.strorin.zappos.data

import android.content.Context

object PersistentStorage {
    private const val PRICE_PREFERENCES = "price_preferences"
    private const val PRICE_FIELD = "price"
    private const val NOTIFICATION_ENABLED = "notification_enabled"

    fun savePriceToTrack(context: Context, price: Float) {
        val sharedPreferences =
            context.getSharedPreferences(PRICE_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat(PRICE_FIELD, price)
        editor.apply()
    }

    fun getTrackingPrice(context: Context): Float {
        val sharedPreferences =
            context.getSharedPreferences(PRICE_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getFloat(PRICE_FIELD, (-1).toFloat())
    }

    fun setNotificationEnabled(context: Context, enabled: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences(PRICE_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(NOTIFICATION_ENABLED, enabled)
        editor.apply()
    }

    fun isNotificationEnabled(context: Context): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(PRICE_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(NOTIFICATION_ENABLED, false)
    }
}