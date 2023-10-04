package com.camc.factory.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camc.factory.data.model.entity.MediaRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaRecordDao {
    @Query("SELECT * FROM media_records")
    fun getMediaRecords(): Flow<List<MediaRecord>>

    @Query("SELECT * FROM media_records WHERE id = :id")
    suspend fun getMediaRecordById(id: Int): MediaRecord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaRecord(mediaRecord: MediaRecord)

    @Delete
    suspend fun deleteMediaRecord(mediaRecord: MediaRecord)

    @Query("SELECT * FROM media_records WHERE name LIKE '%' || :name || '%'")
    fun getMediaRecordByName(name: String): Flow<List<MediaRecord>> // 修改为返回Flow<List<MediaRecord>>
}
