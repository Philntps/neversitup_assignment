package com.example.myapplication.data.remote.model

import com.example.myapplication.data.remote.dto.CoinDto

data class Coin(
    val change: String?=null,
    val color: String?=null,
    val description: String? = null,
    val iconUrl: String,
    val marketCap: String?=null,
    val name: String,
    val price: String?,
    val rank: Int,
    val symbol: String,
    val uuid: String,
    val websiteUrl: String? = null
)

fun CoinDto.toCoin(): Coin {
    return Coin(
        iconUrl = iconUrl,
        symbol = symbol,
        color = color,
        description = description,
        name = name,
        change = change,
        marketCap = marketCap,
        rank = rank,
        uuid = uuid,
        price = price,
        websiteUrl = websiteUrl
    )
}
