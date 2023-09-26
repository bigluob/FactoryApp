package com.camc.media.compoent

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.camc.media.feature.note.presentation.add_edit_note.AddEditNoteScreen
import com.camc.media.feature.note.presentation.notes.NotesScreen
import com.camc.media.feature.note.presentation.take_photo.TakePhotoScreen
import com.camc.media.router.AppRouter

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    routeWithArgs: Map<String, List<NamedNavArgument>>
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        routeWithArgs.forEach { (route, args) ->
            composable(
                route = route,
                arguments = args
            ) { backStackEntry ->
                when (route) {
                    AppRouter.NotesScreen.route -> {
                        NotesScreen(navController = navController)
                    }
                    AppRouter.AddEditNoteScreen.route -> {
                        val color = backStackEntry.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(navController = navController, noteColor = color)
                    }
                    AppRouter.TakePhotoScreen.route -> {
                        TakePhotoScreen(navController = navController, onCameraClick = {})
                    }
                }
            }
        }
    }
}
