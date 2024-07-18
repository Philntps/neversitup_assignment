package com.example.myapplication.model.remote.model

import com.example.myapplication.model.remote.dto.StatsDto

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
