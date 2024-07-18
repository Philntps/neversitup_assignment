package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.Department
import com.example.myapplication.data.model.Product
import retrofit2.http.Path

interface DepartmentRepository {
    suspend fun getDepartmentList(): List<Department>

    suspend fun getProducts(
        @Path("departmentId") departmentId: String,
    ): List<Product>
}