package com.camc.media.data.data_source.mapper

import com.camc.media.data.data_source.User
/*import com.camc.media.domain.User*/
import com.camc.media.data.data_source.User as LocalUser

class UserMapper {

    fun toDomain(user: LocalUser) = with(user) {
        User(
            usePinchToZoom = usePinchToZoom,
            useTapToFocus = useTapToFocus,
            useCamFront = useCamFront
        )
    }

    fun toLocal(user: User) = with(user) {
        LocalUser(
            usePinchToZoom = usePinchToZoom,
            useTapToFocus = useTapToFocus,
            useCamFront = useCamFront
        )
    }
}