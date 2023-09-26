package com.camc.media.data.data_source

import androidx.compose.ui.input.key.Key.Companion.MediaRecord
import androidx.room.Database
import androidx.room.RoomDatabase
import com.camc.media.data.model.Note
import com.camc.media.data.model.RecordMedia


@Database(
    entities = [Note::class, RecordMedia::class],
    version = 3
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val mediaRecordDao: MediaRecordDao

    companion object {
        const val DATABASE_NAME = "mediarecord_db"
    }
}