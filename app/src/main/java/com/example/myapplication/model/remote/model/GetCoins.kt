package com.example.myapplication.model.remote.model

import com.example.myapplication.model.remote.dto.GetCoinsDto

data class GetCoins(
    val coins:List<Coin>,
    val stat:Stats
)

fun GetCoinsDto.toGetCoins():GetCoins{
    return GetCoins(
        coins = coins.map { it.toCoin() },
        stat = stats.toStats()
    )
}
