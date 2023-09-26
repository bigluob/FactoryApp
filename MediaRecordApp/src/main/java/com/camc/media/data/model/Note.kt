package com.camc.media.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.camc.media.ui.theme.BabyBlue
import com.camc.media.ui.theme.RedOrange

@Entity
data class Note(
    val title: String,
    val content: String,
    val recordNumb: Int,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange,BabyBlue)
    }
}

class InvalidNoteException(message: String) : Exception(message)