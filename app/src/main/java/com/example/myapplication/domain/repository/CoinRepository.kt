package com.example.myapplication.domain.repository

import com.example.myapplication.data.remote.model.Coin
import com.example.myapplication.data.remote.model.GetCoins
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinRepository {
    suspend fun getCoins(
        @Query("search") search: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): GetCoins

    suspend fun getCoinByUdid(
        @Path("udid") udid: String,
    ): Coin
}