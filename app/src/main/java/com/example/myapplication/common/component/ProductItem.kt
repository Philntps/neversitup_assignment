package com.example.myapplication.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.mockProduct
import com.example.myapplication.data.model.Product

@Preview
@Composable
fun ProductItem(product: Product = mockProduct(), onClick: () -> Unit = {}) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 200.dp, height = 240.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CoinImage(
                imageUrl = "${product.imageUrl}?name=${product.name}",//make image unique cause the link is dynamic
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = product.name,
                    Modifier
                        .padding(8.dp),
                    maxLines = 1,
                    softWrap = true
                )
                Text(
                    text = product.desc,
                    Modifier
                        .padding(8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.price,
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.End)
                )
            }
        }
    }
}