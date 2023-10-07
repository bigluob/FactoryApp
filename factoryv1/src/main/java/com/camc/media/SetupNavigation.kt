package com.camc.media

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController

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
            composable(
                route = "$HOME_ROUTE/{parameter}",
                arguments = listOf(navArgument("parameter") { type = NavType.StringType })
            ) { backStackEntry ->
                val parameter = backStackEntry.arguments?.getString("parameter")
                HomeScreen(navController, parameter)
            }
        }
    }