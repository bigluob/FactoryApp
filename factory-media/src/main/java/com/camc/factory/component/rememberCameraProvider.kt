package com.camc.factory.component

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat

@Composable
fun rememberCameraProvider(context: Context): State<ProcessCameraProvider?> {
    return remember {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        val state = mutableStateOf<ProcessCameraProvider?>(null)

        cameraProviderFuture.addListener({
            state.value = cameraProviderFuture.get()
        }, ContextCompat.getMainExecutor(context))

        state
    }
}