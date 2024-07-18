package com.example.myapplication

import android.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.myapplication.common.ApiResponse
import com.example.myapplication.domain.usecase.department.GetDepartmentListUseCase
import com.example.myapplication.domain.usecase.product.GetProductListUseCase
import com.example.myapplication.data.model.Department
import com.example.myapplication.data.model.mockDepartment
import com.example.myapplication.data.model.mockProduct
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
    lateinit var getDepartmentListUseCase: GetDepartmentListUseCase

    @MockK
    lateinit var getProductListUseCase: GetProductListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(Dispatchers.Unconfined)  // Set the main dispatcher to the Unconfined dispatcher
    }

    @Test
    fun `call getDepartmentList then return success response and set departListState`() = runTest {
        //Arrange
        val mockDepartments = List(12) { mockDepartment() }
        coEvery { getDepartmentListUseCase.invoke() } returns flow {
            emit(
                ApiResponse.Success(data = mockDepartments)
            )
        }
        //Action
        viewModel.getDepartmentList()
        //Assert

        viewModel.departmentState.test {
            assertEquals(
                12, awaitItem().data!!.size
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `call onSelectDepartment then return success response and set productUiState`() = runTest {
        //Arrange
        val mockDepartment = Department("1","url","name")
        val mockProducts = List(10) { mockProduct() }
        coEvery { getProductListUseCase.invoke(mockDepartment.id) } returns flow {
            emit(
                ApiResponse.Success(data = mockProducts)
            )
        }
        //Action
        viewModel.onSelectDepartment(mockDepartment)
        //Assert

        viewModel.productUiState.test {
            val state = awaitItem()
            println("Test ${state.productState} ${state.selectedDepartment}")
            assertEquals(
                Department("1","url","name"), state.selectedDepartment
            )
            assertEquals(
                10, state.productState.data!!.size
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

}

