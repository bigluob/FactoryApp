package com.camc.factory.component


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressBar(
    progress: Int,
    modifier: Modifier = Modifier,
    barColor: Color = Color.Blue,
    backgroundColor: Color = Color.Gray
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
    ) {
        // 绘制背景
        drawRect(
            color = backgroundColor,
            size = size
        )

        // 计算进度条的宽度
        val barWidth = size.width * progress

        // 创建渐变效果
        val brush = Brush.horizontalGradient(
            colors = listOf(Color.White, barColor),
            startX = 0f,
            endX = barWidth
        )

        // 绘制进度条
        drawRect(
            brush = brush,
            size = Size(barWidth, size.height),
            topLeft = Offset(0f, 0f)
        )
    }
}