package com.example.myapplication.data.remote.dto

data class GetCoinsDto(
    val coins: List<CoinDto>,
    val stats: StatsDto
)