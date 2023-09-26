package com.camc.media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.camc.media.compoent.AppNavHost
import com.camc.media.router.AppRouter
import com.camc.media.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNoteAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val routeWithArgs = mapOf(
                        AppRouter.NotesScreen.route to listOf(),
                        AppRouter.AddEditNoteScreen.route to listOf(
                            navArgument("noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument("noteColor") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        ),
                        AppRouter.TakePhotoScreen.route to listOf(
                            navArgument("noteId") {
                                type = NavType.StringType
                                defaultValue = -1
                            }
                        )
                    )

                    AppNavHost(
                        navController = navController,
                        startDestination = AppRouter.NotesScreen.route,
                        routeWithArgs = routeWithArgs
                    )

                }
            }
        }
    }
}