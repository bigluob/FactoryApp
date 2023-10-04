package com.camc.factory.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camc.factory.data.model.entity.RecordCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordCategoryDao {
    @Query("SELECT * FROM record_category")
    fun getRecordCategory(): Flow<List<RecordCategory>>

    @Query("SELECT * FROM record_category WHERE id = :id")
    suspend fun getRecordCategoryById(id: Int): RecordCategory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecordCategory(recordCategory: RecordCategory)

    @Delete
    suspend fun deleteRecordCategory(recordCategory: RecordCategory)
}