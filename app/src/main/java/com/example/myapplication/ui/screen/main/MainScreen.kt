package com.example.myapplication.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.screen.component.CoinImage

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val search by viewModel.searchQuery.collectAsState()
    var isShowModal by remember { mutableStateOf(false) }
    var selectedUdid by remember { mutableStateOf("") }
    val isShowDetail by remember {
        derivedStateOf { isShowModal && selectedUdid.isNotEmpty() }
    }
    val isShowTopRank = remember {
        derivedStateOf {
            state.coins.size > 3 && search.isEmpty()
        }
    }
    MainScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScreen(
) {
    Column(
        Modifier
            .fillMaxSize(),
        Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Department Carousel", Modifier.padding(horizontal = 8.dp))
        LazyRow(
            modifier = Modifier,
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .size(width = 100.dp, height = 100.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CoinImage(imageUrl = null, Modifier.fillMaxSize())
                        Text(
                            text = "name",
                            Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
        Text(text = "Product list : ${"\$DepartmentName"}", Modifier.padding(horizontal = 8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .size(width = 100.dp, height = 240.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        CoinImage(
                            imageUrl = null,
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)) {
                            Text(
                                text = "name",
                                Modifier
                                    .padding(8.dp)
                            )
                            Text(
                                text = "name",
                                Modifier
                                    .padding(8.dp)
                            )
                            Text(
                                text = "name",
                                Modifier
                                    .padding(8.dp)
                                    .align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
    }
}
