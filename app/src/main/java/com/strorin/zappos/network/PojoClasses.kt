package com.strorin.zappos.network

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