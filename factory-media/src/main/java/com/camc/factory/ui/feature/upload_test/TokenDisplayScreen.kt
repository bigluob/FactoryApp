package com.camc.factory.ui.feature.upload_test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.camc.factory.utils.SharedPreferencesManager

@Composable
fun TokenDisplayScreen(sharedPreferencesManager: SharedPreferencesManager) {
    val token = sharedPreferencesManager.getToken()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        if (token != null) {
            Text("Token: $token", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        } else {
            Text("Token not found", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}