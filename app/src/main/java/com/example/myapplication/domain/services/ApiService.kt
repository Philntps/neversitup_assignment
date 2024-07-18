package com.example.myapplication.domain.services

import com.example.myapplication.model.remote.dto.GetCoinByUdidDto
import com.example.myapplication.model.remote.dto.GetCoinsDto
import com.example.myapplication.model.remote.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("coins")
    suspend fun getCoins(
        @Query("search") search: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<GetCoinsDto>

    @GET("coin/{udid}")
    suspend fun getCoinByUdid(
        @Path("udid") udid: String,
    ): Response<GetCoinByUdidDto>
}