package com.camc.factory.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

@Composable
fun RequestCameraAndStoragePermission(
    lifecycleOwner: LifecycleOwner,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current // Obtain the Context using LocalContext

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.CAMERA] == true &&
            permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true
        ) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val allPermissionsGranted = permissions.all { permission ->
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    if (allPermissionsGranted) {
        onPermissionGranted()
    } else {
        requestPermissionLauncher.launch(permissions)
    }
}
