package com.camc.media.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.camc.media.utils.DeviceUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity
data class RecordMedia(
    val noteTitle: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey var photoId: String = ""
) {
    init {
        generatePhotoId()
    }

    // Generate photoId function
    private fun generatePhotoId() {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val formattedTimestamp = dateFormat.format(Date(timestamp))

        photoId = "$noteTitle-$formattedTimestamp"
    }
}