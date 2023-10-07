package com.camc.factory.ui.feature.page.upload

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.camc.common.utils.FileHelper2
import com.camc.factory.component.MyImageComponent
import com.camc.factory.ui.feature.viewmodel.ImageUploadViewModel
import com.camc.factory.ui.feature.viewmodel.ResultStatus
import com.camc.factory.utils.SharedPreferencesManager
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
    navigateBack: () -> Unit,
    sharedPreferencesManager: SharedPreferencesManager // 通过依赖注入获得的实例
) {
    // 创建 ImageUploadViewModel
    val imageUploadViewModel: ImageUploadViewModel = hiltViewModel()
    /*val fileUploadCallback = remember { MyFileUploadCallback(0) }*/
    var files by remember { mutableStateOf<List<File>>(emptyList()) }
    // 在 ImageUploadScreen 顶部定义
    var selectedImageItems by remember { mutableStateOf<Set<File>>(emptySet()) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val uploadProgress by imageUploadViewModel.uploadProgress.collectAsState()
    val uploadResult by imageUploadViewModel.uploadResult.observeAsState()

    // 创建 SharedPrefsManager 实例
    sharedPreferencesManager.saveToken("eyJhbGciOiJub25lIiwidHlwIjoiQmVhcmVyIn0.eyJwcmltYXJ5c2lkIjoiMTU3NzU5NDcxNDE2ODgxOTcxMiIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL3NpZCI6Ind1eGwxIiwidW5pcXVlX25hbWUiOiLlkLTmlrDlvZUiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoie1wiVUlEXCI6XCIxNTc3NTk0NzE0MTY4ODE5NzEyXCIsXCJVc2VySURcIjpcInd1eGwxXCIsXCJVc2VyTmFtZVwiOlwi5ZC05paw5b2VXCIsXCJUZW5hbnRJRFwiOm51bGwsXCJUZW5hbnROYW1lXCI6bnVsbCxcIkRlcHRJRFwiOlwiMTU3NjIyMjUyMTg4NTc4MjAxNlwiLFwiRGVwdE5hbWVcIjpcIuS_oemYs-WIhuWFrOWPuFwiLFwiR3JvdXBJRFwiOlwiMTU5ODg5NDI5ODAwNjc4MTk1MlwiLFwiR3JvdXBOYW1lXCI6XCLnlJ_kuqfnrqHnkIbpg6hcIixcIkdyYWRlbGV2ZWxcIjpcIue7hOmVv1wiLFwiU2VjdXJpdHlOYW1lXCI6XCLlhazlvIBcIixcIlNlY3VyaXR5VmFsdWVcIjowLFwiUm9sZUlkc1wiOm51bGwsXCJSb2xlc1wiOm51bGwsXCJQZXJtaXNzaW9uc1wiOm51bGx9IiwiQXVkaWVuY2UiOiJEaVhpbi5QRE0iLCJJc3N1ZXIiOiJEaVhpbi5QRE0iLCJuYmYiOjE2OTY0ODkyMjQsImV4cCI6MTY5NjUzMjQyNCwiaWF0IjoxNjk2NDg5MjI0LCJpc3MiOiJEaVhpbi5QRE0iLCJhdWQiOiJEaVhpbi5QRE0ifQ.")
    val token = sharedPreferencesManager.getToken()
    LaunchedEffect(Unit) {
        if (token != null) {
            if (token.isEmpty()) {
                sharedPreferencesManager.saveToken("eyJhbGciOiJub25lIiwidHlwIjoiQmVhcmVyIn0.eyJwcmltYXJ5c2lkIjoiMTU3NzU5NDcxNDE2ODgxOTcxMiIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL3NpZCI6Ind1eGwxIiwidW5pcXVlX25hbWUiOiLlkLTmlrDlvZUiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoie1wiVUlEXCI6XCIxNTc3NTk0NzE0MTY4ODE5NzEyXCIsXCJVc2VySURcIjpcInd1eGwxXCIsXCJVc2VyTmFtZVwiOlwi5ZC05paw5b2VXCIsXCJUZW5hbnRJRFwiOm51bGwsXCJUZW5hbnROYW1lXCI6bnVsbCxcIkRlcHRJRFwiOlwiMTU3NjIyMjUyMTg4NTc4MjAxNlwiLFwiRGVwdE5hbWVcIjpcIuS_oemYs-WIhuWFrOWPuFwiLFwiR3JvdXBJRFwiOlwiMTU5ODg5NDI5ODAwNjc4MTk1MlwiLFwiR3JvdXBOYW1lXCI6XCLnlJ_kuqfnrqHnkIbpg6hcIixcIkdyYWRlbGV2ZWxcIjpcIue7hOmVv1wiLFwiU2VjdXJpdHlOYW1lXCI6XCLlhazlvIBcIixcIlNlY3VyaXR5VmFsdWVcIjowLFwiUm9sZUlkc1wiOm51bGwsXCJSb2xlc1wiOm51bGwsXCJQZXJtaXNzaW9uc1wiOm51bGx9IiwiQXVkaWVuY2UiOiJEaVhpbi5QRE0iLCJJc3N1ZXIiOiJEaVhpbi5QRE0iLCJuYmYiOjE2OTY0ODkyMjQsImV4cCI6MTY5NjUzMjQyNCwiaWF0IjoxNjk2NDg5MjI0LCJpc3MiOiJEaVhpbi5QRE0iLCJhdWQiOiJEaVhpbi5QRE0ifQ.")
            }
        }
        val token = sharedPreferencesManager.getToken() ?: ""
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
                    text = "选中上传的图片数量：${selectedImageItems.size}",
                    modifier = Modifier.padding(16.dp)
                )

                // 选择图片并上传
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(3),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        itemsIndexed(files) { index, file ->
                            val isSelected = selectedImageItems.contains(file)
                            val borderModifier = if (isSelected) {
                                Modifier.border(2.dp, Color.Blue)
                            } else {
                                Modifier
                            }

                            Image(
                                painter = rememberImagePainter(file.toUri()),
                                contentDescription = "图片",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .then(borderModifier)
                                    .clickable {
                                        // 切换选中状态
                                        if (isSelected) {
                                            selectedImageItems = selectedImageItems
                                                .toMutableSet()
                                                .apply { remove(file) }
                                        } else {
                                            selectedImageItems = selectedImageItems
                                                .toMutableSet()
                                                .apply { add(file) }
                                        }
                                    }
                            )
                        }
                    }
                    // 上传按钮（FloatButton）位于右下角
                    FloatingActionButton(
                        onClick = {
                            // 检查是否有选中的图片
                            if (selectedImageItems.isNotEmpty()) {
                                // 执行上传操作，使用 ViewModel 处理上传
                                if (token != null) {
                                    imageUploadViewModel.uploadImage(selectedImageItems, token)
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

                // 显示上传结果
                if (uploadResult != null) {
                    when (uploadResult) {
                        is ResultStatus.Success -> {
                            Text(
                                text = "上传成功: ${(uploadResult as ResultStatus.Success).message}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        is ResultStatus.Failure -> {
                            Text(
                                text = "上传失败: ${(uploadResult as ResultStatus.Failure).error}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
                // 显示上传进度
                Text(
                    text = "${(uploadProgress * 100).toInt()}%",
                    modifier = Modifier.padding(16.dp)
                )
            }
        })
}

