package com.example.myapplication.ui.screen.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.ApiResponse
import com.example.myapplication.domain.usecase.department.GetDepartmentListUseCase
import com.example.myapplication.domain.usecase.product.GetProductListUseCase
import com.example.myapplication.data.model.Department
import com.example.myapplication.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getDepartmentListUseCase: GetDepartmentListUseCase,
    private val getProductListUseCase: GetProductListUseCase
) : ViewModel() {
    private val _departmentState =
        MutableStateFlow<ApiResponse<List<Department>>>(ApiResponse.Loading())
    val departmentState: StateFlow<ApiResponse<List<Department>>> = _departmentState

    private val _productUiState = MutableStateFlow(ProductUiState())
    val productUiState: StateFlow<ProductUiState> = _productUiState

    private val coroutineScope = viewModelScope


    init {
        getDepartmentList()
    }

    fun getDepartmentList() {
        coroutineScope.launch(Dispatchers.IO) {
            getDepartmentListUseCase.invoke().collect { res ->
                _departmentState.value = res
            }
        }
    }

    private fun getProductList(departmentId:String) {
        coroutineScope.launch(Dispatchers.IO) {
            getProductListUseCase.invoke(departmentId).collect { res ->
                _productUiState.value = productUiState.value.copy(productState = res)
            }
        }
    }

    fun onSelectDepartment(department: Department){
        _productUiState.value = productUiState.value.copy(department)
        getProductList(department.id)
    }
}


