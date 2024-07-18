package com.example.myapplication.data.repository

import com.example.myapplication.data.service.ApiService
import com.example.myapplication.domain.repository.DepartmentRepository
import com.example.myapplication.data.model.Department
import com.example.myapplication.data.model.Product
import javax.inject.Inject

class DepartmentRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DepartmentRepository {
    override suspend fun getDepartmentList(): List<Department> {
        return apiService.getDepartments()
    }

    override suspend fun getProducts(departmentId: String): List<Product> {
        return apiService.getProducts(departmentId)
    }
}