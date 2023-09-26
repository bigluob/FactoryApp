package com.camc.media.data.data_source

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val usePinchToZoom: Boolean,
    val useTapToFocus: Boolean,
    val useCamFront: Boolean,
) {
    companion object {
        val Default = User(usePinchToZoom = true, useTapToFocus = true, useCamFront = false)
    }
}