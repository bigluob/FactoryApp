package com.camc.factory.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.camc.factory.data.dao.MediaRecordDao
import com.camc.factory.data.dao.RecordCategoryDao
import com.camc.factory.data.model.entity.MediaRecord
import com.camc.factory.data.model.entity.RecordCategory


@Database(entities = [RecordCategory::class, MediaRecord::class], version =3, exportSchema = false)
abstract class FactDatabase : RoomDatabase() {
    abstract fun recordCategoryDao(): RecordCategoryDao
    abstract fun mediaRecordDao(): MediaRecordDao

    companion object {
        const val DATABASE_NAME = "fact_db"
    }
}