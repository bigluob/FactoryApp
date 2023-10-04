package com.camc.factory.data.local

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val usePinchToZoom: Boolean,
    val useTapToFocus: Boolean,
    val useCamFront: Boolean,
){
    companion object {
        val Default = User(usePinchToZoom = true, useTapToFocus = true, useCamFront = false)
    }
}