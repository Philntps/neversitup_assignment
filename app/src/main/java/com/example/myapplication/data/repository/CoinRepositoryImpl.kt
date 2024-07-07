package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.remote.model.Coin
import com.example.myapplication.data.remote.model.GetCoins
import com.example.myapplication.data.remote.model.toCoin
import com.example.myapplication.data.remote.model.toGetCoins
import com.example.myapplication.domain.services.ApiService
import com.example.myapplication.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CoinRepository {
    override suspend fun getCoins(search: String, offset: Int, limit: Int): GetCoins {
        return try {
            Log.d("Network","call  getCoins")
            val response = apiService.getCoins(
                search,
                offset,
                limit
            )
            Log.d("Network","$response")
            response.data!!.toGetCoins()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCoinByUdid(udid: String): Coin {
        return try {
            Log.d("Network","call  getCoinByUdid")
            val response = apiService.getCoinByUdid(udid)
            Log.d("Network","$response")
            response.data!!.coin.toCoin()
        } catch (e: Exception) {
            throw e
        }
    }


}