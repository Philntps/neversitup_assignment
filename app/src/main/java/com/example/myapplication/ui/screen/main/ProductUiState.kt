package com.example.myapplication.ui.screen.main

import com.example.myapplication.common.ApiResponse
import com.example.myapplication.data.model.Department
import com.example.myapplication.data.model.Product

data class ProductUiState(
    val selectedDepartment: Department? = null,
    val productState: ApiResponse<List<Product>> = ApiResponse.Loading()
)