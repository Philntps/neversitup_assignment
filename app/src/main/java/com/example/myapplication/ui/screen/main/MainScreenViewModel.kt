package com.example.myapplication.ui.screen.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.ApiResponse
import com.example.myapplication.data.model.Department
import com.example.myapplication.domain.usecase.department.GetDepartmentListUseCase
import com.example.myapplication.domain.usecase.product.GetProductListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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



    init {
        getDepartmentList()
    }

    fun getDepartmentList() {
        viewModelScope.launch(Dispatchers.IO) {
            getDepartmentListUseCase.invoke().collect { res ->
                _departmentState.update {res}

            }
        }
    }

    private fun getProductList(departmentId:String) {
        viewModelScope.launch(Dispatchers.IO) {
            getProductListUseCase.invoke(departmentId).collect { res ->
                Log.d("PHIL","${res.message}")
                _productUiState.update {it.copy(productState = res)}
            }
        }
    }

    fun onSelectDepartment(department: Department){
        _productUiState.update {it.copy(selectedDepartment = department)}
        getProductList(department.id)
    }
}


