package com.camc.factory.ui.feature.page

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import java.io.File

@Composable
fun TodoFileUploadScreen(
    navController: NavController,
    todoTitle: String
) {
    val context = LocalContext.current
    val photoList = getPhotosByCategoryId(context, todoTitle.toString())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (photoList.isNotEmpty()) {
            // 显示照片列表
            LazyColumn {
                items(photoList.size) { index ->
                    val photo = photoList[index]
                    Image(
                        painter = rememberImagePainter(data = photo),
                        contentDescription = "Photo",
                        modifier = Modifier.size(300.dp)
                    )
                }
            }
        } else {
            Text(text = "No photos found for category $todoTitle")
        }

        Button(
            onClick = { uploadPhotos(photoList) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Upload Photos")
        }
    }

    navController.popBackStack()
}

fun uploadPhotos(photoList: List<File>) {

}

fun getPhotosByCategoryId(context: Context, categoryId: String): List<File> {
    val directory = context.filesDir // 设置照片存储的目录
    val photoList = mutableListOf<File>()

    directory.listFiles()?.forEach { file ->
        if (file.name.contains(categoryId)) {
            photoList.add(file)
        }
    }

    return photoList
}