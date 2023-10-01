package com.camc.factory.ui.feature.page.upload

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.camc.factory.component.MyImageComponent
import com.camc.factory.data.network.file.MyFileUploadCallback
import com.camc.factory.utils.FileHelper
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageUploadScreen(
    navController: NavController,
    categoryName: String,
    context: Context,
    navigateBack: () -> Unit
) {
    var files: List<File> by remember { mutableStateOf(emptyList()) }
    val fileUploadCallback = remember { MyFileUploadCallback(0) }

    files= FileHelper.listFilesByCategory(categoryName)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 显示图片缩略图
        Image(
            painter = MyImageComponent(imageUrl = files.firstOrNull()?.toUri()?.toString() ?: ""),
            contentDescription = "缩略图",
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        // 显示上传进度
        Text(
            text = fileUploadCallback.progressState.toString(),
            modifier = Modifier.padding(16.dp)
        )

        // 选择图片并上传

        // 图片列表 Grid
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            modifier = Modifier.padding(16.dp)
        ) {
            itemsIndexed(files) { index, file ->
                Image(
                    painter = rememberImagePainter(file.toUri()),
                    contentDescription = "图片",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}