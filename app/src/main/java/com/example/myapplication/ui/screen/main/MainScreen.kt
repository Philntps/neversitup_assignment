package com.example.myapplication.ui.screen.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.common.ApiResponse
import com.example.myapplication.common.component.DepartmentItem
import com.example.myapplication.common.component.ProductDescriptionDialog
import com.example.myapplication.common.component.ProductItem
import com.example.myapplication.data.model.Department
import com.example.myapplication.data.model.Product

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val departmentState by viewModel.departmentState.collectAsState()
    val productUiState by viewModel.productUiState.collectAsState()
    MainScreen(departmentState, productUiState, onSelectDepartment = viewModel::onSelectDepartment)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScreen(
    departmentState: ApiResponse<List<Department>> = ApiResponse.Loading(),
    productUiState: ProductUiState = ProductUiState(),
    onSelectDepartment: (Department) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
        Crossfade(targetState = departmentState, label = "") { state ->
            when (state) {
                is ApiResponse.Error -> {
                    Text(text = "${state.message}")
                }

                is ApiResponse.Loading -> {
                    CircularProgressIndicator()
                }

                is ApiResponse.Success -> {
                    LaunchedEffect(Unit) {
                        onSelectDepartment(state.data!!.first())
                    }
                    Column(
                        Modifier
                            .fillMaxSize(),
                        Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = "Department Carousel", Modifier.padding(horizontal = 8.dp))
                        LazyRow(
                            modifier = Modifier,
                            contentPadding = PaddingValues(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            items(state.data!!) { department ->
                                DepartmentItem(
                                    department,
                                    onClick = { onSelectDepartment(department) })
                            }
                        }
                        Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                            when (productUiState.productState) {
                                is ApiResponse.Error -> {
                                    Text(text = "${state.message}")
                                }

                                is ApiResponse.Loading -> {

                                    CircularProgressIndicator()
                                }

                                is ApiResponse.Success -> {

                                    var selectedProduct by remember {
                                        mutableStateOf<Product?>(null)
                                    }
                                    var onOpenDescDialog by remember {
                                        mutableStateOf(false)
                                    }
                                    LaunchedEffect(selectedProduct) {
                                        onOpenDescDialog = selectedProduct != null
                                    }
                                    when {
                                        onOpenDescDialog -> {
                                            ProductDescriptionDialog(
                                                selectedProduct!!,
                                                onDismissRequest = {
                                                    onOpenDescDialog = false
                                                    selectedProduct = null
                                                })
                                        }
                                    }
                                    Column(
                                        Modifier
                                            .fillMaxSize(),
                                        Arrangement.spacedBy(16.dp)
                                    ) {
                                        Text(
                                            text = "Product list : ${productUiState.selectedDepartment!!.name}",
                                            Modifier.padding(horizontal = 8.dp)
                                        )
                                        LazyVerticalGrid(
                                            columns = GridCells.Fixed(2),
                                            contentPadding = PaddingValues(8.dp),
                                            verticalArrangement = Arrangement.spacedBy(16.dp),
                                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            items(items = productUiState.productState.data!!) { product ->
                                                ProductItem(
                                                    product,
                                                    onClick = { selectedProduct = product })
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }
    }
}
