package com.camc.factory.data.repository

import com.camc.factory.data.model.entity.MediaRecord
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun getMediaRecords(): Flow<List<MediaRecord>>

    suspend fun getMediaRecordById(id: Int): MediaRecord?

    suspend fun insertMediaRecord(record: MediaRecord)

    suspend fun deleteMediaRecord(record:  MediaRecord)
    suspend fun getMediaRecordByName(name: String): Flow<List<MediaRecord>>
}