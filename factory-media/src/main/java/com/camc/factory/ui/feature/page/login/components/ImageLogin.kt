package com.camc.factory.ui.feature.page.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.camc.facttory.R

@Composable
fun ImageLogin(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.media_record),
        contentDescription = "Image Login",
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .size(120.dp),
        alignment = Alignment.TopCenter
    )
}