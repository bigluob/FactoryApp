package com.camc.factory.utils

import androidx.compose.ui.graphics.Color

object CardColorUtil {
    private val LightBlue = Color(0xFFD7E8DE)
    private val LightGreen = Color(0xFF4CAF50)
    private val RedOrange = Color(0xFFC5BDB2)

    fun getCardColor(imageCount: Int, requiredCount: Int): Color {
        return when {
            imageCount == 0 -> LightBlue
            imageCount >= requiredCount -> LightGreen
            else -> RedOrange
        }
    }
}