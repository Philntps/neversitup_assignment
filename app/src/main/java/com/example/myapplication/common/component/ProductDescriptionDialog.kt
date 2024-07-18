package com.example.myapplication.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.data.model.MockProduct
import com.example.myapplication.data.model.Product

@Preview(showBackground = true)
@Composable
fun ProductDescriptionDialog(product: Product = MockProduct(), onDismissRequest: () -> Unit = {}) {
    Dialog(onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        ElevatedCard(
            onClick = { /*TODO*/ }, modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(shape = RoundedCornerShape(12.dp))
        ) {
            Column(
                Modifier, Arrangement.Center
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(16.dp), Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Product Description",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = product.desc,
                        Modifier
                            .weight(1f)
                            .fillMaxSize()
                    )
                }
                HorizontalDivider()
                TextButton(onClick = onDismissRequest, Modifier.fillMaxWidth()) {
                    Text(text = "Close", color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}