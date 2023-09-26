package com.camc.media.compoent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.camc.media.ui.theme.RedOrange
import kotlinx.coroutines.delay


enum class SnackbarDuration { Short, Long }

@Composable
fun MessageSnackbar(
    message: String,
    modifier: Modifier = Modifier,
    duration: SnackbarDuration = SnackbarDuration.Short,
    onDismiss: () -> Unit = {}
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(visible) {
        if (visible) {
            delay(getDurationMillis(duration))
            visible = false
            onDismiss()
        }
    }

    if (visible) {
        Snackbar(
            modifier = modifier,
            content = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = RedOrange,
                        modifier = Modifier.padding(end = 18.dp)
                    )
                    Text(
                        text = message,
                        style = MaterialTheme.typography.body2,
                        color = Color.Red
                    )
                }
            },
            action = {
                TextButton(onClick = {
                    visible = false
                    onDismiss()
                }) {
                    Text(text = "关闭", color = Color.Black)
                }
            },
            backgroundColor = MaterialTheme.colors.primary
        )
    }
}

private fun getDurationMillis(duration: SnackbarDuration): Long {
    return when (duration) {
        SnackbarDuration.Short -> 3000L
        SnackbarDuration.Long -> 5000L
        SnackbarDuration.Indefinite -> Long.MAX_VALUE
    }
}