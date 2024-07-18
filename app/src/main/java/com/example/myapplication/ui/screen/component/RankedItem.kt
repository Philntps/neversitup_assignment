package com.example.myapplication.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.remote.model.Coin

@Preview
@Composable
fun RankedItem(
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
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(110.dp)
            .height(140.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
//                CoinImage(coin.iconUrl,"logo", Modifier.size(40.dp))
                Text(text = coin.symbol, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = coin.name, fontSize = 12.sp, color = Color(0xFF999999), softWrap = true)
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


