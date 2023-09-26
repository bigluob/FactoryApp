package com.camc.factory.ui.feature.page

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.camc.factory.ui.feature.viewmodel.CaptureViewModel
import java.util.*

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun CaptureScreen(
    navController: NavController,
    categoryName: String,
    captureViewModel: CaptureViewModel
) {


    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraPermission = Manifest.permission.CAMERA
    val isCameraPermissionGranted = ActivityCompat.checkSelfPermission(
        context,
        cameraPermission
    ) == PackageManager.PERMISSION_GRANTED

    val imageCapture = remember {
        context.display?.let {
            ImageCapture.Builder()
                .setTargetRotation(it.rotation)
                .build()
        }
    }

    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val cameraProvider = cameraProviderFuture.get()

    var flashMode by remember { mutableStateOf(ImageCapture.FLASH_MODE_OFF) }

    Column(Modifier.fillMaxSize()) {
        // Camera preview
        AndroidView(
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview
                )
                previewView
            },
            modifier = Modifier.weight(1f)
        )

        // Flash mode button
        Button(
            onClick = {
                flashMode = when (flashMode) {
                    ImageCapture.FLASH_MODE_OFF -> ImageCapture.FLASH_MODE_ON
                    ImageCapture.FLASH_MODE_ON -> ImageCapture.FLASH_MODE_AUTO
                    ImageCapture.FLASH_MODE_AUTO -> ImageCapture.FLASH_MODE_OFF
                    else -> ImageCapture.FLASH_MODE_OFF
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = when (flashMode) {
                    ImageCapture.FLASH_MODE_OFF -> "Flash: OFF"
                    ImageCapture.FLASH_MODE_ON -> "Flash: ON"
                    ImageCapture.FLASH_MODE_AUTO -> "Flash: AUTO"
                    else -> "Flash: OFF"
                }
            )
        }

        // Bottom bar with capture and gallery buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Capture button
            Button(
                onClick = {
                    if (imageCapture != null) {
                        captureViewModel.capturePhoto(
                            context,
                            imageCapture,
                            flashMode,
                            categoryName
                        ) { success ->
                            captureViewModel._photoCaptureSuccess.value = success
                        }
                    }

                }
            ) {
                Text(text = "Capture")
            }

            // Gallery button
            Button(
                onClick = {
                    // Handle gallery button click
                }
            ) {
                Text(text = "Gallery")
            }
        }
        // 在 captureViewModel.photoCaptureSuccess 观察器中执行导航操作
        var photoCaptureSuccess by remember { mutableStateOf(false) }
        captureViewModel.photoCaptureSuccess.observe(lifecycleOwner) { success ->
            photoCaptureSuccess = success
        }

        LaunchedEffect(photoCaptureSuccess) {
            if (photoCaptureSuccess) {
                navController.popBackStack()
            } else {
                // 处理照片拍摄失败的情况
            }
        }
    }
}
