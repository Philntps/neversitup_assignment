package com.example.myapplication.ui.screen.main

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.core.shouldInsertInvite
import com.example.myapplication.domain.services.ApiResponse
import com.example.myapplication.ui.screen.component.CoinItem
import com.example.myapplication.ui.screen.component.CustomSearchBar
import com.example.myapplication.ui.screen.component.InviteFriendsItem
import com.example.myapplication.ui.screen.component.RankedItem
import com.example.myapplication.ui.screen.detail.DetailModalSheet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

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
    MainScreen(
        state,
        search,
        isShowTopRank.value,
        onTap = fun(udid: String) {
            selectedUdid = udid
            isShowModal = true
        },
        onFetchData = viewModel::fetchData,
        onLoadMore = viewModel::loadMore,
        onSearch = fun(s: String) { viewModel.search(s) })
    if (isShowDetail) {
        DetailModalSheet(selectedUdid, onDismissRequest = {
            isShowModal = false
            selectedUdid = ""
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen(
    state: MainUiState = MainUiState(),
    search: String = "",
    isShowTopRank: Boolean = false,
    onTap: (String) -> Unit = {},
    onFetchData: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            onFetchData()
            delay(1.seconds)
            isRefreshing = false
        }
    }
    val listState = rememberLazyListState()
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            val loadMore = totalItemsNumber != 0 && lastVisibleItemIndex > (totalItemsNumber - 2)
            loadMore
        }
    }
    val pullRefreshState = rememberPullToRefreshState()
    LaunchedEffect(key1 = loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                Log.d("Main", "Load more...")
                onLoadMore()
            }
    }

    if(state.coinsState is ApiResponse.Error){
        Toast.makeText(
            context,
            state.coinsState.message,
            Toast.LENGTH_LONG
        ).show()
    }

    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            CustomSearchBar(search, onSearch = onSearch)
        }
        HorizontalDivider(Modifier.fillMaxWidth())
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            state = pullRefreshState,
            isRefreshing = isRefreshing,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    state = pullRefreshState,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            onRefresh = onRefresh,
            content = {
                Box(Modifier.matchParentSize(), Alignment.TopCenter) {
                    if (state.coinsState is ApiResponse.Success && state.coins.isEmpty()) {
                        Column(
                            Modifier.matchParentSize(),
                            Arrangement.Center,
                            Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Sorry", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "No result match this keyword",
                                fontSize = 16.sp,
                            )
                        }
                    } else {
                        when(configuration.orientation){

                            Configuration.ORIENTATION_LANDSCAPE -> {
                                LazyVerticalGrid(columns = GridCells.Fixed(3),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 27.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    item (span = { GridItemSpan(maxLineSpan)}){
                                        if (isShowTopRank) {
                                            Column(
                                                Modifier.fillMaxWidth(),
                                                Arrangement.spacedBy(12.dp),
                                                Alignment.CenterHorizontally
                                            ) {
                                                val topRankText = buildAnnotatedString {
                                                    append("Top ")
                                                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onError)) {
                                                        append("3")
                                                    }
                                                    append(" rank crypto")
                                                }
                                                Text(
                                                    text = topRankText,
                                                    fontWeight = FontWeight.Bold,
                                                    lineHeight = 20.sp,
                                                    modifier = Modifier
                                                )
                                                Row(
                                                    Modifier,
                                                    Arrangement.spacedBy(8.dp),
                                                    Alignment.CenterVertically
                                                ) {
                                                    for (i in 0 until minOf(3, state.coins.size)) {
                                                        RankedItem(
                                                            coin = state.coins[i],
                                                            onClick = { onTap(state.coins[i].uuid) })
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    item(span = { GridItemSpan(maxLineSpan)}){
                                        Column(
                                            Modifier.fillMaxWidth(),
                                            Arrangement.Center,
                                            Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Buy, sell and hold crypto",
                                                fontWeight = FontWeight.Bold,
                                                lineHeight = 20.sp

                                            )
                                        }
                                    }
                                    itemsIndexed(
                                        if (isShowTopRank) state.coins.subList(
                                            3,
                                            state.coins.size
                                        ) else state.coins
                                    ) { index, coin ->
                                        CoinItem(coin, onClick = { onTap(coin.uuid) })
                                        if(shouldInsertInvite(index+1)){
                                            InviteFriendsItem()
                                        }
                                    }
                                    item (span = { GridItemSpan(maxLineSpan)}){
                                        if (state.coinsState is ApiResponse.Loading) {
                                            Column(
                                                Modifier.fillMaxWidth(),
                                                Arrangement.Center,
                                                Alignment.CenterHorizontally
                                            ) {
                                                CircularProgressIndicator(
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                        if (state.coinsState is ApiResponse.Error) {
                                            Column(
                                                Modifier
                                                    .height(82.dp)
                                                    .fillMaxWidth(),
                                                Arrangement.Center,
                                                Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = "Could not load data",
                                                    fontSize = 16.sp,
                                                    color = MaterialTheme.colorScheme.onSurface,
                                                )
                                                Text(
                                                    text = "Try again",
                                                    Modifier.clickable {
                                                        onLoadMore()
                                                    },
                                                    color = MaterialTheme.colorScheme.primary,
                                                    fontSize = 14.sp,
                                                )
                                            }
                                            LaunchedEffect(Unit) {
                                                coroutineScope.launch {
                                                    if (state.coins.isNotEmpty()) {
                                                        listState.scrollToItem(
                                                            state.coins.lastIndex,
                                                            scrollOffset = Int.MAX_VALUE
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else ->{
                                LazyColumn(
                                    contentPadding = PaddingValues(horizontal = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    state = listState
                                ) {
                                    item {
                                        if (isShowTopRank) {
                                            Column(
                                                Modifier.fillMaxWidth(),
                                                Arrangement.spacedBy(12.dp),
                                                Alignment.CenterHorizontally
                                            ) {
                                                val topRankText = buildAnnotatedString {
                                                    append("Top ")
                                                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onError)) {
                                                        append("3")
                                                    }
                                                    append(" rank crypto")
                                                }
                                                Text(
                                                    text = topRankText,
                                                    fontWeight = FontWeight.Bold,
                                                    lineHeight = 20.sp,
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                                Row(
                                                    Modifier,
                                                    Arrangement.spacedBy(8.dp),
                                                    Alignment.CenterVertically
                                                ) {
                                                    for (i in 0 until minOf(3, state.coins.size)) {
                                                        RankedItem(
                                                            coin = state.coins[i],
                                                            onClick = { onTap(state.coins[i].uuid) })
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    item {
                                        Column(
                                            Modifier.fillMaxWidth(),
                                        ) {
                                            Text(
                                                text = "Buy, sell and hold crypto",
                                                fontWeight = FontWeight.Bold,
                                                lineHeight = 20.sp

                                            )
                                        }
                                    }
                                    itemsIndexed(
                                        if (isShowTopRank) state.coins.subList(
                                            3,
                                            state.coins.size
                                        ) else state.coins
                                    ) { index, coin ->
                                        CoinItem(coin, onClick = { onTap(coin.uuid) })
                                        if(shouldInsertInvite(index+2)){
                                            Spacer(modifier = Modifier.height(12.dp))
                                            InviteFriendsItem()
                                        }
                                    }
                                    item {
                                        if (state.coinsState is ApiResponse.Loading) {
                                            Column(
                                                Modifier.fillMaxWidth(),
                                                Arrangement.Center,
                                                Alignment.CenterHorizontally
                                            ) {
                                                CircularProgressIndicator(
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                        if (state.coinsState is ApiResponse.Error) {
                                            Column(
                                                Modifier
                                                    .height(82.dp)
                                                    .fillMaxWidth(),
                                                Arrangement.Center,
                                                Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = "Could not load data",
                                                    fontSize = 16.sp,
                                                    color = MaterialTheme.colorScheme.onSurface,
                                                )
                                                Text(
                                                    text = "Try again",
                                                    Modifier.clickable {
                                                        onLoadMore()
                                                    },
                                                    color = MaterialTheme.colorScheme.primary,
                                                    fontSize = 14.sp,
                                                )
                                            }
                                            LaunchedEffect(Unit) {
                                                coroutineScope.launch {
                                                    if (state.coins.isNotEmpty()) {
                                                        listState.scrollToItem(
                                                            state.coins.lastIndex,
                                                            scrollOffset = Int.MAX_VALUE
                                                        )
                                                    }
                                                }
                                                Toast.makeText(
                                                    context,
                                                    state.coinsState.message,
                                                    Toast.LENGTH_LONG
                                                )
                                                    .show()
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            },
        )
    }
}
