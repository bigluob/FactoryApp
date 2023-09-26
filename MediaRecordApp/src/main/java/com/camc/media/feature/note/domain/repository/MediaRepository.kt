package com.camc.media.feature.note.domain.repository

import com.camc.media.data.model.RecordMedia

import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    fun getRecordMedias(): Flow<List<RecordMedia>>

    suspend fun getRecordMediaById(id: Int): RecordMedia?

    suspend fun insertRecordMedia(note: RecordMedia)

    suspend fun deleteRecordMedia(note: RecordMedia)
}