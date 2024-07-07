package com.example.myapplication.ui.screen.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.myapplication.domain.services.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.myapplication.domain.usecase.GetCoinsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private var offset = 0
    private var limit = 20

    init {
        observeSearchQuery()
        startRepeatFetchData()
    }

    private fun startRepeatFetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                val search = searchQuery.value
                if (search.isEmpty()) {
                    fetchData()
                }
                delay(10.seconds)
            }
        }
    }

    fun fetchData(query: String="") {
        viewModelScope.launch(Dispatchers.IO) {
            getCoinsUseCase.invoke(query, offset, limit)
                .collect { res ->
                    when (res) {
                        is ApiResponse.Error -> {
                            _state.value = state.value.copy(
                                coinsState = res
                            )
                        }

                        is ApiResponse.Loading -> {
                            _state.value = state.value.copy(
                                coinsState = res
                            )
                        }

                        is ApiResponse.Success -> {
                            _state.value = state.value.copy(
                                coins = res.data!!.coins,
                                coinsState = res
                            )
                        }
                    }
                }
        }
    }

    fun loadMore() {
        limit += 20
        fetchData()
    }

    fun search(query: String) {
        limit = 20
        _state.value = state.value.copy(coins = emptyList())
        _searchQuery.value = query
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500) // Adjust debounce period as needed
                .distinctUntilChanged()
                .collect { query ->
                    fetchData(query)
                }
        }
    }


}