package com.example.myapplication.domain.usecase

import android.util.Log
import com.example.myapplication.data.remote.model.Coin
import com.example.myapplication.data.remote.model.GetCoins
import com.example.myapplication.domain.repository.CoinRepository
import com.example.myapplication.domain.services.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    companion object {
        const val TAG = "GetCoinsUseCase"
    }

    operator fun invoke(udid:String
    ): Flow<ApiResponse<Coin>> = flow {
        try {
            emit(ApiResponse.Loading())
            val res = coinRepository.getCoinByUdid(udid)
            Log.d(TAG, res.toString())
            emit(ApiResponse.Success(res))
        } catch (e: HttpException) {
            Log.e(TAG, e.localizedMessage ?: "An unexpected error occurred")
            throw e
        } catch (e: IOException) {
            Log.e(
                TAG,
                "Couldn't reach server. Check your internet connection \n${e.message}"
            )
            throw e
        } catch (e: Exception) {
            throw e
        }
    }.catch { e ->
        Log.e(
            TAG,
            "Couldn't reach server. Check your internet connection \n${e.message}"
        )
        emit(ApiResponse.Error(e.message))
    }
}