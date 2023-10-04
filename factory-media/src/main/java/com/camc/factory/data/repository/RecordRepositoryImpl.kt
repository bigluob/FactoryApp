package com.camc.factory.data.repository

import com.camc.factory.data.dao.MediaRecordDao
import com.camc.factory.data.model.entity.MediaRecord
import kotlinx.coroutines.flow.Flow

class RecordRepositoryImpl (
    private val dao: MediaRecordDao
) : RecordRepository  {
    override fun getMediaRecords(): Flow<List<MediaRecord>> {
        return dao.getMediaRecords()
    }

    override suspend fun getMediaRecordById(id: Int): MediaRecord? {
        return  dao.getMediaRecordById(id)
    }

    override suspend fun insertMediaRecord(mediaRecord: MediaRecord) {
       dao.insertMediaRecord(mediaRecord)
    }

    override suspend fun deleteMediaRecord(mediaRecord: MediaRecord) {
        dao.getMediaRecords()
    }

    override suspend fun getMediaRecordByName(name: String): Flow<List<MediaRecord>> {
        return dao.getMediaRecordByName(name)
    }

}