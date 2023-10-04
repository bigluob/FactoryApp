package com.camc.factory.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_records")
data class MediaRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,//CategoryName
    val fileName: String,
    val createTime: Long,
    val recorder: String
)