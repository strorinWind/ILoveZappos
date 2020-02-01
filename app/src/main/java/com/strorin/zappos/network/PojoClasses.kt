package com.strorin.zappos.network

import java.sql.Timestamp

enum class TransactionType(int: Int){
    Buy(0),
    Sell(1)
}

data class TransactionDTO(
    val date: Long,
    val tid: Long,
    val price: Double,
    val amount: Double,
    val type: TransactionType
)

data class OrderBookDTO(
    val timestamp: Long,
    val bids: List<List<Float>>,
    val asks: List<List<Float>>
)

