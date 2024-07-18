package com.example.myapplication.common.component

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Department
import com.example.myapplication.data.model.MockDepartment

@Preview
@Composable
fun DepartmentItem(department: Department = MockDepartment(), onClick: () -> Unit = {}) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 100.dp, height = 100.dp)
            .border(
                BorderStroke(color = Color.DarkGray, width = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CoinImage(
                imageUrl = "${department.imageUrl}?name=${department.name}",//make image unique cause the link is dynamic
                Modifier.fillMaxSize()
            )
            Text(
                text = department.name,
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.DarkGray,
                        offset = Offset.VisibilityThreshold,
                        blurRadius = 5f
                    )
                )
            )
        }
    }
}