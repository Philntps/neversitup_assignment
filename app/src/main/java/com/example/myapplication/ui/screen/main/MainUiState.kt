package com.example.myapplication.ui.screen.main

import com.example.myapplication.data.remote.model.Coin
import com.example.myapplication.data.remote.model.GetCoins
import com.example.myapplication.domain.services.ApiResponse

data class MainUiState(
    val coinsState:ApiResponse<GetCoins> = ApiResponse.Loading(),
    val coins:List<Coin> = emptyList(),
)
