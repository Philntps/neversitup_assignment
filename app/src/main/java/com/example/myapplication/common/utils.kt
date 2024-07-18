package com.example.myapplication.common

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color

fun colorStringToColor(colorString: String?): Color {
    try {
        return Color(android.graphics.Color.parseColor(colorString))
    } catch (e: Exception) {
        e.printStackTrace()
        return Color.Black
    }
}

@SuppressLint("DefaultLocale")
fun formatMarketCap(marketCap: Double?): String {
    return if (marketCap != null) {
        when {
            marketCap >= 1_000_000_000_000 -> String.format(
                "%.2f trillion",
                marketCap / 1_000_000_000_000
            )

            marketCap >= 1_000_000_000 -> String.format("%.2f billion", marketCap / 1_000_000_000)
            marketCap >= 1_000_000 -> String.format("%.2f million", marketCap / 1_000_000)
            else -> marketCap.toString()
        }
    } else {
        "N/A"
    }
}

fun shouldInsertInvite(position: Int): Boolean {
    var currentPosition = 5
    while (currentPosition <= position) {
        if (currentPosition == position) return true
        currentPosition *= 2
    }
    return false
}