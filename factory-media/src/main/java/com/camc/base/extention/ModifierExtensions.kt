package com.camc.base.extention

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

fun Modifier.noClickable() = then(Modifier.clickable(enabled = false) {})