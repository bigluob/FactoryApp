package com.camc.media.router

import android.net.Uri

sealed class AppRouter(val route: String) {
    object NotesScreen: AppRouter("notes_screen")
    object AddEditNoteScreen: AppRouter("add_edit_note_screen")
    object TakePhotoScreen: AppRouter("take_photo_screen")
    object UploadMediaScreen: AppRouter("upload")


    object CameraScreen : AppRouter("camera/{title}")

    object Gallery : AppRouter("gallery")
    object Configuration : AppRouter("configuration")
    object Preview : AppRouter("preview/{${Args.Path}}") {
        fun createRoute(path: String) = "preview/${Uri.encode(path)}"
    }

}
