package com.camc.factory.ui.feature.page.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordOutTextField(
    textValue: String,
    onValueChange: (String) -> Unit,
    onDone: (KeyboardActionScope.() -> Unit),
) {
    var visibilityPassword by rememberSaveable { mutableStateOf(value = false) }

    OutlinedTextField(
        value = textValue,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 6.dp, bottom = 5.dp, top = 4.dp),
        label = { Text(text = "Password") },
        placeholder = { Text(text = "password") },
        trailingIcon = {
            val (icon, iconColor) = if (visibilityPassword) {
                Pair(
                    Icons.Filled.Visibility,
                    Color.Red.copy(0.7F)
                )
            } else {
                Pair(
                    Icons.Filled.VisibilityOff,
                    Color.Black
                )
            }

            IconButton(onClick = { visibilityPassword = !visibilityPassword }) {
                Icon(
                    icon,
                    contentDescription = "Visibility",
                    tint = iconColor
                )
            }
        },
        visualTransformation = if (visibilityPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = onDone
        ),
        shape = CircleShape
    )
}