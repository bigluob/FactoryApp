package com.camc.factory.data.repository

import com.camc.factory.data.model.entity.RecordCategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    // Define the getCategorys function
    suspend fun getCategorys(): Flow<List<RecordCategory>>

    // Define the getCategoryById function
    suspend fun getCategoryById(id: Int): RecordCategory?

     suspend fun insertCategory(recordCategory: RecordCategory)

     suspend fun deleteCategory(recordCategory: RecordCategory)
}