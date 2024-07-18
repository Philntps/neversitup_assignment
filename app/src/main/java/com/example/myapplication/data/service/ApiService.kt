package com.example.myapplication.data.service

import com.example.myapplication.data.model.Department
import com.example.myapplication.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/v1/departments")
    suspend fun getDepartments(): List<Department>

    @GET("api/v1/departments/{departmentId}/products")
    suspend fun getProducts(
        @Path("departmentId") departmentId: String,
    ): List<Product>
}