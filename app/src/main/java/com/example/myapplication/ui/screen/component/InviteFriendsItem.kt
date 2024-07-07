package com.example.myapplication.ui.screen.component

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Preview
@Composable
fun InviteFriendsItem() {
    val context = LocalContext.current
    Card(
        onClick = {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://careers.lmwn.com")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp), Alignment.Center
        ) {
            Row(Modifier.fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .size(40.dp),Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gift),
                        contentDescription = "Gift",
                        modifier = Modifier.scale(0.5f)
                    )
                }

                Column(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                ) {
                    val inviteFriendText = buildAnnotatedString {
                        append("You can earn \$10  when you invite a friend to buy crypto. ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("Invite your friend")
                        }
                    }
                    Text(
                        text = inviteFriendText,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}