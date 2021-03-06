package com.strorin.zappos.network

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface BitstampApi {

    @GET("transactions/{currency_pair}/")
    fun transactionHistory(@Path("currency_pair") currencyPair: String): Single<Response<List<TransactionDTO>>>

    @GET("order_book/{currency_pair}/")
    fun orderBook(@Path("currency_pair") currencyPair: String): Single<Response<OrderBookDTO>>

    @GET("ticker_hour/{currency_pair}/")
    fun ticketHour(@Path("currency_pair") currencyPair: String): Single<Response<TicketHour>>
}