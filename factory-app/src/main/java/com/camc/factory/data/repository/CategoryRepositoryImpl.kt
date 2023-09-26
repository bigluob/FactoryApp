package com.camc.factory.data.repository


import com.camc.factory.data.dao.RecordCategoryDao
import com.camc.factory.data.model.entity.RecordCategory

import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val categoryDao: RecordCategoryDao
) : CategoryRepository {
    override suspend fun getCategorys(): Flow<List<RecordCategory>> {
       return  categoryDao.getRecordCategory()
    }

    override suspend fun getCategoryById(id: Int): RecordCategory? {
        return categoryDao.getRecordCategoryById(id)
    }

    override suspend fun insertCategory(recordCategory: RecordCategory) {
        categoryDao.insertRecordCategory(recordCategory)
    }

    override suspend fun deleteCategory(recordCategory: RecordCategory) {
        categoryDao.deleteRecordCategory(recordCategory)
    }

}