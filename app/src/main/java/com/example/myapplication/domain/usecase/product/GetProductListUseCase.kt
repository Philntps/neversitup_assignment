package com.example.myapplication.domain.usecase.product

import android.util.Log
import com.example.myapplication.domain.repository.DepartmentRepository
import com.example.myapplication.common.ApiResponse
import com.example.myapplication.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository
) {
    companion object {
        const val TAG = "GetProductListUseCase"
    }

    operator fun invoke(
        departmentId: String
    ): Flow<ApiResponse<List<Product>>> = flow {
        try {
            emit(ApiResponse.Loading())
            val res = departmentRepository.getProducts(departmentId)
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