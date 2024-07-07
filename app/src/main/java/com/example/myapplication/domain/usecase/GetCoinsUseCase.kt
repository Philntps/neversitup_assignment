package com.example.myapplication.domain.usecase

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import com.example.myapplication.data.remote.model.GetCoins
import com.example.myapplication.domain.repository.CoinRepository
import com.example.myapplication.domain.services.ApiResponse
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    companion object {
        const val TAG = "GetCoinsUseCase"
    }

    operator fun invoke(
        search: String = "",
        offset: Int = 0,
        limit: Int = 20
    ): Flow<ApiResponse<GetCoins>> = flow {
        try {
            emit(ApiResponse.Loading())
            val res = coinRepository.getCoins(search, offset, limit)
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