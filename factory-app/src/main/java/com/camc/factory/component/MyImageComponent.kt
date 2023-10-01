package com.camc.factory.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberImagePainter

@Composable
fun MyImageComponent(imageUrl: String): Painter {
    return rememberImagePainter(imageUrl)
}