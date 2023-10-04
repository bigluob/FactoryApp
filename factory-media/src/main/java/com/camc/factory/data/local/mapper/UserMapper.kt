package com.camc.factory.data.local.mapper

import com.camc.factory.data.local.User  as LocalUser



class UserMapper {

    fun toDomain(user: LocalUser) = with(user) {
        LocalUser(
            usePinchToZoom = usePinchToZoom,
            useTapToFocus = useTapToFocus,
            useCamFront = useCamFront
        )
    }

    fun toLocal(user: LocalUser) = with(user) {
        LocalUser(
            usePinchToZoom = usePinchToZoom,
            useTapToFocus = useTapToFocus,
            useCamFront = useCamFront
        )
    }
}