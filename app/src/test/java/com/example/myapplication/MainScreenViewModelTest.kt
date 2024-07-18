package com.example.myapplication

import android.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.myapplication.model.remote.model.Coin
import com.example.myapplication.model.remote.model.GetCoins
import com.example.myapplication.model.remote.model.Stats
import com.example.myapplication.domain.services.ApiResponse
import com.example.myapplication.domain.usecase.GetCoinsUseCase
import com.example.myapplication.ui.screen.main.MainScreenViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainScreenViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @InjectMockKs
    lateinit var viewModel: MainScreenViewModel

    @MockK
    lateinit var getCoinsUseCase: GetCoinsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(Dispatchers.Unconfined)  // Set the main dispatcher to the Unconfined dispatcher

    }


    @Test
    fun `search with empty string then clear load list`() {
        //Arrange
        val query = ""
        //Action
        viewModel.search(query)

        //Assert
        val result = viewModel.searchQuery.value
        assertEquals("", result)
        assertEquals(emptyList<Coin>(), viewModel.state.value.coins)
    }

    @Test
    fun `call fetch data then return success response and set coins state`() = runTest {
        //Arrange
        val query = ""
        coEvery { getCoinsUseCase.invoke(any(), any(), any()) } returns flow {
            emit(
                ApiResponse.Success<GetCoins>(
                    data = GetCoins(
                        coins = listOf(
                            Coin(
                                change = "2.44",
                                color = "#f7931A",
                                iconUrl = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
                                marketCap = "1244321247918",
                                name = "Bitcoin",
                                price = "63102.347486805294",
                                rank = 1,
                                symbol = "BTC",
                                uuid = "Qwsogvtv82FCd"
                            )
                        ),
                        stat = Stats(1, 100)
                    )
                )
            )
        }
        //Action
        viewModel.fetchData(query)
        //Assert

        viewModel.state.test {
            assertEquals(
                1, awaitItem().coins.size
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

}

