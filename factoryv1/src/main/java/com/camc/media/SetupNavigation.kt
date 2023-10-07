package com.camc.media

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.camc.media.features.HomeScreen
import com.camc.media.features.LoginScreen
import com.camc.media.features.SettingScreen
import com.camc.media.features.UploadScreen

object NavigationUtils {
    private const val HOME_ROUTE = "home"
    private const val LOGIN_ROUTE = "login"
    private const val SETTING_ROUTE = "setting"
    private const val UPLOAD_ROUTE = "upload"

    fun navigateToHome(navController: NavHostController) {
        navController.navigate(HOME_ROUTE) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToLogin(navController: NavHostController) {
        navController.navigate(LOGIN_ROUTE) {
            popUpTo(HOME_ROUTE) {
                inclusive = true
            }
        }
    }

    fun navigateToSetting(navController: NavHostController) {
        navController.navigate(SETTING_ROUTE)
    }

    fun navigateToUpload(navController: NavHostController) {
        navController.navigate(UPLOAD_ROUTE)
    }

    // 带参数的导航方法
    fun navigateWithParameters(navController: NavHostController, parameter: String) {
        val routeWithParameter = "$HOME_ROUTE/$parameter"
        navController.navigate(routeWithParameter) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    @Composable
    fun SetupNavigation() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = HOME_ROUTE) {
            composable(HOME_ROUTE) {
                HomeScreen(navController)
            }
            composable(LOGIN_ROUTE) {
                LoginScreen(navController)
            }
            composable(SETTING_ROUTE) {
                SettingScreen(navController)
            }
            composable(UPLOAD_ROUTE) {
                UploadScreen(navController)
            }

        }
    }


}