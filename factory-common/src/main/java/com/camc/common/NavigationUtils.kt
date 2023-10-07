package com.camc.common

import androidx.navigation.NavHostController

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
}