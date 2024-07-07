package com.example.myapplication.data.remote.model

import com.example.myapplication.data.remote.dto.StatsDto

data class Stats(
    val total: Int,
    val totalCoins: Int,
)

fun StatsDto.toStats():Stats{
    return Stats(
        total,
        totalCoins
    )
}
