package com.example.myapplication.ui.screen.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.common.colorStringToColor
import com.example.myapplication.common.formatMarketCap
import com.example.myapplication.model.remote.model.Coin
import com.example.myapplication.domain.services.ApiResponse
import com.example.myapplication.ui.screen.component.CoinImage

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailModalSheet(
    uuid: String = "1234",
    onDismissRequest: () -> Unit = {},
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(uuid) {
        viewModel.getCoinDetail(uuid)
    }
    val state by viewModel.state.collectAsState()
    DetailModalSheetPreview(state, onDismissRequest)
}

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailModalSheetPreview(
    state: ApiResponse<Coin> = ApiResponse.Success(
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
    onDismissRequest: () -> Unit = {},
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(containerColor = MaterialTheme.colorScheme.background,

        onDismissRequest = { onDismissRequest() }, content = {
            when (state) {
                is ApiResponse.Error -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                    onDismissRequest()
                }

                is ApiResponse.Loading -> {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp, 32.dp), Alignment.TopCenter
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ApiResponse.Success -> {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp, 32.dp, 24.dp, 16.dp), Alignment.TopCenter
                    ) {
                        val formattedPrice =
                            remember {
                                if (state.data!!.price != null) {
                                    String.format(
                                        "%.2f",
                                        (state.data.price?.toDoubleOrNull())
                                    )
                                } else {
                                    "N/A"
                                }
                            }
                        val formattedMarketCap =
                            remember {
                                formatMarketCap(state.data!!.marketCap?.toDoubleOrNull())
                            }

                        Column(
                            Modifier.fillMaxWidth(),
                            Arrangement.spacedBy(16.dp),
                            Alignment.CenterHorizontally
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                Arrangement.SpaceEvenly,
                                Alignment.CenterVertically
                            ) {
//                                CoinImage(state.data!!.iconUrl, "logo", Modifier.size(50.dp))
                                Column(
                                    Modifier
                                        .weight(1f)
                                        .padding(horizontal = 12.dp)
                                ) {
                                    Row(Modifier, Arrangement.spacedBy(4.dp)) {
                                        state.data?.let {
                                            Text(
                                                text = it.name,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                maxLines = 1,
                                                color = colorStringToColor(state.data.color)
                                            )
                                        }
                                        Text(
                                            text = "(${state.data?.symbol})",
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            softWrap = true
                                        )
                                    }
                                    Row(Modifier, Arrangement.spacedBy(8.dp)) {
                                        Text(
                                            text = "PRICE",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "$ $formattedPrice",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            softWrap = true
                                        )
                                    }
                                    Row(Modifier, Arrangement.spacedBy(8.dp)) {
                                        Text(
                                            text = "MARKET CAP",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "$ ${formattedMarketCap}",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            softWrap = true,

                                            )
                                    }
                                }
                            }
                            Text(
                                text = state.data!!.description ?: "No description",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            if (state.data!!.websiteUrl != null) {
                                HorizontalDivider()
                                Text(
                                    text = "GO TO WEBSITE",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.clickable {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW, android.net.Uri.parse(
                                                state.data.websiteUrl
                                            )
                                        )
                                        context.startActivity(intent)
                                    }
                                )
                            }
                        }
                    }

                }

            }
        }, sheetState = sheetState
    )

}