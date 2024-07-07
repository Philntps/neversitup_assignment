package com.example.myapplication.ui.screen.detail

import com.example.myapplication.data.remote.model.Coin
import com.example.myapplication.domain.services.ApiResponse

data class DetailUiState(
    val coinState: ApiResponse<Coin> = ApiResponse.Loading()
)
