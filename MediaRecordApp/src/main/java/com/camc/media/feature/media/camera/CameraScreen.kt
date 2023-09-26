package com.camc.media.feature.media.camera

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@ExperimentalAnimationApi
@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: CameraViewModel = hiltViewModel(),
    title: String?) {
    // 显示并处理相机功能
    // 使用title变量（如果需要的话）
}
