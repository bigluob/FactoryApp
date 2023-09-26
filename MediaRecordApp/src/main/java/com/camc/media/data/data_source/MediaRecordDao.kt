package com.camc.media.data.data_source

import androidx.room.*
import com.camc.media.data.model.RecordMedia

import kotlinx.coroutines.flow.Flow

@Dao
interface MediaRecordDao {

    @Query("SELECT * FROM RecordMedia")
    fun getRecordMedias(): Flow<List<RecordMedia>>

    @Query("SELECT * FROM RecordMedia WHERE photoId = :photoId")
    suspend fun getRecordMediaByPhotoID(photoId: String): RecordMedia?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecordMedia(recordMedia: RecordMedia)

    @Delete
    suspend fun deleteRecordMedia(recordMedia: RecordMedia)
}