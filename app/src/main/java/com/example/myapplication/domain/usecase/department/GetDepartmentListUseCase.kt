package com.example.myapplication.domain.usecase.department

import android.util.Log
import com.example.myapplication.common.ApiResponse
import com.example.myapplication.domain.repository.DepartmentRepository
import com.example.myapplication.data.model.Department
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDepartmentListUseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository
) {
    companion object {
        const val TAG = "GetCoinsUseCase"
    }

    operator fun invoke(): Flow<ApiResponse<List<Department>>> = flow {
        try {
            emit(ApiResponse.Loading())
            val res = departmentRepository.getDepartmentList()
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