package com.camc.factory.ui.feature.page

import android.preference.PreferenceManager
import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.camc.factory.utils.SharedPrefsManager


@Composable
fun ServerSettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val defaultServerAddress = "http://82.157.66.177:8888/api/Login/Login"
    //获取上下文

    val showDialogState = remember { mutableStateOf(false) }

    // 初始化 serverAddressState 的值
    LaunchedEffect(key1 = Unit) {
        val savedServerAddress = sharedPreferences.getString("serverAddress", null)
        showDialogState.value = savedServerAddress != null
    }

    if (showDialogState.value) {
        AlertDialog(
            onDismissRequest = { showDialogState.value = false },
            title = { Text("服务器地址设置") },
            text = {
                Column {
                    OutlinedTextField(
                        value = defaultServerAddress,
                        onValueChange = { /* Handle value change */ },
                        label = { Text("服务器地址") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val serverAddress = defaultServerAddress
                        /*sharedPreferences.edit().apply {
                            putString("serverAddress", serverAddress)
                            apply()
                        }*/
                        SharedPrefsManager.newInstance(context).putString("serverAddress", serverAddress)
                        showDialogState.value = false
                    }
                ) {
                    Text("保存")
                }
            }
        )
    }
}