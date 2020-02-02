package com.strorin.zappos.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


object ApiLoader {
    private const val BASE_URL = "https://www.bitstamp.net/api/v2/"

    private val okHttpClient = OkHttpClient.Builder()
        .cache(null)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();

    private val retrofitNoCache = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();

    val bitstampApi = retrofit.create(BitstampApi::class.java);

    val bitstampApiNoCache = retrofitNoCache.create(BitstampApi::class.java)
}