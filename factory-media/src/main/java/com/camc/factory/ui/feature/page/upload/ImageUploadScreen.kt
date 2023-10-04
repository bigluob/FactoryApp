package com.camc.factory.ui.feature.page.upload

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.camc.common.utils.FileHelper2
import com.camc.factory.component.CustomProgressBar
import com.camc.factory.component.MyImageComponent
import com.camc.factory.data.network.file.MyFileUploadCallback
import com.camc.factory.ui.feature.viewmodel.ImageUploadViewModel
import com.camc.factory.utils.SharedPrefsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageUploadScreen(
    navController: NavController,
    categoryName: String,
    context: Context,
    navigateBack: () -> Unit
) {
    // 创建 ImageUploadViewModel
    val viewModel: ImageUploadViewModel = viewModel()
    val fileUploadCallback = remember { MyFileUploadCallback(0) }
    var files by remember { mutableStateOf<List<File>>(emptyList()) }
    // 在 ImageUploadScreen 顶部定义
    var selectedImageItems by remember { mutableStateOf<Set<File>>(emptySet()) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val uploadProgress by viewModel.uploadProgress.collectAsState()
    // 创建 SharedPrefsManager 实例
    val sharedPrefsManager = SharedPrefsManager.newInstance(context)
    val token = sharedPrefsManager.getString("token", "")

    LaunchedEffect(Unit) {
        val result = withContext(Dispatchers.IO) {
            FileHelper2.listFilesByCategory(context, categoryName)
        }
        files = result.first
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("选择多媒体上传") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },

        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 在顶部显示选中的图片数量
                Text(
                    text = "选中的图片数量：${selectedImageItems.size}",
                    modifier = Modifier.padding(16.dp)
                )

                // 选择图片并上传
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // 图片列表 Grid
                    // 图片列表 Grid
                    // 图片列表 Grid
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(3),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        itemsIndexed(files) { index, file ->
                            val isSelected = selectedImageItems.contains(file)

                            Image(
                                painter = rememberImagePainter(file.toUri()),
                                contentDescription = "图片",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        // 当图片被点击时，切换选中状态
                                        if (isSelected) {
                                            selectedImageItems = selectedImageItems - file
                                        } else {
                                            selectedImageItems = selectedImageItems + file
                                        }
                                    }
                            )

                            // 如果图片被选中，显示一个标记
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(Color.Blue)
                                        .clip(CircleShape)
                                        .align(Alignment.TopEnd)
                                )
                            }
                        }
                    }
                    // 上传按钮（FloatButton）位于右下角
                    FloatingActionButton(
                        onClick = {
                            // 检查是否有选中的图片
                            if (selectedImageItems.isNotEmpty()) {
                                // 执行上传操作，使用 ViewModel 处理上传
                                if (token != null) {
                                    viewModel.uploadImage(selectedImageItems, token)
                                }
                            } else {
                                // 如果没有选中图片，显示错误消息
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("请选择要上传的图片")
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(imageVector = Icons.Default.CloudUpload, contentDescription = "上传")
                    }
                }

                // 显示图片缩略图
                Image(
                    painter = MyImageComponent(
                        imageUrl = files.firstOrNull()?.toUri()?.toString() ?: ""
                    ),
                    contentDescription = "缩略图",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                // 显示上传进度
                Text(
                    text = (fileUploadCallback.progressState * 100).toInt().toString() + "%",
                    modifier = Modifier.padding(16.dp)
                )

                // 底部进度条
                CustomProgressBar(
                    progress = fileUploadCallback.progressState, // 使用上传进度作为进度条的进度
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                // 显示上传进度
                Text(
                    text = "${(uploadProgress * 100).toInt()}%",
                    modifier = Modifier.padding(16.dp)
                )
            }
        })
}

