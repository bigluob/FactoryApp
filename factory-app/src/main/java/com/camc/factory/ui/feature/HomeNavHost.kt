package com.camc.factory.ui.feature


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.camc.factory.common.AppRouter
import com.camc.factory.common.AppRouterParams
import com.camc.factory.ui.feature.page.CategoryAddEditScreen
import com.camc.factory.ui.feature.page.CategoryScreen
import com.camc.factory.ui.feature.page.ServerSettingsScreen
import com.camc.factory.ui.feature.page.login.LoginScreen
import com.camc.factory.ui.feature.page.upload.ImageUploadScreen
import com.camc.factory.ui.feature.viewmodel.CaptureViewModel
import com.camc.factory.ui.feature.viewmodel.LoginViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("UnsafeOptInUsageError")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeNavHost(context: Context) {
    val navController = rememberAnimatedNavController()
    val executor: Executor = Executors.newSingleThreadExecutor()
    val loginViewModel: LoginViewModel = viewModel()
    val captureViewModel: CaptureViewModel = viewModel()
    val loadingProgressBar = loginViewModel.progressBar.value
    val imageError = loginViewModel.imageErrorAuth.value
    AnimatedNavHost(navController, startDestination = AppRouter.Category.route) {
        composable(AppRouter.Category.route) {
            Column() {
                CategoryScreen(navController)
            }
        }
        composable(route = AppRouter.Login.route) {
            if (loginViewModel.isSuccessLoading.value) {
                LaunchedEffect(key1 = Unit) {
                    navController.navigate(route = AppRouter.Category.route) {
                        popUpTo(route = AppRouter.Login.route) {
                            inclusive = true
                        }
                    }
                }
            } else {
                LoginScreen(
                    loadingProgressBar = loadingProgressBar,
                    onclickLogin = loginViewModel::login,
                    imageError = imageError
                )
            }
        }
       /* composable(
            route = "${AppRouter.TakePhoto.route}/{${AppRouterParams.PAR_CATEGORY_TITLE}}",
            arguments = listOf(
                navArgument(AppRouterParams.PAR_CATEGORY_TITLE) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val categoryName = entry.arguments?.getString(AppRouterParams.PAR_CATEGORY_TITLE) ?: ""
            CaptureScreen(
                navController,
                categoryName,
                captureViewModel
            )
        }*/

        composable(
            route = "${AppRouter.ImageFileUpload.route}/{${AppRouterParams.PAR_CATEGORY_TITLE}}",
            arguments = listOf(
                navArgument(AppRouterParams.PAR_CATEGORY_TITLE) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val categoryName = entry.arguments?.getString(AppRouterParams.PAR_CATEGORY_TITLE) ?: ""
            ImageUploadScreen(
                navController = navController,
                categoryName = categoryName,
                context = context,
                navigateBack = {
                    navController.popBackStack() // 导航回上一页
                }
            )
        }
        composable(
            AppRouter.CategoryAddEdit.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        ) {
            Column() {
                CategoryAddEditScreen(navController)
            }
        }


        composable(
            AppRouter.ServerSettings.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        ) {
            Column() {
                ServerSettingsScreen(navController)
            }
        }
    }
}