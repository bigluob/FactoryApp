package com.camc.factory.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_category")
data class RecordCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val demandRecords: Int,
    val recordedCount: Int = 0,
    val time: Long = System.currentTimeMillis(), // 使用 Long 存储时间戳
    val isUploaded: Boolean = false
) {

}