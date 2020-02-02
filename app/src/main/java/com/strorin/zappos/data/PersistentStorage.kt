package com.strorin.zappos.data

import android.content.Context

object PersistentStorage {
    private const val PRICE_PREFERENCES = "price_preferences"
    private const val PRICE_FIELD = "price"

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
}