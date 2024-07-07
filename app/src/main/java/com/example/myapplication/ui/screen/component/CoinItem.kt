package com.example.myapplication.ui.screen.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.remote.model.Coin

@SuppressLint("DefaultLocale")
@Preview
@Composable
fun CoinItem(
    coin: Coin = Coin(
        change = "2.44",
        color = "#f7931A",
        iconUrl = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
        marketCap = "1244321247918",
        name = "Bitcoin",
        price = "63102.347486805294",
        rank = 1,
        symbol = "BTC",
        uuid = "Qwsogvtv82FCd"
    ), onClick: () -> Unit = {}
) {

    val formattedPrice = remember {  if(coin.price != null) String.format("%.5f", coin.price.toDoubleOrNull()) else "N/A" }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp), Alignment.Center
        ) {
            Row(Modifier.fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterVertically) {
                CoinImage(coin.iconUrl, "logo", Modifier.size(40.dp))
                Column(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = coin.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    Text(
                        text = coin.symbol,
                        fontSize = 12.sp,
                        color = Color(0xFF999999),
                        fontWeight = FontWeight.Bold,
                        softWrap = true
                    )
                }
                Column(Modifier, Arrangement.Center, Alignment.End) {
                    Text(
                        text = "$${ formattedPrice}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if(coin.change != null) {
                        ChangeIndicator(change = coin.change)
                    }else{
                        Text(
                            text = "N/A",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}