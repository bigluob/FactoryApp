package com.camc.factory.ui.feature.page.login.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonLogin(
    onclick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onclick,
        modifier = Modifier.width(300.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 5.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.LightGray
        )
    ) {
        Text(
            text = "用户登录",
            fontSize = 28.sp,
            color = Color.White
        )
    }
}