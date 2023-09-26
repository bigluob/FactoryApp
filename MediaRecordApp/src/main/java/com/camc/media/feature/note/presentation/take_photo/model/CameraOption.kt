package com.camc.media.feature.note.presentation.take_photo.model

import androidx.annotation.StringRes
import com.camc.media.R


enum class CameraOption(@StringRes val titleRes: Int) {
    Photo(R.string.photo),
    Video(R.string.video),
    QRCode(R.string.qr_code);

}
