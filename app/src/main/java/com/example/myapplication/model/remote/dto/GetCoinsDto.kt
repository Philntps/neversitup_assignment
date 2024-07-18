package com.example.myapplication.model.remote.dto

data class GetCoinsDto(
    val coins: List<CoinDto>,
    val stats: StatsDto
)