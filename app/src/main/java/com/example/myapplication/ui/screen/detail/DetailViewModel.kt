package com.example.myapplication.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.remote.model.Coin
import com.example.myapplication.domain.services.ApiResponse
import com.example.myapplication.domain.usecase.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val  getCoinDetailUseCase: GetCoinDetailUseCase
): ViewModel() {
    private val _state = MutableStateFlow<ApiResponse<Coin>>(ApiResponse.Loading())
    val state: StateFlow<ApiResponse<Coin>> = _state

    fun getCoinDetail(udid:String){
        viewModelScope.launch(Dispatchers.IO) {
            getCoinDetailUseCase.invoke(udid).collect{
                _state.value =  it
            }
        }
    }
}